package com.samuel.petshop;

import com.samuel.petshop.dataModel.PetShop;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Pet Shop");
        primaryStage.setScene(new Scene(root, 900, 650));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws IOException {
        try{
            PetShop.getInstance().storeSpeciesList();
            PetShop.getInstance().storePetList();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() throws IOException {
        try {
            PetShop.getInstance().loadSpeciesList();
            PetShop.getInstance().loadPetList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
