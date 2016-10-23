package editor;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    Stage window;
    private Desktop desktop = Desktop.getDesktop();
    public BorderPane borderPane;
    final HTMLEditor htmlEditor = new HTMLEditor();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        htmlEditor.setPrefHeight(400);
        borderPane.setCenter(htmlEditor);

    }

    public void openTextFile() {
        FileChooser fileChooser = new FileChooser();
        configureFileChooserOpen(fileChooser);
        File file = fileChooser.showOpenDialog(window);

        if (file != null) {
            openFile(file);
        }
    }

    private void openFile(File file) {
        try {
            desktop.open(file);

        } catch (Exception ex) {
            Logger.getLogger(
                    TextEditor.class
                    .getName()).log(
                            Level.SEVERE, null, ex
                    );
        }

    }

    public void saveTextFile() {

        FileChooser fileChooser = new FileChooser();
        configureFileChooserSave(fileChooser);
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            // Buraya save islemi yapilacak
        } else {
            // Buraya, elimizde yeni dosya olusturacak kod eklenecek
        }
    }

    public void saveToFTP() throws Exception {

        Stage secondWindow = new Stage();
        secondWindow.initModality(Modality.APPLICATION_MODAL);
        secondWindow.setTitle("FTP Information");

        Parent ftpSaveLayout = FXMLLoader.load(getClass().getResource("FtpBoxDesign.fxml"));

        Scene scene = new Scene(ftpSaveLayout);
        secondWindow.setScene(scene);
        secondWindow.showAndWait();

    }

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

    public void closeProgram() {
        ((Stage) (borderPane.getScene().getWindow())).close();
    }

    private static void configureFileChooserSave(final FileChooser fileChooser) {
        fileChooser.setTitle("Save file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"), "/Desktop"));

        FileChooser.ExtensionFilter pteFilter = new FileChooser.ExtensionFilter("PTF files (*.ptf)", "*.ptf");
        fileChooser.getExtensionFilters().add(pteFilter);

    }

    public void printText() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            job.showPrintDialog(null);
            htmlEditor.print(job);
            job.endJob();
        }
    }

}
