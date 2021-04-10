package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import main.java.model.Resources;
import main.java.model.ScreenFactory;
import main.java.model.ScreenName;
import main.java.service.Root;

import java.net.MalformedURLException;

public class JumpButton extends Button {
    String nameOfImage = "goingDown1.png";
    int fitSize = 50;

    private ScreenName now;
    private ScreenName next;
    int place;//-1 left; 0 middle; 1 right;

    public JumpButton(ScreenName now, ScreenName next, int place){
        this.now = now;
        this.next = next;
        this.place = place;
        setView();
        setController();
    }
    public JumpButton(JumpButton but){
        this.now = but.now;
        this.next = but.next;
        this.place = but.place;
        setView();
        setController();
    }
    public void setNameOfImage(String imageName){
        this.nameOfImage = imageName;
        setView();
    }

    private void setView(){
        try {
            graphicProperty().setValue(getImage());//Устанавливаем картинку
        } catch (MalformedURLException ex) {
            setText("Jump");
        }
        setBackground(Background.EMPTY);//Убираем ненужный фон

    }
        private ImageView getImage() throws MalformedURLException{
            Resources res = new Resources();
            ImageView image = new ImageView(res.getImage(nameOfImage));
            image.setFitHeight(fitSize);
            image.setFitWidth(fitSize);
            return image;
        }

    private void setController(){
        setOnAction((ActionEvent e) -> {
           setNextScreen();
        });
    }
    public ScreenName getNow(){
        return now;
    }
    public ScreenName getNext(){
        return next;
    }
    public String getNameOfImage(){ return nameOfImage;}
    public void replaceNowAndNext(){
        ScreenName c = now;
        now = next;
        next = c;
    }
    private void setNextScreen(){
        Root root = Root.getInstance();
        Node nextScreen = ScreenFactory.getInstance().getScreen(next);
        replaceNowAndNext();
        switch (place) {
            case -1:
                root.setLeftScreen(nextScreen);
                break;
            case 0:
                root.setFullScreen(nextScreen);
                break;
            case 1:
                root.setRightScreen(nextScreen);
                break;
        }
    }
    public JumpButton clone(){
        return new JumpButton(now,next,place);
    }
}
