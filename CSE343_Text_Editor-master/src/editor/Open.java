/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.FileChooser;

/**
 *sifrelemeler eklenecek 
 * sifre kontrolleri eklenecek
 * ekrana basarken \n algilamiyor
 * 
 * @author ilayda
 */
public class Open {
    private TextArea text;
    private String password;

    public Open() {
        this.text = null;
        this.password = null;
    }
    
    
    
    public void setText(TextArea text) {
        this.text = text;
    }

    public TextArea getText() {
        return text;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    
    public String readFile(File file){
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
            Logger.getLogger(Open.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Open.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Open.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         //System.out.print( stringBuffer.toString());
        return stringBuffer.toString();
    }
    
    
    public boolean ControlPassword(){
        return false;
    }
    
    
    
 
}
