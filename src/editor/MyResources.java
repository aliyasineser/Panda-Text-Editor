/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 *
 * @author aliya
 */
public class MyResources extends ResourceBundle {

    private String message;
    private String description;
    private String htmlText;

    public MyResources(String message, String description, String htmlText) {
        this.message = message;
        this.description = description;
        this.htmlText = htmlText;
    }

    public MyResources(String message, String description) {
        this(message, description, "");
    }

    public MyResources(String message) {
        this(message, "", "");
    }

    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public Object handleGetObject(String key) {
        if (key.equals("okKey")) {
            return "Ok";
        }
        if (key.equals("message")) {
            return message;
        }
        if (key.equals("description")) {
            return description;
        }
        if (key.equals("text")) {
            return htmlText;
        }

        return null;
    }

    public Enumeration<String> getKeys() {
        return Collections.enumeration(keySet());
    }

    // Overrides handleKeySet() so that the getKeys() implementation
    // can rely on the keySet() value.
    protected Set<String> handleKeySet() {
        return new HashSet<String>(Arrays.asList("okKey", "message", "description", "text"));
    }
}
