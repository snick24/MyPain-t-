package com.example.maspain;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The saveImage class provides a method to save the visual content of a JavaFX Canvas as an image file.
 * It enables users to save the content of the Canvas to specified file formats like PNG, JPEG, or BMP.
 */
public class saveImage {

    /**
     * The `saveImg` method allows you to save the visual content of a Canvas to a user-specified file format,
     * with options to save as PNG, JPEG, or BMP formats.
     * <p>
     * It does so by creating a writeable image and saving a screenshot of the canvas to the file location
     *
     * @param canvas The JavaFX Canvas whose content you want to save as an image.
     * @param imageFile The target file where the image will be saved.
     **/
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