package com.example.maspain;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class NotificationManager {
    public static void showNotification(String message, Window ownerWindow) {
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.initOwner(ownerWindow);

        VBox notificationBox = new VBox();

        Label label = new Label(message);
        label.setStyle("-fx-text-fill: blue;");

        notificationBox.getChildren().add(label);
        Scene scene = new Scene(notificationBox);
        stage.setScene(scene);
        stage.show();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> stage.close())
        );
        timeline.play();
    }
}