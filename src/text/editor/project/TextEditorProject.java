package text.editor.project;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.web.HTMLEditor;
import java.io.*;
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
            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                openFile(file);
            }
        });
        
        
        fileMenu.getItems().add(new MenuItem("Save..."));
        fileMenu.getItems().add(new MenuItem("Save to..."));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Settings"));
        fileMenu.getItems().add(new MenuItem("Print"));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Exit"));
        
        
        
        //Main menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);
        
        
        final HTMLEditor htmlEditor = new HTMLEditor();
        
        htmlEditor.setPrefHeight(400);  
        
        
        // Sol tarafa File Explorer set et !!! 
        
        
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
    
    
    private void openFile(File file){
//        try {
//            desktop.open(file);
//        } catch (Exception ex) {
//            Logger.getLogger(
//                TextEditorProject.class.getName()).log(
//                    Level.SEVERE, null, ex
//                );
//        }
        
    }
    
}
