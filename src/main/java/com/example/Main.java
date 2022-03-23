package com.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;
    private static Scene scene2;
    private static Scene scene3;

    @Override
    public void start(Stage stage) throws IOException {
        ListView wordView = new ListView<>();
        VBox loadingBox = new VBox(20);
        stage.setTitle("Text Analyzer");
        Button button = new Button("Proccess URL");

        Label introLabel = new Label("Welcome to Text Analyzer");
        Label instructionLabel = new Label(
                "The following will print the top 20 words used in the source.\nPlease ONLY enter https://www.gutenberg.org/ HTML links\nResults can take a while depending on file length.\n");
        Label scene2Label = new Label("Top 20 Word Occurance");
        TextField urlField = new TextField("Enter URL");
        Button backButton = new Button("Process another URL");

        VBox layout2 = new VBox(20);
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().addAll(scene2Label, wordView,backButton);

        button.setOnAction(e -> {
            App analyzeApp = new App(urlField.getText());
            wordView.getItems().clear();
            wordView.getItems().add(analyzeApp.Main());

            //layout2.getChildren().addAll(scene2Label, wordView,backButton);
            stage.setScene(scene2);
        });

        backButton.setOnAction(e -> stage.setScene(scene));

        VBox layoutBox = new VBox(20);
        layoutBox.setAlignment(Pos.CENTER);
        layoutBox.getChildren().addAll(introLabel, instructionLabel, urlField, button);

        scene = new Scene(layoutBox, 640, 480);
        scene2 = new Scene(layout2, 640, 480);
        scene3 = new Scene(loadingBox, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }

}