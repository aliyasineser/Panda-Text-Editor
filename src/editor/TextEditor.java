package editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author aliya
 */
public class TextEditor extends Application {

    public static String passwordOfTheUser;

    public static String getPasswordOfTheUser() {
        return passwordOfTheUser;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("EditorDesign.fxml"));
        primaryStage.getIcons().addAll(new Image("file:src/Assets/32x32_panda_icon.png"),
                new Image("file:src/Assets/64x64_panda_icon.png"), new Image("file:src/Assets/256x256_panda_icon.png"));
        primaryStage.setTitle("Panda Text Editor");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
