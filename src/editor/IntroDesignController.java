package editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author emreaydin
 */
public class IntroDesignController implements Initializable {
    
    private static String directoryPath = EditorAssets.passwordDirectory;
    private static String fileName = EditorAssets.passwordFile;
    private static Boolean authentication = false;
    private static String password = null;
    
    @FXML
    public PasswordField passField1;
    @FXML
    public Label passwordText2;
    @FXML
    public PasswordField passField2;
    @FXML
    private VBox IntroScen;
   
    @FXML
    private void btnClick(ActionEvent event) throws IOException{
        if (password == null) {
            if (passField1.getText().length() != 0 && 
                passField1.getText().equals(passField2.getText())) {
                password = passField1.getText();
                savePassword(passField1.getText());
                loadTextEditor(event);
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Passwords doesn't match");
                alert.setContentText("Passwords must be same!");
                alert.show();
            }
        }else{
            if (passField1.getText().length() != 0 && 
                passField1.getText().equals(password)){
                loadTextEditor(event);
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Wrong password");
                alert.setContentText("If you don't remember password contact with administrator!");
                alert.show();
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getUserPassword();
        } catch (IOException ex) {
            Logger.getLogger(IntroDesignController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (password != null) {
            passwordText2.setVisible(false);
            passField2.setVisible(false);
        }
    }    
    private void savePassword(String text) {
        File file = new File(directoryPath + fileName);
        Writer writer = null;
        try{
        file.createNewFile();
        writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream(file), "utf-8"));
        writer.write(text);
        } catch (IOException ex) {
            System.out.println("Save password error.");
        } finally {
           try {writer.close();} catch (Exception ex) {/*ignore*/}
        }        
    }

    private void getUserPassword() throws IOException {

        // Try catch bloğu eklenerek exception fırlatması engellenebilir.
        // Dosya bulunamaması durumunda yeniden oluşturulur.
        
        // If directory not exist create directory.
        File directory = new File(directoryPath);
        directory.mkdirs();

        // If file not exist create file.
        File file = new File(directoryPath + fileName);
        if (!file.exists()) {
            password = null;
            file.createNewFile();
        }else{
            // Create buffer reader.
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            // Get password.
            password = bufferedReader.readLine();

            bufferedReader.close();
        }
    }
    
    public static String getPassword(){
        return password;
    }

    private void loadTextEditor(ActionEvent event) throws IOException{
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("EditorDesign.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.getIcons().addAll(new Image("file:src/Assets/32x32_panda_icon.png"),
                new Image("file:src/Assets/64x64_panda_icon.png"), new Image("file:src/Assets/256x256_panda_icon.png"));
        primaryStage.setTitle("Welcome to Panda Text Editor");
        Scene passwordScene = new Scene(root, 640, 480);
        primaryStage.setScene(passwordScene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }
}
