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
  
    public Cryption(byte[] ivBytes, byte[] messageBytes) {
  	key = new SecretKeySpec(ivBytes, "AES");
      	iv = new IvParameterSpec(ivBytes);
      	message = messageBytes;
    }
    
    public Cryption(byte[] messageBytes) {
        byte[] bytes = new byte[ivLength];
        new Random().nextBytes(bytes);
        
        key = new SecretKeySpec(bytes, "AES");
      	iv = new IvParameterSpec(bytes);
        message = messageBytes;
    }
    
    public IvParameterSpec getIv(){
        return iv;
    }
    
    public static byte[] encryptFile(byte[] messageBytes) {
        final int INT_SIZE = 4;
        
        Cryption crypt = new Cryption(messageBytes);
        byte[] ivBytes = crypt.getIv().getIV();
        byte[] lengthBytes = java.nio.ByteBuffer.allocate(INT_SIZE).putInt(ivBytes.length).array();
        byte[] fileBytes;
        
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(lengthBytes);
            baos.write(ivBytes);
            baos.write(crypt.encrypt());
        
            fileBytes = baos.toByteArray();
            baos.close();
        }
        catch(Exception ex){
            return null;
        }
        
        return fileBytes;
    }
    
    public static byte[] decryptFile(byte[] fileBytes) {
        final int INT_SIZE = 4;
        
        byte[] lengthBytes = Arrays.copyOfRange(fileBytes, 0, INT_SIZE);
        int length = java.nio.ByteBuffer.wrap(lengthBytes).getInt();
        byte[] ivBytes = Arrays.copyOfRange(fileBytes, INT_SIZE, INT_SIZE + length);
        byte[] messageBytes = Arrays.copyOfRange(fileBytes, INT_SIZE + length, fileBytes.length);
        
        return (new Cryption(ivBytes, messageBytes)).decrypt();
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