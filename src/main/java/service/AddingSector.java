/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.service;

import main.java.service.Sector;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.model.Resources;
import main.java.controllers.LifeBalanceWheelController;

/**
 *
 * @author Jarvis
 */
public class AddingSector extends Stage {
    final String DIR_WITH_IMAGES = "lifeBalanceWheel";
    final int ICON_SIZE=40;
    
    TextField name;
    Image[] imgs;
    String[] relativeWays;
    ToggleGroup group;
    
    public AddingSector(){
        this.setScene(new Scene(createPane()));
        this.setWidth(360);
        this.setHeight(400);
        this.initModality(Modality.APPLICATION_MODAL);this.showAndWait();
    }
    private Pane createPane(){
        VBox pane = new VBox();
        pane.setSpacing(7);
        createTop(pane);System.out.println("top created");
        createCenter(pane);System.out.println("center created");
        createBottom(pane);System.out.println("bottom created");
        return pane;
    }
    private void createTop(VBox pane){
        Label label = new Label("Введите название нового сектора: ");
        name = new TextField();
        pane.getChildren().addAll(label,name);
    }
    private void createCenter(VBox pane){
        getImagesFromDir();
        group = new ToggleGroup();
        ToggleButton[] btns = createBtnsImgs(group); btns[0].setSelected(true);
       
        Label label2 = new Label("Выберите иконку");
        GridPane grid = new GridPane();
        grid.setHgap(10);grid.setVgap(10);
        int numOfRow = -1;
        for(int i=0;i<btns.length;i++){
            if(i%5==0)numOfRow++;
            grid.add(btns[i], i - (numOfRow*5), numOfRow);
        }

        pane.getChildren().addAll(label2,grid);

    }
    
    private void getImagesFromDir(){
        Resources res = new Resources();
        File[] ims = res.getListOfImages(DIR_WITH_IMAGES);
        imgs = new Image[ims.length];
        relativeWays = new String[ims.length];
        for(int i=0;i<ims.length;i++){
            imgs[i] = new Image(ims[i].toURI().toString());
            relativeWays[i] = DIR_WITH_IMAGES+'/'+ims[i].getName();
        }
    }
    private ToggleButton[] createBtnsImgs(ToggleGroup group){
        ToggleButton[] btns = new ToggleButton[imgs.length];
        for(int i=0;i<btns.length;i++){
            ImageView im = new ImageView(imgs[i]); 
            im.setFitHeight(ICON_SIZE);im.setFitWidth(ICON_SIZE);
            btns[i] = new ToggleButton("",im);
            btns[i].setUserData(relativeWays[i]);
            btns[i].setToggleGroup(group);
        }
        return btns;
    }
    private void createBottom(VBox pane){
        Button ok = new Button("OK ");
        Label error = new Label("");
        pane.getChildren().addAll(ok,error);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Toggle selected = group.getSelectedToggle();
                if(checkNewName() && selected!=null){
                    System.out.println("Create new Sector");
                    LifeBalanceWheelController parent = LifeBalanceWheelController.getInstance();
                    parent.addNewSector(name.getText(), selected.getUserData().toString());
                    closeWindow();
                }else{
                    error.setText("Неверно введены данные");
                }
            }
        });
    }
    private void closeWindow(){
        this.close();
    }
    private boolean checkNewName(){
        String text = name.getText();
        if(text.isEmpty()){
            return false;
        }
        boolean result = true;
        ArrayList<Sector> sectors = LifeBalanceWheelController.getInstance().getSectors();
        for (Sector sector : sectors) {
            if (text.equals(sector.title)) {
                result = false;
            }
        }
        return result;
    }
}
