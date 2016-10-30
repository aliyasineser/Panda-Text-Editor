package editor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
/**
 *
 * @author Kaan Ucar
 */
public class DirSave implements ISave{
    private final String directory;
    private final String password;
    byte[] bytes;
    
    public DirSave(String dir, byte[] b, String psw){
        directory = dir;
        bytes = b;
        password = psw;
    }
    
    public DirSave(String dir, Object object, String psw){
        directory = dir;
        bytes = ByteArrayConverter.convertToByteArray(object);
        password = psw;
    }
    
    public String getDirectory(){
        return directory;
    }
    
    public String getPassword(){
        return password;
    }
    
    public boolean save(){
        byte[] encryptedBytes = Cryption.encryptFile(bytes, password);
        if(encryptedBytes == null)
            return false;
        
        try{
            Files.write(Paths.get(directory), encryptedBytes);
        }
        catch(IOException ex){
            return false;
        }
        
        return true;
    }
}
