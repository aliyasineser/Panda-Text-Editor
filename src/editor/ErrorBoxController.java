/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aliya
 */
public class ErrorBoxController implements Initializable {

    public GridPane gridWindow;
    public ImageView errImage;
    public Label messageLabel;
    public Label detailsLabel;
    public Button okButton;

    public static void showErrorBox(String windowTitle, String messageTitle, String message){
        try{
            Stage errorWindow = new Stage();
            errorWindow.initModality(Modality.APPLICATION_MODAL);
            errorWindow.setTitle(windowTitle);
            Parent errorLayout = FXMLLoader.load(new URL("file:src/editor/ErrorBox.fxml"), new MyResources(messageTitle, message));
            Scene scene = new Scene(errorLayout);
            errorWindow.setScene(scene);
            errorWindow.showAndWait();
        }
        catch(Exception ex){
        
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errImage.setImage(new Image("file:src/Assets/errorPanda_200x200.png"));
        messageLabel.setText(rb.getString("message"));
        detailsLabel.setText(rb.getString("description"));
        //enter'a basildiginda hata mesaji ekrani kapanir
        okButton.setOnKeyPressed((KeyEvent key)->{
            if (key.getCode() == KeyCode.ENTER) {
                //System.err.println("firee");
                okButton.fire();
            }
        });
    }

    public void closeWindow() {

        ((Stage) (gridWindow.getScene().getWindow())).close();
    }

}
