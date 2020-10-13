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

public class Result {

    public Result(Request rq){resultDialog(rq);}

    public void resultDialog(Request rq){
        Stage stage = new Stage();
        stage.setTitle("Result");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(15,15,15,15));
        grid.setHgap(5);
        grid.setVgap(10);

        Label statusL = new Label("Status code:");

        TextField status = new TextField();
        status.setEditable(false);

        Label resultBodyL = new Label("Resul Body");

        TextField resultBofy = new TextField();
        resultBofy.setEditable(false);

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

        Scene scene = new Scene(grid,500,500);
        stage.setScene(scene);
        stage.show();
    }
}
