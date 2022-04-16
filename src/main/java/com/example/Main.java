package com.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

/**
 * JavaFX App
 *
 * @author Juan Perez
 * @version 1.2
 */
public class Main extends Application {

    private static Scene scene;
    private static Scene scene2;


    /**
     * Starts the JavaFX app with scene 1 as the intro page
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        String dbUrl = "jdbc:sqlserver://localhost:64253;databaseName=wordoccurences;encrypt=false;IntegratedSecurity=true;";
        ListView wordView = new ListView<>();
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
        layout2.getChildren().addAll(scene2Label, wordView, backButton);

        button.setOnAction(e -> {
            App analyzeApp = new App(urlField.getText());
            wordView.getItems().clear();
            analyzeApp.Main();

            try (Connection con = getConnection(dbUrl); Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM word");
                while (rs.next()) {
                    //Fettch results and add each to the JavaFX wordview
                    wordView.getItems().add("Word: " + rs.getString("words") + ", Frequency: " + rs.getInt("frequency"));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }


            stage.setScene(scene2);
        });

        backButton.setOnAction(e -> stage.setScene(scene));

        VBox layoutBox = new VBox(20);
        layoutBox.setAlignment(Pos.CENTER);
        layoutBox.getChildren().addAll(introLabel, instructionLabel, urlField, button);
        layoutBox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, null, null)));

        scene = new Scene(layoutBox, 640, 480);
        scene2 = new Scene(layout2, 640, 480);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}