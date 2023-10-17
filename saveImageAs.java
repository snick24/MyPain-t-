package com.example.maspain;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class saveImageAs {
    // Method for saving the content of a Canvas as an image with a specified file format
    /**
     * Saves the visual content of a JavaFX Canvas to an image file with a specified format.
     * By opening a file choser and allowing the user to pick where and what the user wants to save their image.
     *
     * @param canvas The JavaFX Canvas whose content the image is being saved as
     **/
    public void saveImgAs(Canvas canvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As"); // Set the title of the file chooser dialog

        // Add extension filters for common image formats
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF Files", "*.gif"),
                new FileChooser.ExtensionFilter("BMP Files", "*.bmp")
        );

        File selectedFile = fileChooser.showSaveDialog(null); // Show the file save dialog

        if (selectedFile != null) { // Check if a file was selected
            // Determine the image format based on the file name extension (e.g., png or jpg)
            String format = selectedFile.getName().toLowerCase().endsWith(".png") ? "png" : "jpg";

            int width = (int) canvas.getWidth(); // Get the width of the Canvas
            int height = (int) canvas.getHeight(); // Get the height of the Canvas

            // Create a WritableImage from the Canvas content
            WritableImage writableImage = new WritableImage(width, height);
            canvas.snapshot(new SnapshotParameters(), writableImage);

            // Converting Writable image to Buffered Image
            BufferedImage bImage = SwingFXUtils.fromFXImage(writableImage, null);
            boolean userConfirmed = showDataLossWarning();
            if (userConfirmed) {
                try {
                    // Write the BufferedImage to the selected file with the determined format
                    ImageIO.write(bImage, format, selectedFile);
                } catch (IOException e) {
                    e.printStackTrace(); // Handle any input/output exceptions that may occur during saving
                }
            }
        }
    }

    private boolean showDataLossWarning() {
        // You can use an alert or dialog to display a warning message to the user.
        // Here's a basic example:

        Alert dataLossAlert = new Alert(Alert.AlertType.WARNING);
        dataLossAlert.setTitle("Data Loss Warning");
        dataLossAlert.setHeaderText(null);
        dataLossAlert.setContentText("Saving in a different format may result in data loss or quality changes. Do you want to proceed?");

        // Add Yes and No buttons
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        dataLossAlert.getButtonTypes().setAll(yesButton, noButton);

        // Show the alert and wait for the user's choice
        Optional<ButtonType> result = dataLossAlert.showAndWait();

        // Return true if the user confirmed (clicked Yes)
        return result.isPresent() && result.get() == yesButton;
    }
}