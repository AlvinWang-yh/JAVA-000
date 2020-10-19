import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: xiaofengfeisha
 * @Date: 2020/10/18 17:57
 * @Version 1.0
 */
public class MyClassLoader extends ClassLoader{

    public static void main(String[] args) {
        MyClassLoader myClassLoader = new MyClassLoader();
        try {
            Class<?> hello = myClassLoader.findClass("Hello");
            Object a = hello.newInstance();
            Method method = hello.getMethod("hello");
            method.invoke(a);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try {
            String path = this.getClass().getResource("").getPath();
            String fileName = path+name+".xlass";
            File file = new File(fileName);
            Long fileLength = file.length();

            FileInputStream fi = new FileInputStream(fileName);
            byte[] classBytes = new byte[fileLength.intValue()];
            fi.read(classBytes);

            for(int i=0;i<classBytes.length;i++){
                classBytes[i] = (byte)(255 - classBytes[i]);
            }
            return defineClass(name,classBytes,0,classBytes.length);
        } catch (FileNotFoundException e) {
            throw new ClassNotFoundException(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new ClassNotFoundException(name);
    }
}
