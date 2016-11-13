package editor;

import com.sun.javaws.Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author aliyasineser,emreaydin
 */
public class TextEditor extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
      
        Parent root = FXMLLoader.load(getClass().getResource("IntroDesign.fxml"));
        primaryStage.getIcons().addAll(new Image("file:src/Assets/32x32_panda_icon.png"),
                new Image("file:src/Assets/64x64_panda_icon.png"), new Image("file:src/Assets/256x256_panda_icon.png"));
        primaryStage.setTitle("Welcome to Panda Text Editor");
        Scene passwordScene = new Scene(root, 600, 480);
        primaryStage.setScene(passwordScene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(400);
        primaryStage.show();
       
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    // Şifre için getter.
    public static String getPasswordOfTheUser() {
        return IntroDesignController.getPassword();
    }
}
