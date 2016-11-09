/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ilayd
 */
public class PasswordDesignController implements Initializable {

    public VBox passScene;
    public Button cancelButton;
    public Button enterButton;
    public PasswordField passText;
    public ImageView Image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image.setImage(new Image("file:src/Assets/64x64_panda_icon.png"));
    }

    public void enter() throws IOException {

        // TextEditor.passwordOfTheUser = passText.getText();
        if (TextEditor.getPasswordOfTheUser().equals(passText.getText())) {
            //TextEditor.receivedPassword = passText.getText();
            EditorController.receivedPassword = passText.getText();
            ((Stage) (passScene.getScene().getWindow())).close();
        } else {
            System.err.println(TextEditor.getPasswordOfTheUser() + "  " + passText.getText());
            passText.clear();
            Stage errorWindow = new Stage();
            errorWindow.initModality(Modality.APPLICATION_MODAL);
            errorWindow.setTitle("Error");
            Parent errorLayout = FXMLLoader.load(new URL("file:src/editor/ErrorBox.fxml"), new MyResources("Error", "Password is incorrect"));
            Scene scene = new Scene(errorLayout);
            errorWindow.setScene(scene);
            errorWindow.showAndWait();
            EditorController.receivedPassword = passText.getText();
//                ((Stage) (passScene.getScene().getWindow())).close();
        }

        // ((Stage) (passScene.getScene().getWindow())).close();
    }
    

    public void cancel() {
        EditorController.sign = true;
        //eger arayuzden editorController class,na bir sey return edebilirsek 
        //bu sign silinecek

        ((Stage) (passScene.getScene().getWindow())).close();
    }

}
