package editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author aliyasineser,emreaydin
 */
public class TextEditor extends Application {
    // Start login screen.
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("IntroDesign.fxml"));
        primaryStage.getIcons().addAll(new Image("file:src/Assets/32x32_panda_icon.png"), 
                new Image("file:src/Assets/64x64_panda_icon.png"), 
                new Image("file:src/Assets/256x256_panda_icon.png"));
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
}
