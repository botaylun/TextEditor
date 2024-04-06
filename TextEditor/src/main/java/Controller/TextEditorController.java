package Controller;

import Model.TextDocumentModel;
import View.TextEditor;

import java.io.IOException;

public class TextEditorController {
    private static TextDocumentModel documentModel;
    private static TextEditor textEditor;

    public static void main(String[] args) {
        documentModel = new TextDocumentModel();
        textEditor = new TextEditor();

    }

    public static void saveDocument(String filePath) {
        try {
            documentModel.saveToFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDocument(String filePath) {
        try {
            documentModel.loadFromFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDocumentText() {
        StringBuilder builder = new StringBuilder();
        documentModel.getLines().forEach(line -> builder.append(line).append("\n"));
        return builder.toString();
    }
}