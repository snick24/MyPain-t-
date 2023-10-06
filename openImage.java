package com.example.maspain;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class openImage {
    // Method for opening an image and displaying it on a canvas
    public File openImg(Stage primaryStage, Canvas canvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        File selectedFile = fileChooser.showOpenDialog(primaryStage); // Show a file dialog to select an image

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString()); // Loading the selected image
            GraphicsContext gc = canvas.getGraphicsContext2D();

            canvas.setWidth(image.getWidth()); // Set canvas width to match image width
            canvas.setHeight(image.getHeight()); // Set canvas height to match image height
            gc.drawImage(image, 0, 0); // Draw the image on the canvas at (0,0)
        }
        return selectedFile; // Return the selected file
    }
}
