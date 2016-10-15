package text.editor.project;

import java.awt.Desktop;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.web.HTMLEditor;
import javafx.print.PrinterJob;
import javafx.scene.layout.*;

/**
 *
 * @author aliyasineser
 */
public class TextEditorProject extends Application {

    Stage window;
    BorderPane layout;

    private Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(Stage primaryStage) {

        //File menu
        Menu fileMenu = new Menu("File");
        MenuItem newFile = new MenuItem("New...");
        newFile.setOnAction(e -> System.out.println("Create a new file..."));
        fileMenu.getItems().add(newFile);

        MenuItem openFile = new MenuItem("Open...");
        fileMenu.getItems().add(openFile);
        openFile.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            configureFileChooserOpen(fileChooser);
            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                openFile(file);
            }
        });
        MenuItem saveItem = new MenuItem("Save...");
        fileMenu.getItems().add(saveItem);
        saveItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            configureFileChooserSave(fileChooser);
            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                openFile(file);
            }
        });

        fileMenu.getItems().add(new MenuItem("Save to directory"));
        fileMenu.getItems().add(new MenuItem("Save to FTP"));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Settings"));
        MenuItem print = new MenuItem("Print");
        fileMenu.getItems().add(print);
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem exitProgram = new MenuItem("Exit");
        fileMenu.getItems().add(exitProgram);
        exitProgram.setOnAction(e -> {
            // Popup cikar.
            primaryStage.close();
        });

        //Main menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);

        final HTMLEditor htmlEditor = new HTMLEditor();

        htmlEditor.setPrefHeight(400);

        print.setOnAction(e -> {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null) {
                job.showPrintDialog(null);
                htmlEditor.print(job);
                job.endJob();
            }
        }
        );

        // Show Time
        layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(htmlEditor);
        primaryStage.setTitle("Text Editor");
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void openFile(File file) {
//        try {
//            desktop.open(file);
//        } catch (Exception ex) {
//            Logger.getLogger(
//                TextEditorProject.class.getName()).log(
//                    Level.SEVERE, null, ex
//                );
//        }

    }

    private static void configureFileChooserOpen(final FileChooser fileChooser) {
        fileChooser.setTitle("Open file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"), "/Desktop"));

        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All files (*)", "*");

        fileChooser.getExtensionFilters().add(txtFilter);
        fileChooser.getExtensionFilters().add(allFilter);
    }

    private static void configureFileChooserSave(final FileChooser fileChooser) {
        fileChooser.setTitle("Save file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"), "/Desktop"));

        FileChooser.ExtensionFilter pteFilter = new FileChooser.ExtensionFilter("PTE files (*.pte)", "*.pte");
        fileChooser.getExtensionFilters().add(pteFilter);

    }

}
