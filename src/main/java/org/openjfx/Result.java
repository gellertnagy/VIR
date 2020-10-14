package org.openjfx;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.openjfx.Error.errorMessage;

public class Result {

    public Result(Request rq){sendRequest(rq);}

    private static HttpURLConnection connection;

    public void sendRequest(Request rq){
        Response rs = new Response();
        try {
            URL url = new URL(rq.getUrl());
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(rq.getMethod());
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            rs.setResponseCode(connection.getResponseCode());
            rs.setResponseBody(connection.getResponseMessage());

            resultDialog(rs);

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resultDialog(Response rs){
        Stage stage = new Stage();
        stage.setTitle("Result");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(15,15,15,15));
        grid.setHgap(5);
        grid.setVgap(10);

        if (rs.getResponseCode()==0 || rs.getResponseBody().isEmpty()){
            errorMessage("Hiba történt!","");
        }

        Label statusL = new Label("Status code:");

        TextField status = new TextField();
        status.setEditable(false);
        //status.setText(rs.getResponseCode());

        Label resultBodyL = new Label("Resul Body");

        TextField resultBofy = new TextField();
        resultBofy.setEditable(false);
        resultBofy.setText(rs.getResponseBody());

        Button back = new Button("Vissza");
        back.setOnAction(e->{
            stage.close();
        });

        Button ujra = new Button("Újraküldés");

        Button exit = new Button("Kilépés");
        exit.setOnAction(e->{
            Platform.exit();
        });

        /*
        Innen kezdődik a HTTP kérés
         */



        /*
        És a vége
         */

        grid.add(statusL,0,0);
        grid.add(status,1,0);
        grid.add(resultBodyL,0,1);
        grid.add(resultBofy,1,1);
        grid.add(back,0,2);
        grid.add(ujra,1,2);
        grid.add(exit,2,2);

        Scene scene = new Scene(grid,500,500);
        stage.setScene(scene);
        stage.show();
    }
}
