package editor;

import java.io.*;
/**
 *
 * @author Kaan Ucar
 */
public final class ByteArrayConverter {
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