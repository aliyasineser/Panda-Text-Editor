package editor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class Sifreyi almak icin olusan ekranin kontrolleri yapilir
 *
 * @author ilayd
 */
public class PasswordDesignController implements Initializable {
    
    public VBox passScene;
    public Button cancelButton;
    public Button enterButton;
    public PasswordField passText;
    public ImageView Image;
    public Pane pane;

    private static String password = null;
    
    /**
     * This method creates a window to getting password from user.
     *
     * @return password which is received from user.
     */
    public static String askPassword(){
        Stage passWindow = new Stage();
        passWindow.initModality(Modality.APPLICATION_MODAL);
        passWindow.setTitle("Enter password");
        
        try{
            Parent passLayout = FXMLLoader.load(new URL("file:src/editor/PasswordDesign.fxml"));
            Scene thisScene = new Scene(passLayout);
        
            passWindow.setOnCloseRequest(event -> {
                password = null;
            });
            
            passWindow.setScene(thisScene);
            passWindow.showAndWait();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return password;
    }
    
    /**
     * Shortcuts added
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image.setImage(new Image("file:src/Assets/64x64_panda_icon.png"));

    }

    public void handleButtons() {

        //enter'a basildigi zaman sifre sisteme girilecek
        //esc'ye basildigi zaman sifre ekrani kapatilacak
        passText.setOnKeyPressed((KeyEvent key) -> {
            try {
                if (key.getCode() == KeyCode.ENTER) {

                    enter();
                }
                if (key.getCode() == KeyCode.ESCAPE) {
                    cancel();

                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });

    }

    /**
     * When the enter key is pressed updates password and closes window
     *
     * @throws IOException
     */
    public void enter() throws IOException {
        password = passText.getText();
        ((Stage) (passScene.getScene().getWindow())).close();
    }

    /**
     * When the cancel key is pressed resets password and closes window
     */
    public void cancel() {
        password = null;
        ((Stage) (passScene.getScene().getWindow())).close();
    }
}
