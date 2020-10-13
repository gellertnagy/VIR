package org.openjfx;

import javafx.scene.control.Alert;

public class Error {
    public static void errorMessage(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba történt!");
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
