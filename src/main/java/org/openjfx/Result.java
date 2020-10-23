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
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

//import static jdk.internal.net.http.HttpRequestImpl.USER_AGENT;
import static org.openjfx.Error.errorMessage;

public class Result {

    public Result(Request rq) throws IOException {sendRequest(rq);}

    private static HttpURLConnection connection;
    BufferedReader reader;
    String line;
    StringBuffer responseContent = new StringBuffer();

    public void sendRequest(Request rq) throws IOException {
       Response rs = new Response();
       if(rq.getMethod()=="GET"){
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
       }else if (rq.getMethod()=="POST"){
           URL obj = new URL(rq.getUrl());
           HttpURLConnection con = (HttpURLConnection) obj.openConnection();
           con.setRequestMethod(rq.getMethod());

           con.setDoOutput(true);
           OutputStream os = con.getOutputStream();
           os.write(rq.getBody().getBytes());
           os.flush();
           os.close();

           int responseCode = con.getResponseCode();
           rs.setResponseCode(responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                       con.getInputStream()));
               String inputLine;
               StringBuffer response = new StringBuffer();

               while ((inputLine = in.readLine()) != null) {
                   response.append(inputLine);
               }
               in.close();
               rs.setResponseBody(response.toString());
           resultDialog(rs);
       }else{
           errorMessage("Hiba történt","Hiba!");
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
        //grid.add(ujra,1,2);
        grid.add(exit,1,2);

        Scene scene = new Scene(grid,750,750);
        stage.setScene(scene);
        stage.show();
    }
}
