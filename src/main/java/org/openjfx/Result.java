package org.openjfx;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.openjfx.Error.errorMessage;

public class Result {

    public Result(Request rq){sendRequest(rq);}

    private static HttpURLConnection connection;
    BufferedReader reader;
    String line;
    StringBuffer responseContent = new StringBuffer();

    public void sendRequest(Request rq){
        Response rs = new Response();
        try {
            URL url = new URL(rq.getUrl());
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(rq.getMethod());
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if(connection.getResponseCode() > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            rs.setResponseCode(connection.getResponseCode());
            rs.setResponseBody(responseContent.toString());

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

        Spinner<Integer> status = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(rs.getResponseCode(),rs.getResponseCode(),rs.getResponseCode());
        status.setValueFactory(valueFactory);
        status.setEditable(false);

        Label resultBodyL = new Label("Resul Body");

        TextField resultBofy = new TextField();
        resultBofy.setEditable(false);
        resultBofy.setText(rs.getResponseBody());
        resultBofy.setPrefWidth(400);
        resultBofy.setPrefHeight(200);


        Button back = new Button("Vissza");
        back.setOnAction(e->{
            stage.close();
        });

        Button ujra = new Button("Újraküldés");

        Button exit = new Button("Kilépés");
        exit.setOnAction(e->{
            Platform.exit();
        });


        grid.add(statusL,0,0);
        grid.add(status,1,0);
        grid.add(resultBodyL,0,1);
        grid.add(resultBofy,1,1);
        grid.add(back,0,2);
        grid.add(ujra,1,2);
        grid.add(exit,2,2);

        Scene scene = new Scene(grid,750,750);
        stage.setScene(scene);
        stage.show();
    }
}
