package me.bryang.chatlab.file;


import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;

public class FileWrapper<T extends PluginFiles> {

    private String fileName;
    private Path path;

    private YamlConfigurationLoader loader;
    private ConfigurationNode node;

    private final Class<T> clazz;
    private final T internClass;


    public FileWrapper(String fileName, Path path, Class<T> clazz, T internClass){
        this.fileName = fileName;
        this.path = path;

        this.clazz = clazz;
        this.internClass = internClass;

        start();
    }

    public void start(){

         loader = YamlConfigurationLoader
                .builder()
                .path(path.resolve(fileName + ".yml"))
                 .defaultOptions(config -> config.header("\n " + fileName + ".yml"))
                .build();

        try {
            node = loader.load();
            node.set(clazz, internClass);
            loader.save(node);


        }catch(IOException exception){
            exception.fillInStackTrace();
        }
    }

    public T get(){
        return internClass;
    }

    public void reload(){

        try {
           node = loader.load();
        }catch(IOException exception){
            exception.fillInStackTrace();
        }

    }



}
