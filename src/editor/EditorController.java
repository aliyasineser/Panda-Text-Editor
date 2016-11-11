package editor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author aliya
 */
public class EditorController implements Initializable {

    private Stage window;
    private Desktop desktop = Desktop.getDesktop();
    public BorderPane borderPane;
    final HTMLEditor htmlEditor = new HTMLEditor();
    
    // Dosya şifrelemede kullanılan password.
    public static String receivedPassword = null; //askpassword ile aldigi o anlik sifre
    
    public static boolean sign = false;
    //dogruluk kontrolleri fonksiyonlar icinde yapiliyor
    //askpasswordden return ediliyor
    
    final Label labelFile = new Label();

    final TextArea textArea = TextAreaBuilder.create()
            .prefWidth(400)
            .wrapText(true)
            .build();

    // son girilen directory ve passwordler sayesinde ctrl+s ile hızlıca kayıt yapılabilecek
    private String lastDirectory = null;
    private String lastPassword = null;
    private String lastText = "";
    private String lastSavedText = "";
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        htmlEditor.setPrefHeight(400);
        borderPane.setCenter(htmlEditor);
        htmlEditor.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                lastText = stripHTMLTags(htmlEditor.getHtmlText());
            }
        });
    }
    
    public static String getReceivedPassword() {
        return receivedPassword;
    }
    /**
     * This method create an menu and getting password to encrypt file.
     * @return password which is received from user.
     * @throws Exception FXML loader can throw exception.
     */
    public static String askPassword() throws Exception {
        Stage passWindow = new Stage();
        passWindow.initModality(Modality.APPLICATION_MODAL);
        passWindow.setTitle("Enter password to encrypt file");
       
        Parent passLayout = FXMLLoader.load(new URL("file:src/editor/PasswordDesign.fxml"));
        
        Scene thisScene = new Scene(passLayout);
        passWindow.setOnCloseRequest(event -> {
           sign = true;
                    
        });
        passWindow.setScene(thisScene);
        passWindow.showAndWait();
        return getReceivedPassword();
    }

    public void newTextFile() {
        if (isTextChanged() && askSaveChanges()) {
            saveTextFile();
        }

        htmlEditor.setHtmlText("");
        lastText = "";
        lastSavedText = "";
        lastDirectory = null;
    }
    
    private boolean isTextChanged() {
        return !(lastText.equals(lastSavedText));
    }

    /**
     * TODO
     * @return 
     */
    public boolean askSaveChanges() {
        TODO:
        // return true for yes
        // return false for no

        return false;
    }

    public void openTextFile() throws Exception {
        if (isTextChanged() && askSaveChanges()) {
            saveTextFile();
        }

        FileChooser fileChooser = new FileChooser();
        configureFileChooserOpen(fileChooser);
        File file = fileChooser.showOpenDialog(window);

        if (file == null) {
            return; // path secimi iptal edildi
        }
        labelFile.setText(file.getPath());

        try {
            if (file.getName().endsWith(".ptf")) {
                Path path = Paths.get(file.getPath());

                byte[] encryptedBytes = Files.readAllBytes(path);
                String password = askPassword();
                //System.out.println(password);
                if (password == null) {
                    return;
                }

                byte[] decryptedBytes = Cryption.decryptFile(encryptedBytes, password);

                while (decryptedBytes == null && password != null) {
                    // sifre yanlis tekrar sor
                    if (sign) {//eger arayuzde cancel'a basilirsa !!
                        //dosyayi acmadan open islemini durdurur
                        return;
                    }
                    password = askPassword();
                    decryptedBytes = Cryption.decryptFile(encryptedBytes, password);
                }

                String txt = (String) (ByteArrayConverter.convertFromByteArray(decryptedBytes));
                htmlEditor.setHtmlText(txt);
                lastDirectory = file.getPath();
            } else {
                htmlEditor.setHtmlText(readFile(file));
                lastDirectory = null;
            }

            lastText = htmlEditor.getHtmlText();
            lastSavedText = lastText;
        } catch (Exception ex) {
            Stage errorWindow = new Stage();
            errorWindow.initModality(Modality.APPLICATION_MODAL);
            errorWindow.setTitle("Error");

            Parent errorLayout = FXMLLoader.load(new URL("file:src/editor/ErrorBox.fxml"), new MyResources("Error", "File couldn't opened."));

            Scene scene = new Scene(errorLayout);
            errorWindow.setScene(scene);
            errorWindow.showAndWait();
        }
    }

    public static String readFile(File file) throws Exception {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
                stringBuffer.append("\n");
            }

        } catch (FileNotFoundException ex) {
            Stage errorWindow = new Stage();
            errorWindow.initModality(Modality.APPLICATION_MODAL);
            errorWindow.setTitle("Error");

            Parent errorLayout = FXMLLoader.load(new URL("file:src/editor/ErrorBox.fxml"), new MyResources("Error", "File couldn't found."));

            Scene scene = new Scene(errorLayout);
            errorWindow.setScene(scene);
            errorWindow.showAndWait();
        } catch (IOException ex) {
            Stage errorWindow = new Stage();
            errorWindow.initModality(Modality.APPLICATION_MODAL);
            errorWindow.setTitle("Error");

            Parent errorLayout = FXMLLoader.load(new URL("file:src/editor/ErrorBox.fxml"), new MyResources("Error", "File couldn't opened."));

            Scene scene = new Scene(errorLayout);
            errorWindow.setScene(scene);
            errorWindow.showAndWait();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Stage errorWindow = new Stage();
                errorWindow.initModality(Modality.APPLICATION_MODAL);
                errorWindow.setTitle("Error");

                Parent errorLayout = FXMLLoader.load(new URL("file:src/editor/ErrorBox.fxml"), new MyResources("Error", "File is corrupted."));

                Scene scene = new Scene(errorLayout);
                errorWindow.setScene(scene);
                errorWindow.showAndWait();
            }
        }
        return stringBuffer.toString();
    }

    public void saveTextFile() {

        FileChooser fileChooser = new FileChooser();
        configureFileChooserSave(fileChooser);
        File file = fileChooser.showSaveDialog(window);

        if (file == null) {
            return; // path secimi iptal edildi
        }
        String directory = file.toString();

        if (file.getName().lastIndexOf('.') != -1) {
            directory = directory.substring(0, directory.lastIndexOf('.'));
        }

        try {
            saveTextFile(directory + ".ptf");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveTextFile(String directory) throws Exception {
        File file = new File(directory);

        String password = askPassword();
        if (DirSave.save(file, htmlEditor.getHtmlText(), password)) {
            lastDirectory = file.getPath();
            lastPassword = password;
            lastText = htmlEditor.getHtmlText();
            lastSavedText = lastText;
        } else {
            Stage errorWindow = new Stage();
            errorWindow.initModality(Modality.APPLICATION_MODAL);
            errorWindow.setTitle("Error");

            Parent errorLayout = FXMLLoader.load(getClass().getResource("ErrorBox.fxml"), new MyResources("Error", "File couldn't save."));

            Scene scene = new Scene(errorLayout);
            errorWindow.setScene(scene);
            errorWindow.showAndWait();
        }
    }

    // bu fonksiyon ctrl+S icin
    public void quickSaveTextFile() throws Exception {
        if (lastDirectory == null) {
            saveTextFile();
        } else if (lastPassword == null) {
            saveTextFile(lastDirectory);
        } else if (!DirSave.save(new File(lastDirectory), htmlEditor.getHtmlText(), lastPassword)) {
            Stage errorWindow = new Stage();
            errorWindow.initModality(Modality.APPLICATION_MODAL);
            errorWindow.setTitle("Error");

            Parent errorLayout = FXMLLoader.load(new URL("file:src/editor/ErrorBox.fxml"), new MyResources("Error", "Wrong Password."));

            Scene scene = new Scene(errorLayout);
            errorWindow.setScene(scene);
            errorWindow.showAndWait();
        } else {
            lastText = htmlEditor.getHtmlText();
        }
    }

    /**
     * Calls FTP Save Box and saves text to given address
     *
     * @throws Exception
     */
    public void saveToFTP() throws Exception {

        Stage secondWindow = new Stage();
        secondWindow.initModality(Modality.APPLICATION_MODAL);
        secondWindow.setTitle("FTP Information");

        Parent ftpSaveLayout = FXMLLoader.load(getClass().getResource("FtpBoxDesign.fxml"), new MyResources("", "", htmlEditor.getHtmlText()));

        Scene scene = new Scene(ftpSaveLayout);
        secondWindow.setScene(scene);
        secondWindow.showAndWait();

    }

    /**
     * This method configure file chooser menu.User can choose txt files,ptf files,
     * and all files
     * @param fileChooser fileChooser object.
     */
    private static void configureFileChooserOpen(final FileChooser fileChooser) {
        fileChooser.setTitle("Open file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"), "/Desktop"));

        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter ptfFilter = new FileChooser.ExtensionFilter("PTF files (*.ptf)", "*.ptf");
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All files (*)", "*");

        fileChooser.getExtensionFilters().add(ptfFilter);
        fileChooser.getExtensionFilters().add(txtFilter);
        fileChooser.getExtensionFilters().add(allFilter);

    }

    /**
     * Exiting from Program.
     */
    public void closeProgram() {
        if (isTextChanged() && askSaveChanges()) {
            saveTextFile();
        }

        ((Stage) (borderPane.getScene().getWindow())).close();
    }

    /**
     * This method configure file chooser menu.User want to save file to anywhere
     * This method choose directory which will save there.
     * @param fileChooser 
     */
    private static void configureFileChooserSave(final FileChooser fileChooser) {
        fileChooser.setTitle("Save file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"), "/Desktop"));

        FileChooser.ExtensionFilter pteFilter = new FileChooser.ExtensionFilter("PTF files (*.ptf)", "*.ptf");
        fileChooser.getExtensionFilters().add(pteFilter);

    }

    /**
     * Prints the text
     */
    public void printText() {

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            if (job.showPrintDialog(null)) {
                htmlEditor.print(job);
                job.endJob();
            }
        }
    }

    private String stripHTMLTags(String htmlText) {

        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuffer sb = new StringBuffer(htmlText.length());
        while (matcher.find()) {
            matcher.appendReplacement(sb, " ");
        }
        matcher.appendTail(sb);
        return (sb.toString().trim());

    }
}
