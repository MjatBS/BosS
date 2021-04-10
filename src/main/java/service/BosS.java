/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.service;

import javafx.beans.value.ChangeListener;
import main.java.controllers.NavigationMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.controllers.NavigationButton;
import main.java.model.Resources;
import main.java.model.ScreenFactory;
import main.java.model.ScreenName;

/**
 *
 * @author Bogdan
 */
public class BosS extends Application{
    final int FIRST_WIDTH = 1010;
    final int FIRST_HEIGHT = 800;
    final int FIRST_ACTIVE_MENU_BUTTON = 2;//от 0 до 2 включ

    private static Stage mainStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        Root root = Root.getInstance();
        NavigationButton[] navButtons = root.createNavigationMenu();
        new NavigationMenuController(navButtons,FIRST_ACTIVE_MENU_BUTTON); // Установка контроля над навигационным меню
        Resources res = new Resources();
        
        Scene scene = new Scene(root, FIRST_WIDTH,FIRST_HEIGHT);
        primaryStage.setTitle("BosS");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        ScreenFactory fack = ScreenFactory.getInstance();
        root.setLeftScreen(fack.getScreen(ScreenName.LIFE_CALENDAR));
        root.setRightScreen(fack.getScreen(ScreenName.PRIORITIZATION_SQUARES));

    }
    static Stage getMainStage(){
        return mainStage;
    }
    

    
    
    
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}
