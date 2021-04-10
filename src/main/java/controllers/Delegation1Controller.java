/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.java.model.Resources;
import main.java.model.ScreenFactory;
import main.java.model.ScreenName;
import main.java.service.Root;

/**
 * FXML Controller class
 *
 * @author Jarvis
 */
public class Delegation1Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    Resources res = new Resources();
    @FXML
    ImageView im1,im2,im3;
    @FXML
    VBox level1,level2,level3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setImages();
        createButtons();
    } 
    private void setImages(){
        im1.setImage(res.getImage("delegation1.png"));
        im2.setImage(res.getImage("delegation2.png"));
        im3.setImage(res.getImage("delegation3.png"));
    }
    private void createButtons(){
        createButton(level1);
        createButton(level2);
        createButton(level3);
    }
    private void createButton(VBox level){
        level.setOnMouseClicked((MouseEvent e) -> {
            ScreenFactory getterScreen = ScreenFactory.getInstance();
            Root root = Root.getInstance();
            if(!e.getSource().equals(level3)){
                System.out.println("One or two");
                Node newScreen = getterScreen.getScreen(ScreenName.DELEGATION_2);
                root.setFullScreen(newScreen);
            }
        });
    }
    
}
