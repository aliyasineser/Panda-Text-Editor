package editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author aliyasineser
 */
public class FtpSave implements ISave {

    String ipAddress;
    Integer portNumber;
    String userID;
    String userPass;
    String path;

    
    
    public FtpSave() {
        this.ipAddress = "";
        this.portNumber = 0;
        this.userID = "";
        this.userPass = "";
        this.path = "";
    }

    public FtpSave(String ipAddress, Integer portNumber, String userID, String userPass, String path) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
        this.userID = userID;
        this.userPass = userPass;
        this.path = path;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(Integer portNumber) {
        this.portNumber = portNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public  String uploadToFTP(String ipAddress, String portNumber, String userID, String userPass, String path){
            
         // get an ftpClient object
        FTPClient ftpClient = new FTPClient();
        FileInputStream inputStream = null;

        int stringtToIntForPortNumber = Integer.parseInt(portNumber);    
        
        try {
            // pass directory path on server to connect
            ftpClient.connect(ipAddress, stringtToIntForPortNumber);

            // pass username and password, returned true if authentication is
            // successful
            boolean login = ftpClient.login(userID, userPass);

            if (login) {
                System.out.println("Connection established...");
                inputStream = new FileInputStream(path);

                boolean uploaded = ftpClient.storeFile(path,inputStream);
                if (uploaded) {
                    System.out.println("File uploaded successfully!");
                } else {
                    System.out.println("Error in uploading file!");
                }

                // logout the user, returned true if logout successfully
                boolean logout = ftpClient.logout();
                if (logout) {
                    System.out.println("Connection close..");
                }
            } else {
                System.out.println("Connection fail..");
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        
          return "";
    }
    

}
