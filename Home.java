package com.example.maspain;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;
import java.util.Optional;


public class Home extends Application {
    private File openImgFile;
    private final openImage imageOpener = new openImage();
    private final saveImage imageSaver = new saveImage();
    private final saveImageAs imageSaverAs = new saveImageAs();
    private DrawingCanvas drawingCanvas;
    private int counter = 30;
    private boolean issaved = false;
    private boolean isopen = false;
    private ToggleButton autoSaveToggleButton;
    private boolean isAutoSaveVisible = false;




    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Paint");
        primaryStage.setMaximized(true);


        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu shapemenu = new Menu("Shapes");
        Menu helpMenu = new Menu("Help");


        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem close = new MenuItem("Close");
        MenuItem undo = new MenuItem("Undo");
        MenuItem redo = new MenuItem("Redo");


        fileMenu.getItems().addAll(open, save, saveAs, close, undo, redo);


        MenuItem Pointer = new MenuItem("Pointer");
        MenuItem Eraser = new MenuItem("Eraser");
        MenuItem Pencil = new MenuItem("Pencil");
        MenuItem textm = new MenuItem("Text");
        MenuItem Straight_Line = new MenuItem("Straight Line");
        MenuItem Sshot = new MenuItem("SnapShot");
        MenuItem clear = new MenuItem("Clear");
        editMenu.getItems().addAll(Pointer, Pencil, clear, textm, Eraser, Straight_Line, Sshot);


        MenuItem Square = new MenuItem("Square");
        MenuItem Circle = new MenuItem("Circle");
        MenuItem HalfCircle = new MenuItem("Half-Circle");
        MenuItem Rectangle = new MenuItem("Rectangle");
        MenuItem Ellipse = new MenuItem("Ellipse");
        MenuItem Triangle = new MenuItem("Triangle");
        MenuItem Polygon = new MenuItem("Polygon");
        shapemenu.getItems().addAll(Square, Circle, HalfCircle, Rectangle, Ellipse, Polygon, Triangle);


        MenuItem about = new MenuItem("About");
        MenuItem contact = new MenuItem("Contact");
        helpMenu.getItems().addAll(about, contact);


        drawingCanvas = new DrawingCanvas(800, 600);


        open.setOnAction(event -> {
            openImgFile = imageOpener.openImg(primaryStage, drawingCanvas);
            isopen = true;
        });


        save.setOnAction(event -> {
            imageSaver.saveImg(drawingCanvas, openImgFile);
            issaved = true;
        });


        saveAs.setOnAction(event -> {
            imageSaverAs.saveImgAs(drawingCanvas);
            issaved = true;
        });


        undo.setOnAction(event -> drawingCanvas.undoItem());
        redo.setOnAction(event -> drawingCanvas.redoItem());


        Pointer.setOnAction(event -> drawingCanvas.pointer());
        Pencil.setOnAction(event -> drawingCanvas.pencil1());
        Eraser.setOnAction(event -> drawingCanvas.eraser());
        Straight_Line.setOnAction(event -> drawingCanvas.straightl());
        Sshot.setOnAction(event -> drawingCanvas.csnapshot());
        clear.setOnAction(event -> {
            Alert confirmClear = new Alert(Alert.AlertType.CONFIRMATION);
            confirmClear.setTitle("Clear Confirmation");
            confirmClear.setHeaderText(null);
            confirmClear.setContentText("Are you sure you want to clear the canvas?");
            Optional<ButtonType> result = confirmClear.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                drawingCanvas.getGraphicsContext2D().clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
            }
        });
        textm.setOnAction(event -> {
            TextInputDialog textDialog = new TextInputDialog();
            textDialog.setTitle("Enter Text");
            textDialog.setHeaderText(null);
            textDialog.setContentText("Please enter the text:");

            Optional<String> result = textDialog.showAndWait();
            result.ifPresent(text -> {
                drawingCanvas.text(text); // Call the text method with the entered text
            });
        });

        Square.setOnAction(event -> drawingCanvas.square());
        Circle.setOnAction(event -> drawingCanvas.circle());
        HalfCircle.setOnAction(event -> drawingCanvas.halfcircle());
        Rectangle.setOnAction(event -> drawingCanvas.rectangle());
        Ellipse.setOnAction(event -> drawingCanvas.ellipse());
        Triangle.setOnAction(event -> drawingCanvas.polygon(3));
        Polygon.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Polygon Sides");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the number of sides:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(sides -> {
                try {
                    int numSides = Integer.parseInt(sides);
                    System.out.println("Number of sides: " + numSides);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            });
        });


        about.setOnAction(event -> {
            Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
            aboutAlert.setTitle("About");
            aboutAlert.setHeaderText(null);
            aboutAlert.setContentText("Why do blind programmers use Java?.........\nBecause they can't C.");
            aboutAlert.showAndWait();
        });


        Label timerLabel = new Label("0");
        Label timeOpenLabel = new Label("Time Open:"); // New label for "Time Open"




        long startTime = System.currentTimeMillis();
        Timeline timerTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    long now = System.currentTimeMillis();
                    long elapsedMillis = now - startTime;
                    timerLabel.setText(Long.toString(elapsedMillis / 1000));
                })
        );
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();




        Label autotimerLabel = new Label("0");
        Label autotimeOpenLabel = new Label("Until Auto-Save:");
        autotimerLabel.setVisible(isAutoSaveVisible);
        autotimeOpenLabel.setVisible(isAutoSaveVisible);


        Timeline autotime = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (counter == 0) {
                        imageSaver.saveImg(drawingCanvas, openImgFile);
                        System.out.println("Image auto-saved");
                        counter = 30;
                    } else {
                        autotimerLabel.setText(Integer.toString(counter));
                    }
                    counter--;
                })
        );


// Start the autotime timeline
        autotime.setCycleCount(Timeline.INDEFINITE);
        autotime.play();


        autoSaveToggleButton = new ToggleButton("Toggle Auto-Save");
        autoSaveToggleButton.setSelected(isAutoSaveVisible);


        autoSaveToggleButton.setOnAction(event -> {
            isAutoSaveVisible = autoSaveToggleButton.isSelected();
            autotimerLabel.setVisible(isAutoSaveVisible);
            autotimeOpenLabel.setVisible(isAutoSaveVisible);
        });

        menuBar.getMenus().addAll(fileMenu, editMenu, shapemenu, helpMenu);
        Slider lineSizeSlider = new Slider(0, 50, drawingCanvas.getLwidth());
        lineSizeSlider.setShowTickMarks(true);
        lineSizeSlider.setMajorTickUnit(10.0);
        lineSizeSlider.setShowTickLabels(true);
        lineSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int newWidth = newValue.intValue();
            drawingCanvas.setLwidth(newWidth);
        });


        ColorPicker colorPicker = new ColorPicker(drawingCanvas.getColor());
        colorPicker.setOnAction(event -> {
            Color newColor = colorPicker.getValue();
            drawingCanvas.setColor(newColor);
        });


        drawingCanvas.setColor(colorPicker.getValue());


        VBox sliderBox = new VBox(lineSizeSlider);
        VBox vBox = new VBox(menuBar);
        VBox vbox1 = new VBox(colorPicker);
        vbox1.getChildren().addAll(timeOpenLabel, timerLabel); // Add ToggleButton
        sliderBox.getChildren().addAll(autoSaveToggleButton,autotimeOpenLabel, autotimerLabel);
        vBox.setAlignment(Pos.CENTER);
        sliderBox.setAlignment(Pos.TOP_LEFT);
        vbox1.setAlignment(Pos.TOP_LEFT); // Adjust alignment for "Time Open" label


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(drawingCanvas);
        borderPane.setTop(vBox);
        borderPane.setLeft(sliderBox);
        borderPane.setRight(vbox1);


        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);


        scene.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.S) {
                imageSaver.saveImg(drawingCanvas, openImgFile);
                event.consume();
            }
        });


        primaryStage.setOnCloseRequest(e -> {
            if (!issaved && isopen) {
                e.consume();
                showSaveConfirmation(primaryStage);
            }
        });


        primaryStage.show();
    }


    private void showSaveConfirmation(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Confirmation");
        alert.setHeaderText("You have unsaved changes. Do you want to save?");
        alert.setContentText("Choose your option.");


        ButtonType saveButton = new ButtonType("Save");
        ButtonType dontSaveButton = new ButtonType("Don't Save");
        ButtonType cancelButton = new ButtonType("Cancel");


        alert.getButtonTypes().setAll(saveButton, dontSaveButton, cancelButton);


        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == saveButton) {
            imageSaver.saveImg(drawingCanvas, openImgFile);
            primaryStage.close();
        } else if (result.get() == dontSaveButton) {
            primaryStage.close();
        }
    }
}
