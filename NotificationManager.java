
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

/**
 * The NotificationManager class provides methods to display notifications as temporary pop-up messages.
 * It contains a method to show a notification message with a specified duration and owner window.
 */
public class NotificationManager {

    /**
     * Displays a notification message as a temporary pop-up for a specified duration.
     * @param message      The message to be displayed in the notification.
     * @param ownerWindow  The owner window for the notification.The notification will be displayed relative to this window.
     */
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

        // Close the notification window after a specified duration (3 seconds)
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> stage.close())
        );
        timeline.play();
    }
}
