package editor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author Kaan Ucar
 */
public class Cryption {
    SecretKeySpec key;
    IvParameterSpec iv;
    byte [] message;
  
    public Cryption(byte[] ivBytes,byte[] msgBytes){
  		key = new SecretKeySpec(ivBytes, "AES");
      	iv = new IvParameterSpec(ivBytes);
      	message = msgBytes;
    }
  
    public byte[] encrypt() {       
        try{
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            
            return cipher.doFinal(message);
        }
        catch(Exception ex){
            return null;
        }
    }

    public byte[] decrypt() {       
        try{
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            
            return cipher.doFinal(message);
        }
        catch(Exception ex){
            return null;
        }
    }
}
