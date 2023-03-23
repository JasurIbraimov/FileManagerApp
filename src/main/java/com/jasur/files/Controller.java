package com.jasur.files;

import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;

import java.io.*;

public class Controller {
    private final View view;
    private final FileModel fileModel;

    public Controller(View view, FileModel fileModel) {
        this.fileModel = fileModel;
        this.view = view;

        view.addTitleChangeListener((observableValue, oldValue, newValue) -> {
            fileModel.setTitle(newValue);
        });
        view.addContentChangeListener((observableValue, oldValue, newValue) -> {
            fileModel.setContent(newValue);
        });
        view.addFileNameChangeListener((observableValue, oldValue, newValue) -> {
            fileModel.setFileName(newValue);
        });
        view.addRadioToggleChangeListener((observableValue, oldValue, newValue) -> {
            RadioButton rb = (RadioButton)view.getOptionGroup().getSelectedToggle();
            if (rb != null) {
                String rbText = rb.getText();
                if (rbText.equals("text")) {
                    fileModel.setExtension(".txt");
                } else if (rbText.equals("binary")) {
                   fileModel.setExtension(".dat");
                }
            }
        });
        view.addOnClickListenerToSaveBtn(actionEvent -> handleSaveClick());
        view.addOnClickListenerToLoadBtn(actionEvent -> handleLoadLClick());
    }

    public FileChooser openFileChooser(String dialogTitle) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(dialogTitle);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Binary Files", "*.dat")
        );
        fileChooser.setInitialFileName(fileModel.getFileName() + fileModel.getExtension());
        fileChooser.setSelectedExtensionFilter(fileChooser.getExtensionFilters().get(
                fileModel.getExtension().equals(".txt") ? 0 :  1
        ));
        return fileChooser;
    }
    public void handleLoadLClick() {
        File file = openFileChooser("Load File").showOpenDialog(view.getStage());
        StringBuilder content;
        if (file != null) {
            String fileName = file.getName();
            int lastIndex = fileName.lastIndexOf('.');
            fileModel.setFileName(fileName.substring(0, lastIndex));
            fileModel.setExtension(fileName.substring(lastIndex));
            try {
                if (fileModel.getExtension().equals(".txt")) {
                    FileReader fileReader = new FileReader(file.getPath());
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line = bufferedReader.readLine();
                    content = new StringBuilder();
                    fileModel.setTitle(line);
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    bufferedReader.close();
                    fileReader.close();
                } else if (fileModel.getExtension().equals(".dat")) {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = bufferedReader.readLine();
                    content = new StringBuilder();
                    fileModel.setTitle(line);
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    bufferedReader.close();
                    fileInputStream.close();
                    inputStreamReader.close();
                } else {
                    throw new IllegalArgumentException("Invalid file extension");
                }

                fileModel.setContent(content.toString());
                int numWords = countWords(fileModel.getContent());
                view.setStatusText("Status: " + fileModel.getFileName() + " - " + numWords + " words");
                view.setContentAreaText(fileModel.getContent());
                view.setTitleInputText(fileModel.getTitle());
                view.setFilenameInputText(fileModel.getFileName());
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load document: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
    public void handleSaveClick() {
        if (fileModel.getFileName().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Filename cannot be empty");
            alert.showAndWait();
            return;
        }
        File file = openFileChooser("Save File").showSaveDialog(view.getStage());
        String text = fileModel.getTitle() + "\n" + fileModel.getContent();
        if (file != null) {
            try {
                if (fileModel.getExtension().equals(".txt")) {
                    FileWriter fileWriter = new FileWriter(file.getPath());
                    fileWriter.write(text);
                    fileWriter.close();
                } else if (fileModel.getExtension().equals(".dat")) {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(text.getBytes());
                    fileOutputStream.close();
                } else {
                    throw new IllegalArgumentException("Invalid file extension");
                }
                int numWords = countWords(fileModel.getContent());
                view.setStatusText("Document saved (" + numWords + " words)");
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save document: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private int countWords(String text) {
        if (text.isEmpty()) {
            return 0;
        } else {
            String[] words = text.split("\\s+");
            return words.length;
        }
    }


}