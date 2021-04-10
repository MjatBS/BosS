/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.model.Resources;

/**
 * FXML Controller class
 *
 * @author Bogdan
 */
public class DayTimingController implements Initializable {
    final String nameImageDivideTime = "plusForAdd.png";
    String firstAlternateColor = "#FFFFFF";
    String secondAlternateColor = "#F2F2F2";
    
    List<Label> labsToWrite;
    List<Label> labsWithTime;
    Node divideTime;
    
    @FXML HBox dayTiming;
    @FXML VBox toWrite;
    @FXML VBox time;
    @FXML VBox emptyColumn;
    
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initArrays();
        createButtonDivideTime();
        createListenersForToWrite();
        createListenersForLabelsTime();
        fillLabsToWriteByAlternateColors();
    }
    void initArrays(){
        labsToWrite = new ArrayList();
        toWrite.getChildren().forEach((n) -> {
            labsToWrite.add((Label) n);
        });
        labsWithTime = new ArrayList();
        for(Node element: time.getChildren()){
            labsWithTime.add((Label) element);
        }
        
        
    }
    
    void createButtonDivideTime(){
        try{
            setImageForDivideTime();
        }catch(MalformedURLException e){
            divideTime = new Button("+");
        }
        //divideTime.setMinSize(20,20);divideTime.setMaxSize(20,20);

        divideTime.setOnMouseClicked((MouseEvent ae) -> {
            int index = emptyColumn.getChildren().indexOf(divideTime);
            if(index!=-1){
                addRowToTable(index+1);//because we add row in the second part of time
                
                buttonDivideTimeToEmptyLabel(index);
                labsWithTime.get(index).setOnMouseEntered(null);
                initArrays();
                fillLabsToWriteByAlternateColors();
            }
        });
    }
        private void setImageForDivideTime() throws MalformedURLException{
            Resources res = new Resources();
            ImageView im = new ImageView(res.getImage(nameImageDivideTime));
            im.setFitHeight(20);im.setFitWidth(20);
            divideTime = im;
        }
    void addRowToTable(int index){
        Label emptyLab = new Label();
        emptyLab.getStyleClass().add("empty-column");
        emptyColumn.getChildren().add(index,emptyLab);
        divTime(index);
        Label toWriteInf = new Label();
        createListenerForToWrite(toWriteInf);
        toWriteInf.getStyleClass().add("write-information");
        toWrite.getChildren().add(index,toWriteInf);
    }
        private void divTime(int index){
            Label secondTime = new Label();
            secondTime.getStyleClass().add("time");
            Label oldTime = (Label) time.getChildren().get(index-1);    //17.00-18.00
            secondTime.setText(oldTime.getText().substring(0, 3) + "30-" + oldTime.getText().substring(6, 11));
            oldTime.setText(oldTime.getText().substring(0, 6) + secondTime.getText().substring(0,5));
            
            time.getChildren().add(index,secondTime);
        }
        
    void createListenersForToWrite(){
        for(int i=1;i<labsToWrite.size();i++){
            Label lab = labsToWrite.get(i);
            createListenerForToWrite(lab);
        }
    }
        private void createListenerForToWrite(Label lab){
            lab.setOnMouseClicked((MouseEvent event) -> {
                buttonDivideTimeToEmptyLabel();//Делается для того, чтобы не возникло левой ошибки
                TextField fieldToWrite = new TextField();
                fieldToWrite.setOnKeyPressed((KeyEvent event1) -> {
                    if (event1.getCode() == KeyCode.ENTER) {
                        String text = fieldToWrite.getText();
                        int num = toWrite.getChildren().indexOf(fieldToWrite);
                        Label lab1 = labsToWrite.get(num);
                        if (text!=null) {
                            lab1.setText(text);
                        }
                        toWrite.getChildren().set(num, lab1);
                    }
                });
                
                int num = labsToWrite.indexOf(lab);
                toWrite.getChildren().set(num, fieldToWrite);
            });
        }
    Label thereIsButtonDivideTime = null;
    void createListenersForLabelsTime(){
        for(int i=1;i<labsWithTime.size();i++){
            Label lab = labsWithTime.get(i);
            createListenerForLabelTime(lab);
        }
        
    }
        private void createListenerForLabelTime(Label lab){
            lab.setOnMouseEntered((MouseEvent e) ->{
                if(thereIsButtonDivideTime!=null){
                    int index = labsWithTime.indexOf(thereIsButtonDivideTime);
                    
                    buttonDivideTimeToEmptyLabel(index);
                }
                thereIsButtonDivideTime = lab;
                int index = labsWithTime.indexOf(lab);
                
                emptyColumn.getChildren().set(index,divideTime);
            });
        }
    
    private void buttonDivideTimeToEmptyLabel(int index){
        Label emptyLab = new Label();
        emptyLab.getStyleClass().add("empty-column");
        emptyColumn.getChildren().set(index,emptyLab);
    }
    private void buttonDivideTimeToEmptyLabel(){
        int index = labsWithTime.indexOf(thereIsButtonDivideTime);
        if(index!=-1)
        buttonDivideTimeToEmptyLabel(index);
    }
    void fillLabsToWriteByAlternateColors(){
        for(int i=1;i<labsToWrite.size();i++){
            if(i%2==1)labsToWrite.get(i).setStyle("-fx-background-color: "+firstAlternateColor + ";");
            else labsToWrite.get(i).setStyle("-fx-background-color: "+secondAlternateColor + ";");
        }
    }
}
