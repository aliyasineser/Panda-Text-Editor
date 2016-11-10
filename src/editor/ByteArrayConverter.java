package editor;

import java.io.*;
/** Includes convertion utilities between Object and byte array
 *
 * @author Kaan Ucar
 */
public final class ByteArrayConverter {
	/**
     * Converts given byte array to an Object.
     * Returns null if there is any problem.
     * @return converted Object
     */
    public static Object convertFromByteArray(byte[] byteObject) {
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(byteObject);
            ObjectInputStream in = new ObjectInputStream(bais);
            
            Object o = in.readObject();
            
            in.close();
            bais.close();

            return o;
	}
        catch(Exception ex){
            return null;
        }
    }
  
	/**
     * Converts given Object to byte array.
     * Returns null if there is any problem.
     * @return converted byte array
     */
    public static byte[] convertToByteArray(Object object) {
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);

            out.writeObject(object);
            byte[] byteArray = baos.toByteArray();

            out.close();
            baos.close();

            return  byteArray;
        }
        catch(Exception ex){
            return null;
        }
    }
}
