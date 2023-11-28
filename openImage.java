package com.example.maspain;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

/**
 * The openImage class provides a method to open and display image files on a canvas.
 * It allows users to select an image file (PNG, JPG, JPEG, GIF, BMP) using a file dialog and display the selected image on the provided canvas.
 */
public class openImage {

    /**
     * Opens an image file dialog, allows the user to select an image, and displays it on the provided canvas.
     *
     * @param primaryStage The primary stage/window of the application.
     * @param canvas       The canvas where the selected image will be displayed.
     * @return The File object representing the selected image file.
     * Returns null if no image file was selected.
     */
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
