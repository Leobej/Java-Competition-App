package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client extends Application {
    @Override
    public void start(Stage stage) throws IOException {
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

    public static void main(String[] args) {

        launch(args);
        /*try {

            String host = "127.0.0.1";
            int port = 32000;
            try (Socket socket = new Socket(host, port)) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in);
                String line = null;

                while (!"exit".equalsIgnoreCase(line)) {
                    line = scanner.nextLine();
                    out.println(line);
                    out.flush();
                    System.out.println("Serverul a zis: " + in.readLine());
                }
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}