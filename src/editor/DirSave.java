package editor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

/**
 *
 * @author Kaan Ucar
 */
<<<<<<< HEAD
public class DirSave{
    public static boolean save(File file, Object object, String password){
        return save(file, ByteArrayConverter.convertToByteArray(object),password);
    }
    
    private static boolean save(File file, byte[] bytes, String password){
=======
public class DirSave {

    public static boolean save(File file, Object object, String password) {
        
        return save(file, ByteArrayConverter.convertToByteArray(object), password);
    }

    private static boolean save(File file, byte[] bytes, String password) {
>>>>>>> refs/remotes/origin/master
        byte[] encryptedBytes = Cryption.encryptFile(bytes, password);
        if (encryptedBytes == null) {
            return false;
<<<<<<< HEAD
        
        try{
            Files.write(Paths.get(file.getPath()), encryptedBytes);
=======
>>>>>>> refs/remotes/origin/master
        }

        try {
            Files.write(Paths.get(file.getPath()), encryptedBytes);
        } catch (IOException ex) {
            return false;
        }

        return true;
    }
}
