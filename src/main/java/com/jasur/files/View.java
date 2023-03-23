package com.jasur.files;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View {

    private final Stage stage;
    private final int SCENE_WIDTH = 500;
    private final int SCENE_HEIGHT = 450;

    private final ToggleGroup optionGroup;
    private final TextArea contentArea;
    private final Label statusText;

    private final TextField titleInput;
    private final TextField filenameInput;

    private final Button saveBtn;
    private final Button loadBtn;

    public ToggleGroup getOptionGroup() {
        return optionGroup;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStatusText(String text) {
        statusText.setText(text);
    }

    public void setContentAreaText(String text) {
        contentArea.setText(text);
    }

    public void setTitleInputText(String text) {
        titleInput.setText(text);
    }

    public void setFilenameInputText(String text) {
        filenameInput.setText(text);
    }


    public View(Stage stage) {
        this.stage = stage;

        // Header
        Label filenameText = new Label("File name: ");
        filenameInput = new TextField();
        RadioButton option1 = new RadioButton("text");
        RadioButton option2 = new RadioButton("binary");

        optionGroup = new ToggleGroup();
        option1.setToggleGroup(optionGroup);
        option2.setToggleGroup(optionGroup);
        option1.setSelected(true);
        option2.setSelected(false);

        saveBtn = new Button("Save");
        loadBtn = new Button("Load");

        FlowPane header = new FlowPane();
        header.setPrefWidth(SCENE_WIDTH);
        header.getChildren().addAll(filenameText, filenameInput, option1, option2, saveBtn, loadBtn);
        header.setHgap(20);

        Label titleText = new Label("Title: ");
        titleInput = new TextField();
        titleInput.setPrefWidth(SCENE_WIDTH);

        Label contentText = new Label("Content: ");
        contentArea = new TextArea();
        contentArea.setPrefWidth(SCENE_WIDTH);

        statusText = new Label("Status: New Document - 0 words");
        statusText.setPrefWidth(SCENE_WIDTH);
        statusText.setAlignment(Pos.CENTER);

        FlowPane root = new FlowPane(Orientation.VERTICAL);
        root.setVgap(20);
        root.setStyle("-fx-padding: 20px");
        root.getChildren().addAll(header, titleText, titleInput, contentText, contentArea, statusText);
        Scene scene = new Scene(root, SCENE_WIDTH + 40, SCENE_HEIGHT);
        stage.setResizable(false);
        stage.setTitle("Files Editor");
        stage.setScene(scene);
        stage.show();
    }

    public void addContentChangeListener(ChangeListener<String> listener) {
        contentArea.textProperty().addListener(listener);
    }

    public void addTitleChangeListener(ChangeListener<String> listener) {
        titleInput.textProperty().addListener(listener);
    }

    public void addFileNameChangeListener(ChangeListener<String> listener) {
        filenameInput.textProperty().addListener(listener);
    }

    public void addRadioToggleChangeListener(ChangeListener<Toggle> listener) {
        optionGroup.selectedToggleProperty().addListener(listener);
    }
    public void addOnClickListenerToSaveBtn(EventHandler<ActionEvent> eventEventHandler) {
        saveBtn.setOnAction(eventEventHandler);
    }
    public void addOnClickListenerToLoadBtn(EventHandler<ActionEvent> eventEventHandler) {
        loadBtn.setOnAction(eventEventHandler);
    }
}
