package editor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

/*
 * This class saves the file as encrypted to given the directory 
 *
 */

public class DirSave {
    /*This method saves given object with encrypting by given 	*password.
     * Returns false if there is any problem.
     * @param file given file that want to save
     * @param object this object converted as a byte array
     * @param password the password of given file
     * @return save() call the wrapper save function
     */
    public static boolean save(File file, Object object, String password) {
        
        return save(file, ByteArrayConverter.convertToByteArray(object), password);
    }

    /*This method encrypted the given file 
     *
     * @param file given file that want to save
     * @param bytes uses for cryption
     * @param password the password of given file
     * @return true when successful saving
     * @return false when unsuccessful saving
     */
    private static boolean save(File file, byte[] bytes, String password) {
        byte[] encryptedBytes = Cryption.encryptFile(bytes, password);
        if (encryptedBytes == null) {
            return false;
        }

        try {
            // writting encrypted file to the directory path
            Files.write(Paths.get(file.getPath()), encryptedBytes);
        } catch (IOException ex) {
            return false;
        }

        return true;
    }
}
