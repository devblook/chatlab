package me.bryang.chatlab.file;


public class FileWrapper<T extends PluginFiles> {

    private Class<T> clazz;
    private T internClass;


    public FileWrapper(Class<T> clazz){
        this.clazz = clazz;
    }

    public T get(){
        return internClass;
    }


}
