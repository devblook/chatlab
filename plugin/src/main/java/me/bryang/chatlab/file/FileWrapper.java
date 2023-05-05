package me.bryang.chatlab.file;


import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Path;

public class FileWrapper<T extends PluginFiles> {
    private Class<T> clazz;
    private Path path;

    private YamlConfigurationLoader loader;
    private ConfigurationNode node;

    private T internClass;


    public FileWrapper(Path path, Class<T> clazz){
        this.path = path;

        this.clazz = clazz;
        start();
    }

    public void start(){

         loader = YamlConfigurationLoader
                .builder()
                .path(path)
                .build();

        try {
            node = loader.load();

            T configValues = node.get(clazz);
            node.set(clazz, configValues);

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
