package text.editor.project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.web.HTMLEditor;
import javafx.scene.layout.*;

/**
 *
 * @author aliyasineser
 */
public class TextEditorProject extends Application {

    Stage window;
    BorderPane layout;
    
    
    @Override
    public void start(Stage primaryStage) {
       
        //File menu
        Menu fileMenu = new Menu("File");
        MenuItem newFile = new MenuItem("New...");
        newFile.setOnAction(e -> System.out.println("Create a new file..."));
        fileMenu.getItems().add(newFile);
        fileMenu.getItems().add(new MenuItem("Open..."));
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
    
}
