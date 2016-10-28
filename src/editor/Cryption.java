package editor;

import java.io.*;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;
/**
 *
 * @author Kaan Ucar
 */
public class Cryption {
    private static int ivLength = 16;
    
    private final SecretKeySpec key;
    private final IvParameterSpec iv;
    private final byte [] message;
    private final String password;
  
    public Cryption(byte[] ivBytes, byte[] messageBytes, String strPassword) {
  	key = new SecretKeySpec(ivBytes, "AES");
      	iv = new IvParameterSpec(ivBytes);
      	message = messageBytes;
        password = strPassword;
    }
    
    public Cryption(byte[] messageBytes, String strPassword) {
        byte[] bytes = new byte[ivLength];
        new Random().nextBytes(bytes);
        
        key = new SecretKeySpec(bytes, "AES");
      	iv = new IvParameterSpec(bytes);
        message = messageBytes;
        password = strPassword;
    }
    
    public IvParameterSpec getIv(){
        return iv;
    }
    
    public static byte[] encryptFile(byte[] fileBytes, String strPassword) {
        return (new Cryption(fileBytes, strPassword)).encrypt();
    }
    
    public static byte[] decryptFile(byte[] fileBytes, String strPassword) {
        final int INT_SIZE = 4;
        
        byte[] keyLengthBytes = Arrays.copyOfRange(fileBytes, 0, INT_SIZE);
        int keyLength = java.nio.ByteBuffer.wrap(keyLengthBytes).getInt();
            
        byte[] keyBytes = Arrays.copyOfRange(fileBytes, INT_SIZE, INT_SIZE + keyLength);
        return (new Cryption(keyBytes, fileBytes, strPassword)).decrypt();
    }
    
    public byte[] encrypt() {       
        try{
            final int INT_SIZE = 4;
            
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            
            byte[] ivBytes = getIv().getIV();
            byte[] ivLengthBytes = java.nio.ByteBuffer.allocate(INT_SIZE).putInt(ivBytes.length).array();
            byte[] passwordBytes = cipher.doFinal(ByteArrayConverter.convertToByteArray(password));
            byte[] passwordLengthBytes = java.nio.ByteBuffer.allocate(INT_SIZE).putInt(passwordBytes.length).array();
            byte[] messageBytes = cipher.doFinal(message);
                    
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(ivLengthBytes);
            baos.write(ivBytes);
            baos.write(passwordLengthBytes);
            baos.write(passwordBytes);
            baos.write(messageBytes);
        
            byte [] fileBytes = baos.toByteArray();
            baos.close();
            
            return fileBytes;
        }
        catch(Exception ex){
            return null;
        }
    }

    public byte[] decrypt() {
        try{
            final int INT_SIZE = 4;
            
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            
            int keyLength = getIv().getIV().length;
            byte[] passwordLengthBytes = Arrays.copyOfRange(message, INT_SIZE + keyLength, 2*INT_SIZE + keyLength);
            int passwordLength = java.nio.ByteBuffer.wrap(passwordLengthBytes).getInt();
            byte[] passwordBytes = cipher.doFinal(Arrays.copyOfRange(message, 2*INT_SIZE + keyLength, 2*INT_SIZE + keyLength + passwordLength));
            
            if(!((String)(ByteArrayConverter.convertFromByteArray(passwordBytes))).equals(password))
                return null;
            
            return cipher.doFinal(Arrays.copyOfRange(message, 2*INT_SIZE + keyLength + passwordLength, message.length));
        }
        catch(Exception ex){
            return null;
        }
    }
}
