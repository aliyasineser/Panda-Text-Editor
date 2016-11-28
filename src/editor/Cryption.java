package editor;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;

/** Includes encryption and decryption utilities.
 *
 * @author Kaan Ucar
 */
public final class Cryption {
    private static final int ivLength = 16;
    
    /**
     * Encrypts given byte array by given password.
     * Returns null if there is any problem.
     * 
     * @param fileBytes bytes to encrypt
     * @param password password
     * 
     * @return encrypted bytes.
     */
    public static byte[] encryptFile(byte[] fileBytes, String password) {
        final int INT_SIZE = 4;
            
        byte[] ivBytes = new byte[ivLength];
        new Random().nextBytes(ivBytes);
        
        SecretKeySpec key = new SecretKeySpec(ivBytes, "AES");
      	IvParameterSpec iv = new IvParameterSpec(ivBytes);
        
        try{
            
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            
            byte[] ivLengthBytes = java.nio.ByteBuffer.allocate(INT_SIZE).putInt(ivBytes.length).array();
            byte[] passwordBytes = cipher.doFinal(ByteArrayConverter.convertToByteArray(password));
            byte[] passwordLengthBytes = java.nio.ByteBuffer.allocate(INT_SIZE).putInt(passwordBytes.length).array();
            byte[] messageBytes = cipher.doFinal(fileBytes);
                    
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(ivLengthBytes);
            baos.write(ivBytes);
            baos.write(passwordLengthBytes);
            baos.write(passwordBytes);
            baos.write(messageBytes);
        
            byte [] encryptedBytes = baos.toByteArray();
            baos.close();
            
            return encryptedBytes;
        }
        catch(Exception ex){
            return null;
        }
    }
    
    /**
     * Decrypts given byte array by given password.
     * Returns null if given password is invalid.
     * 
     * @param fileBytes bytes to decrypt
     * @param password password
     * 
     * @return decrypted bytes.
     * 
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException 
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decryptFile(byte[] fileBytes, String password) 
            throws NoSuchAlgorithmException, NoSuchPaddingException, 
            InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException
    {
        final int INT_SIZE = 4;
        
        byte[] keyLengthBytes = Arrays.copyOfRange(fileBytes, 0, INT_SIZE);
        int keyLength = java.nio.ByteBuffer.wrap(keyLengthBytes).getInt();
            
        byte[] keyBytes = Arrays.copyOfRange(fileBytes, INT_SIZE, INT_SIZE + keyLength);
        
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
      	IvParameterSpec iv = new IvParameterSpec(keyBytes);
            
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
            
        byte[] passwordLengthBytes = Arrays.copyOfRange(fileBytes, INT_SIZE + keyLength, 2*INT_SIZE + keyLength);
        int passwordLength = java.nio.ByteBuffer.wrap(passwordLengthBytes).getInt();
        byte[] passwordBytes = cipher.doFinal(Arrays.copyOfRange(fileBytes, 2*INT_SIZE + keyLength, 2*INT_SIZE + keyLength + passwordLength));
            
        if(!((String)(ByteArrayConverter.convertFromByteArray(passwordBytes))).equals(password))
            return null;
            
        return cipher.doFinal(Arrays.copyOfRange(fileBytes, 2*INT_SIZE + keyLength + passwordLength, fileBytes.length));
    }
}
