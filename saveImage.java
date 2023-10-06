package com.example.maspain;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class saveImage {
    // Method for saving a Canvas content as an image to a specified file
    public void saveImg(Canvas canvas, File imageFile) {
        if (canvas != null && imageFile != null) {
            int width = (int) canvas.getWidth(); // Get the width of the Canvas
            int height = (int) canvas.getHeight(); // Get the height of the Canvas

            // Create a writable image with the same dimensions as the Canvas
            WritableImage writableImage = new WritableImage(width, height);
            canvas.snapshot(new SnapshotParameters(), writableImage); // Take a snapshot of the Canvas content

            // Converting the Writable Image to a Buffered Image
            BufferedImage bImage = SwingFXUtils.fromFXImage(writableImage, null);

            String fileName = imageFile.getName().toLowerCase();
            String format = "png"; // Default to PNG

            if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                format = "jpg";
            } else if (fileName.endsWith(".bmp")) {
                format = "bmp";
            }
            try {
                // Write the BufferedImage to the specified image file
                ImageIO.write(bImage, format, imageFile);
            } catch (IOException e) {
                e.printStackTrace(); // Handle any input/output exceptions that may occur
            }
        }
    }
}