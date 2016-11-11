package editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author aliya
 */
public class TextEditor extends Application {
    
    private static String directoryPath = EditorAssets.passwordDirectory;
    private static String fileName = EditorAssets.passwordFile;
    
    // Kullanıcının programı açarken girmesi gereken şifre.
    // Eğer kullanıcı daha önce programa girmişse girdiği bu şifre linuxta bir klasörün
    // Altında saklanacak eğer programa ilk defa giriyorsa kullanıcıya şifre oluşturması için
    // Bir pencere çıkarılacak.(CreatePasswordController.fxml ve CreatePasswordController.java)
    // Bu pencerede şifre ve şifre tekrarı için iki password field bulunacak.Girilen iki değer doğru olduğunda
    // Şifre belirlenen dosyaya kaydediecek ve kullanıcıya text editor gösterilecek.
    private static String passwordOfTheUser;
    
    // Şifre için getter.
    public static String getPasswordOfTheUser() {
        return passwordOfTheUser;
    }
    
    // Settera neden gerek var?
    // Eğer kullanıcı daha önce bir şifre oluşturmamışsa program ilk açıldığında
    // Şifre belirleme ekranı gelecek şifre başka bir class içinde belirleneceğinden
    // Dışarıda erişebilmek için bir setter gerekebilir.
    public static void setPasswordOfTheUser(String password){
        passwordOfTheUser = password;
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
        passwordOfTheUser = getUserPassword();
        if(passwordOfTheUser == null){
            // TODO: kullanıcıya şifre oluşturma ekranı getir.
        }
        
        // Bu event handler editor bir değişiklik olup kaydedilmeden çıkılmaya çalışıldığında
        // Kullanıcıya uyarıda bulunulması için implement edilecek.
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
            
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private String getUserPassword() throws IOException {
        
        // Try catch bloğu eklenerek exception fırlatması engellenebilir.
        // Dosya bulunamaması durumunda yeniden oluşturulur.
        
        // If directory not exist create directory.
        File directory = new File(directoryPath);
        directory.mkdirs();

        // If file not exist create file.
        File file = new File(directoryPath + fileName);
        file.createNewFile();
        
        // Create buffer reader.
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String password;
        
        // Get password.
        password = bufferedReader.readLine();
        
        bufferedReader.close();

        return password;

    }
}