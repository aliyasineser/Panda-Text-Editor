package text.editor.project;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 *
 * @author aliyasineser
 */
public class FtpBox {

    String ipAddress;
    String portNumber;
    String userID;
    String userPass;

    public static boolean display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("FTP Information");
        window.setMinWidth(400);

        Label textLabel = new Label();
        textLabel.setText("Please enter the ftp information.");

        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Name Label - constrains use (child, column, row)
        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        //Name Input
        TextField nameInput = new TextField("John");
        GridPane.setConstraints(nameInput, 1, 0);

        //Password Label
        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 1);

        //Password Input
        TextField passInput = new TextField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, 1, 1);

        //IP address Label
        Label ipAddrLabel = new Label("IP address:");
        GridPane.setConstraints(ipAddrLabel, 0, 2);

        //IP address Input
        TextField ipAddrInput = new TextField();
        ipAddrInput.setPromptText("127.0.0.1");
        GridPane.setConstraints(ipAddrInput, 1, 2);

        //Port Label
        Label portLabel = new Label("Port No:");
        GridPane.setConstraints(portLabel, 0, 3);

        //Port Input
        TextField portInput = new TextField();
        portInput.setPromptText("8000");
        GridPane.setConstraints(portInput, 1, 3);

        //Create two buttons
        Button finishButton = new Button("Save");
        GridPane.setConstraints(finishButton, 0, 4);
        
        Button cancelButton = new Button("Cancel");
        GridPane.setConstraints(cancelButton, 1, 4);

        //Clicking cancel will close the window only. 
        // If user confirms the information, program will try to connect 
        finishButton.setOnAction(e -> {

            window.close();
        });
        cancelButton.setOnAction(e -> {

            window.close();
        });

        //Add everything to grid
        grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, ipAddrLabel, ipAddrInput, portLabel, portInput, finishButton, cancelButton);

        VBox layout = new VBox(10);

        //Add buttons
        layout.getChildren().addAll(grid);
        layout.setAlignment(Pos.BOTTOM_CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        //Make sure to return answer
        return true;
    }

}
