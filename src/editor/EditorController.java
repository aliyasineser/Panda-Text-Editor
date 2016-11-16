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

/** Includes text editor button controller functions
 *
 * @author aliya
 */
public class EditorController implements Initializable {

    private Stage window;
    private final Desktop desktop = Desktop.getDesktop();
    public BorderPane borderPane;
    final HTMLEditor htmlEditor = new HTMLEditor();

    final Label labelFile = new Label();

    final TextArea textArea = TextAreaBuilder.create()
            .prefWidth(400)
            .wrapText(true)
            .build();

    // son girilen directory ve passwordler sayesinde ctrl+s ile hizlica kayit yapilabilecek
    private String lastDirectory = null;
    private String lastPassword = null;

    private String lastSavedText = null;

    /**
     * Initializes editor
     * 
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {

        htmlEditor.setPrefHeight(400);
        borderPane.setCenter(htmlEditor);
        lastSavedText = htmlEditor.getHtmlText();
        htmlEditor.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //may be useful later
            }
        });
    }

    /**
     * Creates new page.
     */
    public void newTextFile() {
        if (isTextChanged() && askSaveChanges()) {
            quickSaveTextFile();
        }

        htmlEditor.setHtmlText("");
        lastSavedText = "";
        lastDirectory = null;
    }

    /**
     * checks weather the text is changed since last save or not.
     * 
     * @return returns true if changed
     */
    private boolean isTextChanged() {
        return !(htmlEditor.getHtmlText().equals(lastSavedText));
    }

    /**
     * TODO
     *
     * @return
     */
    public boolean askSaveChanges() {
        TODO:
        // return true for yes
        // return false for no

        return false;
    }

    /**
     * Interacts with the user via FileChooser to get the file which will be
     * open.
     *
     * @throws Exception
     */
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
                
                byte[] decryptedBytes;
                String password;
                do{
                    // sifre yanlis tekrar sor
                    password = PasswordDesignController.askPassword();
                    if(password == null)
                        return;
                    
                    decryptedBytes = Cryption.decryptFile(encryptedBytes, password);
                    if(decryptedBytes == null){
                        ErrorBoxController.showErrorBox("Invalid Password!","Invalid Password!", "Password is invalid! Please try again.");
                    }
                }while (decryptedBytes == null);

                String txt = (String) (ByteArrayConverter.convertFromByteArray(decryptedBytes));
                htmlEditor.setHtmlText(txt);
                lastDirectory = file.getPath();
                lastPassword = password;
            } else {
                htmlEditor.setHtmlText(readFile(file));
                lastDirectory = null;
            }

            lastSavedText = htmlEditor.getHtmlText();
        } catch (Exception ex) {
            ErrorBoxController.showErrorBox("Error", "Cannot Open File!", "File could not be opened!");
        }
    }

    /**
     * Reads file from the given File object.
     *
     * @param file filePath
     * @return file context as String
     * @throws Exception
     */
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
            ErrorBoxController.showErrorBox("Error", "Cannot Find File!", "File could not be found!");
        } catch (IOException ex) {
            ErrorBoxController.showErrorBox("Error", "Cannot Open File!", "File could not be opened!");
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                ErrorBoxController.showErrorBox("Error", "Cannot Open File!", "File is corrupted!");
            }
        }
        return stringBuffer.toString();
    }

    /**
     * Saves file with encrypting, interacts with user via File Chooser to get the
     * path and name.
     */
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

        
        saveTextFile(directory + ".ptf");
    }

    /**
     * Takes save location as String and saves with encrypting.
     *
     * @param directory location path to save
     * @throws Exception
     */
    private void saveTextFile(String directory){
        File file = new File(directory);

        String password = PasswordDesignController.askPassword();
        if(password == null)
            return;
        
        if (DirSave.save(file, htmlEditor.getHtmlText(), password)) {
            lastDirectory = file.getPath();
            lastPassword = password;
            lastSavedText = htmlEditor.getHtmlText();
        } else {
            ErrorBoxController.showErrorBox("Error", "Cannot Save File!", "File could not be saved!");
        }
    }

    /**
     * Checks last directory and passwords used and uses them if they are existed.
     * This function designed for Ctrl+S.
     */
    public void quickSaveTextFile(){
        if (lastDirectory == null) {
            saveTextFile();
        } else if (lastPassword == null) {
            saveTextFile(lastDirectory);
        } else if (!DirSave.save(new File(lastDirectory), htmlEditor.getHtmlText(), lastPassword)) {
            ErrorBoxController.showErrorBox("Error", "Cannot Save File!", "File could not be saved!");
        } else {
            lastSavedText = htmlEditor.getHtmlText();
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
     * This method configure file chooser menu.User can choose txt files,ptf
     * files, and all files
     *
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
     * This method configure file chooser menu.User want to save file to
     * anywhere This method choose directory which will save there.
     *
     * @param fileChooser
     */
    private static void configureFileChooserSave(final FileChooser fileChooser) {
        fileChooser.setTitle("Save file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "/Desktop"));

        FileChooser.ExtensionFilter pteFilter = new FileChooser.ExtensionFilter("PTF files (*.ptf)", "*.ptf");
        fileChooser.getExtensionFilters().add(pteFilter);

    }

    /**
     * Written by Hasan Bilgin
     * Prints text from printer.
     * 
     */
    public void printText() {
        PrinterJob job = PrinterJob.createPrinterJob();
        /* Javanin printer a erismek icin kullandigi PrinterJob nesnesi
           olusturuldu*/
        if (job != null) {
            // showPrintDialog Hangi yazicidan cikti alinacagini secmek icin
            // bir dialog box olusturur
            if (job.showPrintDialog(null)) {
                // Yazdirilacak metini secilen printera yollar
                htmlEditor.print(job);
                // Yazdirma islemini tamamlar 
                job.endJob();
            }
        }
        /* Printer metodu linuxta calismadigi tespit edilmisti
           bunun sebebinin yazici secme ekranin gelmemesi oldugu
           farkedildi bu sorunun linux da kurulu bir yazici driveri 
           olmadigi icin olustugu tespit edildi.
        
           Bunun uzerine linux pdf printer programi olan cups-pdf
           ve bir HP yazici driveri kurularak test edildi ikisinde
           de program calisti.
            */
    }
    
    /**
     * Strips htmlText.
     * may be useful later.
     */
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
