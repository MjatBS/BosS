/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import main.java.model.ScreenName;

import java.net.MalformedURLException;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.util.Duration;
import main.java.model.Resources;
import main.java.model.ScreenFactory;
import main.java.service.Root;

/**
 *
 * @author Bogdan
 */
public class NavigationButton extends Button{
    int imageSize=75;
    
    final int TIME_AUDIT = 0;
    final int DELEGATION = 1;
    final int MAIN_PART = 2;
    final String[] NAMES_IMAGES_FOR_BUTTON = {"timeaudit.png","delegation.png","mainpart.png"};
    
    int numButton;
    double scale = 1.4f;
    int millis = 1000;
    
    /**
     * button choose from constants
     * @param numButton
    */
    public NavigationButton(int numButton){
        this.numButton = numButton;
        setView();
    }
    private void setView(){
        try {
            graphicProperty().setValue(getImage());//Устанавливаем картинку
        } catch (MalformedURLException ex) {
            setText(NAMES_IMAGES_FOR_BUTTON[numButton]);
        }
        setBackground(Background.EMPTY);//Убираем ненужный фон
        
    }
        private ImageView getImage() throws MalformedURLException{
            Resources res = new Resources();
            ImageView image = new ImageView(res.getImage(NAMES_IMAGES_FOR_BUTTON[numButton]));
            image.setFitHeight(imageSize);
            image.setFitWidth(imageSize);
            return image;
        }
    
    public void setActiveButton(NavigationButton but){        
        ScaleTransition st = new ScaleTransition(Duration.millis(millis),but);
        //st.setByX(scale);
        //.setByY(scale);
        st.setToX(scale);
        st.setToY(scale);
        //st.setCycleCount((int) 4f);//туда и обратно
        //st.setAutoReverse(true);

        st.play();
    }
    public void setPassiveButton(NavigationButton but){        
        ScaleTransition st = new ScaleTransition(Duration.millis(millis),but);
        //st.setByX(1/scale);
        //st.setByY(1/scale);
        st.setToX(1);
        st.setToY(1);
        
        st.play();
    }
    
    public int getNumButton(){
        return numButton;
    }
    
    public  void setMainScreen(){
        Root toro = Root.getInstance();
        ScreenFactory factory = ScreenFactory.getInstance();
        switch(numButton){
            case 0:
                toro.setLeftScreen(factory.getScreen(ScreenName.LIFE_CALENDAR));
                toro.setRightScreen(factory.getScreen(ScreenName.PRIORITIZATION_SQUARES));
                break;
            case 1:
                toro.setFullScreen(factory.getScreen(ScreenName.DELEGATION_1));
                break;
        }
    }
    
}
