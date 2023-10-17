package com.example.maspain;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
    private int counter = 10;
    private boolean issaved = false;
    private boolean isopen = false;
    private ToggleButton autoSaveToggleButton;
    private boolean isAutoSaveVisible = false;
    private Logger logger;


    /**
     * The `Home` class serves as the main application for a simple painting program. It provides a graphical user interface
     * for drawing various shapes, saving and opening images, and managing the drawing canvas.
     *<p>
     * The program supports features such as drawing, erasing, taking snapshots, undo/redo functionality, and more. Users can
     * select different drawing tools and configure line width and color.
     */

    public static void main(String[] args) {
        launch(args);
    }




    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Paint");
        primaryStage.setMaximized(true);
        logger = new Logger("C:\\CS_250\\maspain\\src\\main\\java\\com\\example\\maspain\\LoggingData.txt");


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

        Label toolslabel = new Label("Tools:");

        ImageView pencon = new ImageView("C:\\CS_250\\maspain\\images\\P-Icon.jpg");
        Tooltip ptip = new Tooltip("Pencil Tool");
        Button pencilbutton = new Button();
        pencilbutton.setTooltip(ptip);
        pencilbutton.setGraphic(pencon);
        pencon.fitWidthProperty().bind(pencilbutton.widthProperty().divide(1.75));
        pencon.fitHeightProperty().bind(pencilbutton.heightProperty().divide(1.5));
        pencilbutton.setOnAction(event ->{
            logger.log(openImgFile +" Pencil Was Selected");
            drawingCanvas.pencil1();
        });

        ImageView econ = new ImageView("C:\\CS_250\\maspain\\images\\E-Icon.jpg");
        Tooltip etip = new Tooltip("Eraser Tool");
        Button eraserbutton = new Button();
        eraserbutton.setTooltip(etip);
        eraserbutton.setGraphic(econ);
        econ.fitWidthProperty().bind(eraserbutton.widthProperty().divide(1.75));
        econ.fitHeightProperty().bind(eraserbutton.heightProperty().divide(1.75));
        eraserbutton.setOnAction(event ->{
            logger.log(openImgFile +" Eraser Was Selected");
            drawingCanvas.eraser();
        });

        ImageView pointcon = new ImageView("C:\\CS_250\\maspain\\images\\pointer.png");
        Tooltip potip = new Tooltip("Pointer Tool");
        Button pointbutton = new Button();
        pointbutton.setTooltip(potip);
        pointbutton.setGraphic(pointcon);
        pointcon.fitWidthProperty().bind(pointbutton.widthProperty().divide(1.75));
        pointcon.fitHeightProperty().bind(pointbutton.heightProperty().divide(1.75));
        pointbutton.setOnAction(event ->{
            logger.log(openImgFile +" Pointer Was Selected");
            drawingCanvas.pointer();
        });

        ImageView lcon = new ImageView("C:\\CS_250\\maspain\\images\\line.png");
        Tooltip ltip = new Tooltip("Line Tool");
        Button linebutton = new Button();
        linebutton.setTooltip(ltip);
        linebutton.setGraphic(lcon);
        lcon.fitWidthProperty().bind(linebutton.widthProperty().divide(1.75));
        lcon.fitHeightProperty().bind(linebutton.heightProperty().divide(1.75));
        linebutton.setOnAction(event -> {
            logger.log(openImgFile +" Straight Line Was Selected");
            drawingCanvas.straightl();
        });

        ImageView tcon = new ImageView("C:\\CS_250\\maspain\\images\\textcon.png");
        Tooltip ttip = new Tooltip("Text Tool");
        Button textbutton = new Button();
        textbutton.setTooltip(ttip);
        textbutton.setGraphic(tcon);
        tcon.fitWidthProperty().bind(textbutton.widthProperty().divide(1.75));
        tcon.fitHeightProperty().bind(textbutton.heightProperty().divide(1.75));
        textbutton.setOnAction(event -> {
            logger.log(openImgFile +" Text Was Selected");
            TextInputDialog textDialog = new TextInputDialog();
            textDialog.setTitle("Enter Text");
            textDialog.setHeaderText(null);
            textDialog.setContentText("Please enter the text:");

            Optional<String> result = textDialog.showAndWait();
            result.ifPresent(text -> {
                drawingCanvas.text(text);
            });
        });

        MenuItem Sshot = new MenuItem("SnapShot");
        MenuItem clear = new MenuItem("Clear");
        editMenu.getItems().addAll(clear,Sshot);

        Label shapelabel = new Label("Shapes:");
        ImageView scon = new ImageView("C:\\CS_250\\maspain\\images\\square.png");
        Tooltip stip = new Tooltip("Square");
        Button squarebutton = new Button();
        squarebutton.setTooltip(stip);
        squarebutton.setGraphic(scon);
        scon.fitWidthProperty().bind(squarebutton.widthProperty().divide(1.95));
        scon.fitHeightProperty().bind(squarebutton.heightProperty().divide(1.55));
        squarebutton.setOnAction(event ->{
            logger.log(openImgFile +" Square Was Selected");
            drawingCanvas.square();
        });

        ImageView ccon = new ImageView("C:\\CS_250\\maspain\\images\\circle.png");
        Tooltip ctip = new Tooltip("Circle");
        Button circlebutton = new Button();
        circlebutton.setTooltip(ctip);
        circlebutton.setGraphic(ccon);
        ccon.fitWidthProperty().bind(circlebutton.widthProperty().divide(1.95));
        ccon.fitHeightProperty().bind(circlebutton.heightProperty().divide(1.55));
        circlebutton.setOnAction(event ->{
            logger.log(openImgFile +" Circle Was Selected");
            drawingCanvas.circle();
        });

        ImageView rcon = new ImageView("C:\\CS_250\\maspain\\images\\square.png");
        Tooltip rtip = new Tooltip("Rectangle");
        Button recbutton = new Button();
        recbutton.setTooltip(rtip);
        recbutton.setGraphic(rcon);
        rcon.fitWidthProperty().bind(recbutton.widthProperty().divide(1.75));
        rcon.fitHeightProperty().bind(recbutton.heightProperty().divide(1.75));
        recbutton.setOnAction(event ->{
            logger.log(openImgFile +" Rectangle Was Selected");
            drawingCanvas.rectangle();
        });

        ImageView hcon = new ImageView("C:\\CS_250\\maspain\\images\\halfcircle.png");
        Tooltip htip = new Tooltip("Half Circle");
        Button halfbutton = new Button();
        halfbutton.setTooltip(htip);
        halfbutton.setGraphic(hcon);
        hcon.fitWidthProperty().bind(halfbutton.widthProperty().divide(2));
        hcon.fitHeightProperty().bind(halfbutton.heightProperty().divide(1.75));
        halfbutton.setOnAction(event ->{
            logger.log(openImgFile +" Half-Circle Was Selected");
            drawingCanvas.halfcircle();
        });

        MenuItem Ellipse = new MenuItem("Ellipse");
        MenuItem Triangle = new MenuItem("Triangle");
        MenuItem Polygon = new MenuItem("Polygon");
        shapemenu.getItems().addAll (Ellipse, Polygon, Triangle);


        MenuItem about = new MenuItem("About");
        MenuItem contact = new MenuItem("Contact");
        helpMenu.getItems().addAll(about, contact);


        drawingCanvas = new DrawingCanvas(800, 600);



        open.setOnAction(event -> {
            logger.log(openImgFile +" was opened");
            openImgFile = imageOpener.openImg(primaryStage, drawingCanvas);
            isopen = true;
        });


        save.setOnAction(event -> {
            logger.log(openImgFile +" was saved");
            imageSaver.saveImg(drawingCanvas, openImgFile);
            issaved = true;
        });


        saveAs.setOnAction(event -> {
            logger.log("saved as "+openImgFile );
            imageSaverAs.saveImgAs(drawingCanvas);
            issaved = true;
        });

        undo.setOnAction(event -> {
            logger.log(openImgFile +" Action was undone");
            drawingCanvas.undoItem();
        });
        redo.setOnAction(event ->{
            logger.log(openImgFile +" Action was redone");
            drawingCanvas.redoItem();
        });

        Sshot.setOnAction(event -> drawingCanvas.csnapshot());
        clear.setOnAction(event -> {
            Alert confirmClear = new Alert(Alert.AlertType.CONFIRMATION);
            confirmClear.setTitle("Clear Confirmation");
            confirmClear.setHeaderText(null);
            confirmClear.setContentText("Are you sure you want to clear the canvas?");
            Optional<ButtonType> result = confirmClear.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                logger.log(openImgFile +" canvas was cleared");
                drawingCanvas.getGraphicsContext2D().clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
            }
        });

        Ellipse.setOnAction(event ->{
            logger.log(openImgFile +" Ellipse was selected");
            drawingCanvas.ellipse();
        });
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
            logger.log(openImgFile +" About was selected");
            Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
            aboutAlert.setTitle("About");
            aboutAlert.setHeaderText(null);
            aboutAlert.setContentText("Why do blind programmers use Java?.........\nBecause they can't C.");
            aboutAlert.showAndWait();
        });


        Label timerLabel = new Label("0");
        Label timeOpenLabel = new Label("Time Open:");




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
                        logger.log(openImgFile+ " was Auto-Saved");
                        NotificationManager.showNotification(openImgFile + " auto-saved", primaryStage);
                        System.out.println(openImgFile+" auto-saved");
                        counter = 30;
                    } else {
                        autotimerLabel.setText(Integer.toString(counter));
                    }
                    counter--;
                })
        );



        autotime.setCycleCount(Timeline.INDEFINITE);
        autotime.play();

        ImageView rocon = new ImageView("C:\\CS_250\\maspain\\images\\Rotation.png");
        Tooltip rotip = new Tooltip("Rotation Button");
        Button rotatebutton = new Button();
        rotatebutton.setTooltip(rotip);
        rotatebutton.setGraphic(rocon);
        rocon.fitWidthProperty().bind(rotatebutton.widthProperty().divide(2));
        rocon.fitHeightProperty().bind(rotatebutton.heightProperty().divide(1.75));
        rotatebutton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Rotation Selector");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose the degrees you want your canvas to be rotated:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(sides -> {
                try {
                    int rotdegrees = Integer.parseInt(sides);
                    logger.log(openImgFile +" was rotated " + rotdegrees + "degrees");
                    drawingCanvas.setRotate(rotdegrees);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            });
        });


        autoSaveToggleButton = new ToggleButton("Toggle Auto-Save");
        autoSaveToggleButton.setSelected(isAutoSaveVisible);

        autoSaveToggleButton.setOnAction(event -> {
            logger.log(openImgFile +" Auto-Save Timer was toggled");
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
            logger.log(openImgFile +" line width set as "+ newWidth);
            drawingCanvas.setLwidth(newWidth);
        });


        ColorPicker colorPicker = new ColorPicker(drawingCanvas.getColor());
        colorPicker.setOnAction(event -> {
            Color newColor = colorPicker.getValue();
            logger.log(openImgFile +" new color set as "+newColor);
            drawingCanvas.setColor(newColor);
        });


        drawingCanvas.setColor(colorPicker.getValue());


        VBox sliderBox = new VBox(lineSizeSlider);
        VBox vBox = new VBox(menuBar);
        VBox vbox1 = new VBox(colorPicker);
        vbox1.getChildren().addAll(timeOpenLabel, timerLabel);
        sliderBox.getChildren().addAll(autoSaveToggleButton,autotimeOpenLabel, autotimerLabel,toolslabel,pencilbutton,eraserbutton,pointbutton,linebutton,textbutton,shapelabel,squarebutton ,circlebutton,recbutton,halfbutton, rotatebutton);
        vBox.setAlignment(Pos.CENTER);
        sliderBox.setAlignment(Pos.TOP_LEFT);
        vbox1.setAlignment(Pos.TOP_LEFT);


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(drawingCanvas);
        borderPane.setTop(vBox);
        borderPane.setLeft(sliderBox);
        borderPane.setRight(vbox1);


        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);


        scene.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.S) {
                logger.log(openImgFile +" Save was hotkeyed");
                imageSaver.saveImg(drawingCanvas, openImgFile);
                event.consume();
            }
        });


        primaryStage.setOnCloseRequest(e -> {
            if (!issaved && isopen) {
                e.consume();
                showSaveConfirmation(primaryStage);
            }
            logger.close();
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
