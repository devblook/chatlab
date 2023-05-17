package me.bryang.chatlab.file;


import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

public class FileWrapper<T extends PluginFiles> {

    private String fileName;
    private Path path;

    private HoconConfigurationLoader loader;
    private CommentedConfigurationNode node;

    private final Class<T> clazz;
    private AtomicReference<T> internClass;


    public FileWrapper(String fileName, Path path, Class<T> clazz) {


        this.fileName = fileName;
        this.path = path;

        this.clazz = clazz;

        start();
    }

    public void start(){

         loader = HoconConfigurationLoader
                .builder()
                .path(path.resolve(fileName + ".conf"))
                 .defaultOptions(
                         config -> config.header("\n " + fileName + ".conf"))
                 .build();

        try {
            node = loader.load();

            internClass = new AtomicReference<>(node.get(clazz));

            node.set(clazz, internClass.get());
            loader.save(node);


        }catch(IOException exception){
            exception.fillInStackTrace();
        }
    }

    public T get(){
        return internClass.get();
    }

    public void reload(){

        try {

            node = loader.load();

            T newClass = node.get(clazz);
            node.set(clazz, newClass);

            internClass.set(newClass);
            loader.save(node);

        }catch(IOException exception){
            exception.fillInStackTrace();
        }

    }



}
