package org.openjfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.openjfx.Error.errorMessage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Postman Copy");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(15,15,15,15));
        grid.setHgap(5);
        grid.setVgap(10);

        Text title = new Text("Üdvözöllek a Postman Copyban!");
        title.setStyle("-fx-font-weight: bold");

        Label methodL = new Label("Method");

        ComboBox<String> methodC = new ComboBox<>();
        ObservableList<String> method = FXCollections.observableArrayList("GET", "POST", "DELETE");
        methodC.setItems(method);
        methodC.setEditable(false);
        methodC.setValue("Válassz!");

        Label urlL = new Label("Endpoint");

        TextField url = new TextField();
        url.setPrefWidth(400);

        Label headerL = new Label("Body");

        TextField header = new TextField();
        header.setPrefWidth(400);
        header.setPrefHeight(200);

        Label bodyL = new Label("Body");

        TextField body = new TextField();
        body.setPrefWidth(400);
        body.setPrefHeight(200);

        Button exit = new Button("Kilépés!");
        exit.setOnAction(e->{
            Platform.exit();
        });

        Button send = new Button("Send request");

        grid.add(title,0,0);
        grid.add(methodL,0,1);
        grid.add(methodC,1,1);
        grid.add(urlL,0,2);
        grid.add(url,1,2);
        grid.add(headerL,0,3);
        grid.add(header,1,3);
        grid.add(bodyL,0,4);
        grid.add(body,1,4);
        grid.add(exit,0,5);
        grid.add(send,1,5);

        send.setOnAction(e->{
            URL u=null;
            if(methodC.getValue().equals("Válassz!")){
                errorMessage("Methodot kötelező választani!","");
                return;
            }else if(url.getText().isEmpty()){
                errorMessage("Az endpoint nem megfelelő","");
                return;
            }
            /*try {
                String urlString = url.toString();
                u = new URL(urlString);
            } catch (MalformedURLException ex) {
                errorMessage("Az endpoint nem megfelelő","");
                ex.printStackTrace();
            }*/
            Request rq = new Request();
            rq.method=methodC.getValue();
            rq.url=url.getText();
            //rq.url=u;
            rq.body=body.getText();
            rq.header=header.getText();
            new Result(rq);
        });

        var scene = new Scene(grid, 750, 750);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

}