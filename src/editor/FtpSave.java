package editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

/**
 *
 * @author aliyasineser
 */
public class FtpSave implements ISave {

    //static final String fileError = "PANDA_TEXT_EDITOR_FILE_ERROR";
    //static final String wrongPassword = "PANDA_TEXT_EDITOR_WRONG_PASSWORD";
    static final String fileUpdatedSuccessful = "PANDA_TEXT_EDITOR_FILE_UPLOADED_SUCCESSFUL";
    static final String fileUpdatedError = "PANDA_TEXT_EDITOR_FILE_UPLOADED_ERROR";
    static final String loginError = "PANDA_TEXT_EDITOR_FILE_LOGIN_ERROR";
    static final String logoutError = "PANDA_TEXT_EDITOR_FILE_LOGOUT_ERROR";
    String returnState;

    String ipAddress;
    Integer portNumber;
    String userID;
    String userPass;
    String path;

    public boolean save() {
        // ISave
        return false;
    }

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

    public String uploadToFTP(String ipAddress, String portNumber, String userID, String userPass, String newFileName, String filePassword) {

        // get an ftpClient object
        FTPClient ftpClient = new FTPClient();
        FileInputStream inputStream = null;
        final String localFileName = newFileName;
        

        int stringtToIntForPortNumber = Integer.parseInt(portNumber);

        try {
            // pass directory path on server to connect
            ftpClient.connect(ipAddress, stringtToIntForPortNumber);

            // pass username and password, returned true if authentication is
            // successful
            boolean login = ftpClient.login(userID, userPass);

            if (login) {
                //System.out.println("Connection established...");
                inputStream = new FileInputStream(path);

                //check the root file if the same file name exist
                FTPFileFilter filter = new FTPFileFilter() {

                    @Override
                    public boolean accept(FTPFile ftpFile) {

                        return (ftpFile.isFile() && ftpFile.getName().startsWith(localFileName));
                    }
                };

                //uzantı ekle endswith()
                //in root directory, if same file name exist then add a number to end of file name
                FTPFile[] result = ftpClient.listFiles("/", filter);
                if(result.length > 0)
                {
                    //change file name like file(1).txt
                    newFileName = rtrim(newFileName);
                    
                }
                
                
                boolean uploaded = ftpClient.storeFile(newFileName, inputStream);
                
        
                if (uploaded) {
                    returnState = fileUpdatedSuccessful;
                } else {
                    returnState = fileUpdatedError;
                }

                // logout the user, returned true if logout successfully
                boolean logout = ftpClient.logout();
                if (!logout) {
                    returnState = logoutError;
                }
            } else {
                returnState = loginError;
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

        return returnState;
    }
    
    
    public static String rtrim(String s) {
        int i = s.length()-1;
        while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
            i--;
        }
        return s.substring(0,i+1);
    }


}
