/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aliya
 */
public class FtpBoxController implements Initializable {

    public VBox ftpScene;
    public Button cancelButton;
    public Button saveButton;
    public TextField ipText = new TextField();         //ip
    public TextField portText = new TextField();       //port
    public TextField idText = new TextField();         //userID
    public PasswordField passText = new PasswordField();       //user password
    public TextField fileNameText = new TextField();   //new file name
    public PasswordField filePassText = new PasswordField(); //file password

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void save() {
        FtpSave saveMe = new FtpSave();
        String uploadToFTP = saveMe.uploadToFTP(ipText.getText(), portText.getText(), idText.getText(), passText.getText(), fileNameText.getText(), "htmltext", filePassText.getText(), ".ptf");
        ((Stage) (ftpScene.getScene().getWindow())).close();
    }

    public void cancel() {

        ((Stage) (ftpScene.getScene().getWindow())).close();
    }

}
