package com.example.maspain;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;



import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * The `DrawingCanvas` class extends `Canvas` and provides a set of methods for drawing various shapes
 * and performing actions on the canvas, including pencil drawing, erasing, drawing straight lines, squares, circles,
 * half circles, polygons, taking snapshots, and undo/redo functionality.
 * <p>
 * This class allows you to create a drawing canvas with specified width and height, set various drawing options,
 * change line width, line color, and more.
 **/
public class DrawingCanvas extends Canvas {
    private final GraphicsContext gc; // Graphics context for drawing on the canvas
    private double startX, startY;
    private int lwidth = 2; // Initial line width
    private Color ccolor = Color.BLACK;
    private double snapshotStartX, snapshotStartY;
    private final Stack<Image> undoStack = new Stack<>();
    private final Stack<Image> redoStack = new Stack<>();


    /**
     * Constructs a DrawingCanvas with the specified width and height.
     * Initializes the canvas, sets up the GraphicsContext for drawing,
     * and sets the initial stroke color to black and line width.
     *
     * @param width  The width of the canvas.
     * @param height The height of the canvas.
     */
    public DrawingCanvas(double width, double height) {
        super(width, height); // Call the constructor of the Canvas class with specified width and height
        gc = getGraphicsContext2D(); // Get the GraphicsContext for drawing on the canvas
        gc.setStroke(ccolor); // Set the initial stroke color to black
        gc.setLineWidth(lwidth); // Set the initial line width
    }
    /**
     * Method to allow the User to use their mouse without
     * any tool interfering
     */
    public void pointer() {
        setOnMousePressed(null);
        setOnMouseDragged(null);
        setOnMouseReleased(null);
    }
    /**
     * Method to draw text on the canvas at the mouse click position
     * @param text The text to be drawn
     */
    public void text(String text) {
        setOnMousePressed(event -> {
            double x = event.getX();
            double y = event.getY();
            gc.setFill(Color.BLACK); // Set the text color
            gc.fillText(text, x, y); // Draw the text at the mouse click position
        });
        setOnMouseDragged(null);
        setOnMouseReleased(null);
    }

    /**
     * Method to enable pencil drawing
     */
    public void pencil1() {
        setOnMousePressed(this::startDrawing); // When the mouse is pressed, call startDrawing method
        setOnMouseDragged(this::continueDrawing); // When the mouse is dragged, call continueDrawing method
        setOnMouseReleased(this::stopDrawing); // When the mouse is released, call stopDrawing method
    }

    /**
     * Method to start drawing with the pencil.
     * @param event The MouseEvent triggering the start of drawing.
     */
    private void startDrawing(MouseEvent event) {
        saveState();
        gc.beginPath(); // Begin a new path for drawing
        gc.lineTo(event.getX(), event.getY()); // Move the drawing cursor to the mouse click location
        gc.stroke(); // Perform the stroke operation to start drawing
    }

    /**
     * Continues drawing with the pencil tool while dragging the mouse.
     * @param event The MouseEvent triggering the continuation of drawing.
     */
    private void continueDrawing(MouseEvent event) {
        gc.lineTo(event.getX(), event.getY()); // Extend the path to the current mouse position
        gc.stroke(); // Perform the stroke operation to continue drawing
    }


    /**
     * Stops drawing with the pencil tool when the mouse is released.
     * @param event The MouseEvent triggering the end of drawing.
     */
    private void stopDrawing(MouseEvent event) {
        gc.closePath(); // Close the path, ending the drawing
    }

    /**
     * Method to enable eraser
     */
    public void eraser() {
        setOnMousePressed(this::startErasing); // When the mouse is pressed, call startDrawing method
        setOnMouseDragged(this::continueErasing); // When the mouse is dragged, call continueDrawing method
        setOnMouseReleased(this::stopErasing); // When the mouse is released, call stopDrawing method
    }

    /**
     * Starts erasing when the mouse is pressed.
     * @param event The MouseEvent triggering the start of erasing.
     */
    private void startErasing(MouseEvent event) {
        saveState();
        gc.setStroke(Color.WHITE);
        gc.beginPath(); // Begin a new path for erasing
        gc.lineTo(event.getX(), event.getY()); // Move the eraser to the mouse click location
        gc.stroke(); // Perform the stroke operation to start erasing
    }

    /**
     * Continues erasing while dragging the mouse.
     * @param event The MouseEvent triggering the continuation of erasing.
     */
    private void continueErasing(MouseEvent event) {
        gc.lineTo(event.getX(), event.getY()); // Extend the eraser path to the current mouse position
        gc.stroke(); // Perform the stroke operation to continue erasing
    }

    /**
     * Stops erasing when the mouse is released.
     * @param event The MouseEvent triggering the end of erasing.
     */
    private void stopErasing(MouseEvent event) {
        gc.closePath(); // Close the eraser path, ending erasing
        gc.setStroke(ccolor); // Reset the stroke color to the original color
    }

    /**
     * Method to enable drawing straight lines
     */
    public void straightl() {
        saveState();
        setOnMousePressed(this::startDrawings); // When the mouse is pressed, call startDrawing method
        setOnMouseDragged(null);
        setOnMouseReleased(this::stopDrawings); // When the mouse is released, call stopDrawing method
    }

    /**
     * Starts drawing a straight line when the mouse is pressed.
     * @param event The MouseEvent triggering the start of drawing a straight line.
     */
    private void startDrawings(MouseEvent event) {
        startX = event.getX();
        startY = event.getY();
    }

    /**
     * Stops drawing a straight line when the mouse is released
     * and a line is created between the two points.
     * @param event The MouseEvent triggering the start of drawing a straight line.
     */
    private void stopDrawings(MouseEvent event) {
        gc.beginPath();
        gc.moveTo(startX, startY);
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();
    }

    /**
     * Method to enable drawing squares
     */
    public void square() {
        setOnMousePressed(this::startSquareDrawing);
        setOnMouseDragged(null);
        setOnMouseReleased(this::stopSquareDrawing);
    }

    private double squareStartX, squareStartY;

    /**
     * Starts drawing a square when the mouse is pressed.
     * @param event The MouseEvent triggering the start of drawing a square.
     */
    private void startSquareDrawing(MouseEvent event) {
        saveState();
        squareStartX = event.getX();
        squareStartY = event.getY();
    }

    /**
     * Continues drawing a square while the mouse is being dragged.
     * Calculates the size and position based on the initial click and the current mouse position.
     * Draws a square using the calculated parameters.
     *
     * @param event The MouseEvent triggering the continuation of drawing a square.
     */
    private void continueSquareDrawing(MouseEvent event) {
        double currentX = event.getX();
        double currentY = event.getY();
        double width = Math.abs(currentX - squareStartX);
        double height = Math.abs(currentY - squareStartY);
        double size = Math.min(width, height);
        double minX = Math.min(squareStartX, currentX);
        double minY = Math.min(squareStartY, currentY);
        gc.strokeRect(minX, minY, size, size);
    }

    /**
     * Stops drawing a square when the mouse is released.
     * @param event The MouseEvent triggering the end of drawing a square.
     */
    private void stopSquareDrawing(MouseEvent event) {
        continueSquareDrawing(event);
    }

    /**
     * Method to enable drawing circles
     */
    public void circle() {
        setOnMousePressed(this::startCircleDrawing);
        setOnMouseDragged(null);
        setOnMouseReleased(this::stopCircleDrawing);
    }

    private double circleStartX, circleStartY;

    /**
     * Starts drawing a circle when the mouse is pressed.
     * @param event The MouseEvent triggering the start of drawing a circle.
     */
    private void startCircleDrawing(MouseEvent event) {
        saveState();
        circleStartX = event.getX();
        circleStartY = event.getY();
    }

    /**
     * Continues drawing a circle while the mouse is being dragged.
     * Calculates the radius and position based on the initial click and the current mouse position.
     * Draws a circle using the calculated parameters.
     *
     * @param event The MouseEvent triggering the continuation of drawing a circle.
     */
    private void continueCircleDrawing(MouseEvent event) {
        double currentX = event.getX();
        double currentY = event.getY();
        double radius = Math.sqrt(Math.pow(currentX - circleStartX, 2) + Math.pow(currentY - circleStartY, 2));
        gc.strokeOval(circleStartX - radius, circleStartY - radius, radius * 2, radius * 2);
    }

    /**
     * Stops drawing a circle when the mouse is released.
     * @param event The MouseEvent triggering the end of drawing a circle.
     */
    private void stopCircleDrawing(MouseEvent event) {
        continueCircleDrawing(event);
    }

    /**
     * Method to enable drawing half circles
     */
    public void halfcircle() {
        setOnMousePressed(this::startHalfCircleDrawing);
        setOnMouseDragged(null);
        setOnMouseReleased(this::stopHalfCircleDrawing);
    }

    private double halfCircleStartX, halfCircleStartY;

    /**
     * Starts drawing a half circle when the mouse is pressed.
     * @param event The MouseEvent triggering the start of drawing a half circle.
     */
    private void startHalfCircleDrawing(MouseEvent event) {
        saveState();
        halfCircleStartX = event.getX();
        halfCircleStartY = event.getY();
    }

    /**
     * Continues drawing a half circle while the mouse is being dragged.
     * Calculates the radius and position based on the initial click and the current mouse position.
     * Draws a half circle using the calculated parameters.
     *
     * @param event The MouseEvent triggering the continuation of drawing a half circle.
     */
    private void continueHalfCircleDrawing(MouseEvent event) {
        double currentX = event.getX();
        double currentY = event.getY();
        double radius = Math.sqrt(Math.pow(currentX - halfCircleStartX, 2) + Math.pow(currentY - halfCircleStartY, 2));
        double centerX = halfCircleStartX;
        double centerY = halfCircleStartY;
        // Draw a half circle based on the starting point and current position
        gc.beginPath();
        gc.arc(centerX, centerY, radius, radius, 0, 180);
        gc.stroke();
    }

    /**
     * Stops drawing a half circle when the mouse is released.
     * @param event The MouseEvent triggering the end of drawing a half circle.
     */
    private void stopHalfCircleDrawing(MouseEvent event) {
        continueHalfCircleDrawing(event);
    }

    /**
     * Method to enable drawing rectangles
     */
    public void rectangle() {
        setOnMousePressed(this::startRectangleDrawing);
        setOnMouseDragged(null);
        setOnMouseReleased(this::stopRectangleDrawing);
    }

    private double rectStartX, rectStartY;

    /**
     * Starts drawing a rectangle when the mouse is pressed.
     * @param event The MouseEvent triggering the start of drawing a rectangle.
     */
    private void startRectangleDrawing(MouseEvent event) {
        saveState();
        rectStartX = event.getX();
        rectStartY = event.getY();
    }

    /**
     * Continues drawing a rectangle while the mouse is being dragged.
     * Calculates the width and height based on the initial click and the current mouse position.
     * Draws a rectangle using the calculated parameters.
     *
     * @param event The MouseEvent triggering the continuation of drawing a rectangle.
     */
    private void continueRectangleDrawing(MouseEvent event) {
        double currentX = event.getX();
        double currentY = event.getY();
        double width = Math.abs(currentX - rectStartX);
        double height = Math.abs(currentY - rectStartY);
        gc.strokeRect(rectStartX, rectStartY, width, height);
    }

    /**
     * Stops drawing a rectangle when the mouse is released.
     * @param event The MouseEvent triggering the end of drawing a rectangle.
     */
    private void stopRectangleDrawing(MouseEvent event) {
        continueRectangleDrawing(event);
    }

    /**
     * Method to enable drawing ellipses
     */
    public void ellipse() {
        setOnMousePressed(this::startEllipseDrawing);
        setOnMouseDragged(null);
        setOnMouseReleased(this::stopEllipseDrawing);
    }

    private double ellipseStartX, ellipseStartY;

    /**
     * Starts drawing an ellipse when the mouse is pressed.
     * @param event The MouseEvent triggering the start of drawing an ellipse.
     */
    private void startEllipseDrawing(MouseEvent event) {
        saveState();
        ellipseStartX = event.getX();
        ellipseStartY = event.getY();
    }

    /**
     * Continues drawing an ellipse while the mouse is being dragged.
     * Calculates the width and height based on the initial click and the current mouse position.
     * Draws an ellipse using the calculated parameters.
     *
     * @param event The MouseEvent triggering the continuation of drawing an ellipse.
     */
    private void continueEllipseDrawing(MouseEvent event) {
        double currentX = event.getX();
        double currentY = event.getY();
        double width = Math.abs(currentX - ellipseStartX);
        double height = Math.abs(currentY - ellipseStartY);
        gc.strokeOval(ellipseStartX, ellipseStartY, width, height);
    }

    /**
     * Stops drawing an ellipse when the mouse is released.
     * @param event The MouseEvent triggering the end of drawing an ellipse.
     */
    private void stopEllipseDrawing(MouseEvent event) {
        continueEllipseDrawing(event);
    }

    private final List<Double> polygonVertices = new ArrayList<>();

    /**
     * Enables drawing polygons with the specified number of sides.
     * @param sides The number of sides for the polygon to be drawn.
     */
    public void polygon(int sides) {
        setOnMousePressed(this::startPolygonDrawing);
        setOnMouseDragged(this::continuePolygonDrawing);
        setOnMouseReleased(this::stopPolygonDrawing);
    }

    // Method to start drawing a polygon
    public void startPolygonDrawing(MouseEvent event) {
        saveState();
        polygonVertices.clear();
        polygonVertices.add(event.getX());
        polygonVertices.add(event.getY());
    }

    /**
     * Starts drawing a polygon when the mouse is pressed.
     * @param event The MouseEvent triggering the start of drawing a polygon.
     */
    private void continuePolygonDrawing(MouseEvent event) {
        if (polygonVertices.size() < 6) {
            polygonVertices.add(event.getX());
            polygonVertices.add(event.getY());
        }
    }

    /**
     * Stops drawing a polygon when the mouse is released.
     * @param event The MouseEvent triggering the end of drawing a polygon.
     */
    private void stopPolygonDrawing(MouseEvent event) {
        if (polygonVertices.size() >= 6) {
            drawPolygon(polygonVertices);
            polygonVertices.clear();
        }
    }

    /**
     * Draws a polygon based on the specified vertices on the canvas.
     * The polygon is drawn if it contains enough vertices (at least 3 sets of x-y coordinates).
     *
     * @param vertices A list of vertices representing the x-y coordinates of the polygon's points.
     */
    private void drawPolygon(List<Double> vertices) {
        if (vertices.size() >= 4) {
            gc.beginPath();
            gc.moveTo(vertices.get(0), vertices.get(1));

            for (int i = 2; i < vertices.size(); i += 2) {
                gc.lineTo(vertices.get(i), vertices.get(i + 1));
            }

            gc.closePath();
            gc.stroke();
        }
    }

    /**
     * Method to take a snapshot of the canvas
     */
    public void csnapshot() {
        setOnMousePressed(this::startSnapshot);
        setOnMouseDragged(null);
        setOnMouseReleased(this::stopSnapshot);
    }

    /**
     * Starts taking a snapshot of the canvas when the mouse is pressed.
     * @param event The MouseEvent triggering the start of taking a snapshot.
     */
    private void startSnapshot(MouseEvent event) {
        saveState();
        snapshotStartX = event.getX();
        snapshotStartY = event.getY();
    }

    /**
     * Continues taking a snapshot of the canvas when the mouse is dragged.
     * @param event The MouseEvent triggering the continuation of taking a snapshot.
     */
    private void continueSnapshot(MouseEvent event) {
        double offsetX = event.getX();
        double offsetY = event.getY();
        double snapshotWidth = Math.abs(offsetX - snapshotStartX);
        double snapshotHeight = Math.abs(offsetY - snapshotStartY);
        // Create a new snapshot with the selected area
        WritableImage snapshotImage = new WritableImage((int) snapshotWidth, (int) snapshotHeight);
        SnapshotParameters sp = new SnapshotParameters();
        sp.setViewport(new Rectangle2D(snapshotStartX, snapshotStartY, snapshotWidth, snapshotHeight));
        snapshotImage = snapshot(sp, snapshotImage);
        gc.drawImage(snapshotImage, event.getX(), event.getY()); // Draw the selected area
    }

    /**
     * Stops taking a snapshot of the canvas when the mouse is released.
     * @param event The MouseEvent triggering the end of taking a snapshot.
     */
    private void stopSnapshot(MouseEvent event) {
        continueSnapshot(event);
    }


    /**
     * Sets the line width for drawing.
     * @param newWidth The new line width to be set.
     */
    public void setLwidth(int newWidth) {
        lwidth = newWidth; // Update the line width
        gc.setLineWidth(lwidth); // Set the new line width in the GraphicsContext
    }

    /**
     * Retrieves the current line width.
     * @return The current line width.
     */
    public int getLwidth() {
        return lwidth; // Return the current line width
    }

    /**
     * Retrieves the current drawing color.
     * @return The current drawing color.
     */
    public Color getColor() {
        return ccolor;
    }

    /**
     * Sets the current drawing color.
     * @param color The color to be set for drawing.
     */
    public void setColor(Color color) {
        this.ccolor = color;
        gc.setStroke(ccolor);
    }

    /**
     * Method to save the current state of the canvas
     */
    public void saveState() {
        WritableImage snapshot = this.snapshot(null, null);
        undoStack.push(snapshot);
        redoStack.clear(); // Clear the redo stack when a new operation is performed.
    }

    /**
     * Method to undo the last drawing action
     */
    public void undoItem() {
        if (!undoStack.isEmpty()) {
            redoStack.push(this.snapshot(null, null));   // Give redo a snapshot of the canvas so redo can work
            Image lastState = undoStack.pop();     // Retrieve the last state of the image from the undo stack
            GraphicsContext gc = this.getGraphicsContext2D();
            gc.clearRect(0, 0, this.getWidth(), this.getHeight());
            gc.drawImage(lastState, 0, 0, this.getWidth(), this.getHeight());
        }
    }

    /**
     * Method to redo the last undone drawing action
     */
    public void redoItem() {
        if (!redoStack.isEmpty()) {
            undoStack.push(this.snapshot(null, null));   //
            Image lastState = redoStack.pop();
            GraphicsContext gc = this.getGraphicsContext2D();
            gc.clearRect(0, 0, this.getWidth(), this.getHeight());
            gc.drawImage(lastState, 0, 0, this.getWidth(), this.getHeight());
        }
    }
}