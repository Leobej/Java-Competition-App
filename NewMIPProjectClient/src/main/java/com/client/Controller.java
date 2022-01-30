package com.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Controller extends Application {

    //Register Window
    @FXML
    private TextField firstname;

    @FXML
    private ToggleGroup group;

    @FXML
    private TextField lastname;

    @FXML
    private RadioButton radio1;

    @FXML
    private Button submit;

    @FXML
    private TextField username;

    @FXML
    private Label alreadyUsedUsername;

    private Boolean registerOK = false;


    //Main Window
    @FXML
    private Button loginMain;

    @FXML
    private Button registerMain;


    //Login Window

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginUsername;

    @FXML
    private TextField teamName;

    private Boolean loginOk = false;

    @FXML
    private Label incorrectUsername;


    //Stage Window
    @FXML
    private TextField scoreField;

    @FXML
    private Text stageNumberLabel;

    @FXML
    private Button submitScore;

    private int stageNumber=0;


// Switch scenes

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void registerMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Register.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void loginMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toStage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Stages.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        //Register functions
    public void registerFunction(ActionEvent e) {

        Boolean isAdmin = false;
        if (radio1.isSelected()) {
            isAdmin = true;
        }

        String string = username.getText() + " " + firstname.getText() +
                " " + lastname.getText() + " " + isAdmin.toString() + " " + teamName.getText();
        sendToServer("REGISTER" + string);
        System.out.println(string);
    }


    public void registerButton(ActionEvent event) throws IOException {
        alreadyUsedUsername.setVisible(false);
        registerFunction(event);
        if (registerOK == true)
            backToMain(event);
        else {
            System.out.println("Username already used");
            alreadyUsedUsername.setVisible(true);
        }

    }
            //Login functions
    private void loginFunction() {

        String string = loginUsername.getText();
        System.out.println("LOGIN" + string);
        sendToServer("LOGIN" + string);
        incorrectUsername.setVisible(false);
    }


    public void loginButton(ActionEvent event) throws IOException {

        loginFunction();

        if (loginOk == true)
            toStage(event);
        else {
            System.out.println("Username incorect");
            incorrectUsername.setVisible(true);
        }
    }



    //Stage functions
    public void onSubmitScore(ActionEvent event) throws IOException{
        stageNumberLabel.setText(String.valueOf(stageNumber));
        stageNumber++;
    }

    public Boolean verifyIfUserSentScore()
    {
        return true;
    }


//server connection

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public void sendToServer(String string) {
        Boolean closed = false;
        try {
            String host = "127.0.0.1";
            int port = 32000;
            try (Socket socket = new Socket(host, port)) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = null;
                while (!"exit".equalsIgnoreCase(line) && closed == false) {
                    line = string;
                    out.println(line);
                    out.flush();
                    String fromServer = in.readLine();
                    if (fromServer.contains("LOGIN") && fromServer.contains("true")) {
                        loginOk = true;
                        break;
                    }
                    if (fromServer.contains("REGISTER") && fromServer.contains("false")) {
                        registerOK = true;
                        break;
                    }
                    if(fromServer.contains("STAGE"))
                    {

                    }
                    closed = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/MainWindow.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
