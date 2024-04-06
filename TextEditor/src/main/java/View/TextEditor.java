package View;

import Model.TextDocumentModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class TextEditor extends Application {
    private TextDocumentModel documentModel;
    private TextArea textArea;

    @Override
    public void start(Stage primaryStage) {
        documentModel = new TextDocumentModel();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        textArea = new TextArea();
        root.setCenter(textArea);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> saveDocument(primaryStage));

        Button loadButton = new Button("Load");
        loadButton.setOnAction(event -> loadDocument(primaryStage));

        Button browseButton = new Button("Browse Folder");
        browseButton.setOnAction(event -> browseFolder(primaryStage));

        HBox buttonContainer = new HBox(10);
        buttonContainer.setPadding(new Insets(10));
        buttonContainer.getChildren().addAll(saveButton, loadButton, browseButton);
        root.setBottom(buttonContainer);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Text Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveDocument(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Document");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                String text = textArea.getText();
                writer.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadDocument(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Document");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                String text = stringBuilder.toString();
                textArea.setText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void browseFolder(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Browse Folder");
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            StringBuilder stringBuilder = new StringBuilder();
            browseFolderRecursive(selectedDirectory, stringBuilder);
            String text = stringBuilder.toString();
            textArea.setText(text);
        }
    }

    private void browseFolderRecursive(File directory, StringBuilder stringBuilder) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    browseFolderRecursive(file, stringBuilder);
                } else {
                    stringBuilder.append(file.getAbsolutePath()).append("\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
