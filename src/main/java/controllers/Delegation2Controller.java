/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javax.xml.xpath.XPathExpressionException;

import main.java.model.Resources;
import main.java.model.XmlParsing;

/**
 * FXML Controller class
 *
 * @author Jarvis
 */
public class Delegation2Controller implements Initializable {
    final String XML_FILE = "delegation2Data.xml";
    String queryForGetLabels = "/root/steps/step[@num='№']/label/@text";
    String [][] labelsForSteps;
    @FXML
    ImageView clock1,clock2,clock3,clock4;
    @FXML
    Button person1,person2,person3,person4;
    @FXML
    VBox step1,step2,step3,step4;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setImages();
        initLabels();
        addListeners();
        setSteps(1);
    }  
    void setImages(){
        Resources res = new Resources();
        clock1.setImage(res.getImage("clock1.png"));
        clock2.setImage(res.getImage("clock2.png"));
        clock3.setImage(res.getImage("clock3.png"));
        clock4.setImage(res.getImage("clock4.png"));
    }
    void initLabels(){
        labelsForSteps = new String[4][4];
        Resources res = new Resources();
        File file = res.getXml(XML_FILE);
        XmlParsing xml = new XmlParsing();
        for(int step=1;step<=4;step++){
            try {
                String newGetLabels = queryForGetLabels.replace('№', (""+step).charAt(0));
                String[] labelsForStep = xml.getDataList(file, newGetLabels);
                labelsForSteps[step-1] = labelsForStep;
            } catch (XPathExpressionException ex) {
                Logger.getLogger(Delegation2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void addListeners(){
        addListener(person1);
        addListener(person2);
        addListener(person3);
        addListener(person4);
    }
    private void addListener(Button but){
        but.setOnAction((ActionEvent e) -> {
            String name = but.getId();  
            int num = Integer.parseInt(name.substring(name.length()-1));
            setSteps(num);
        });
    }
    
    private void setSteps(int numPerson){
        String queryForGetColor = "/root/persons/person[@num='"+numPerson+"']/step[@num='№']/@color";
        String queryForGetDo = "/root/persons/person[@num='"+numPerson+"']/step[@num='№']/@do#";//< № # > !!
        String colorForDo = "#000000";
        String colorForNotDo = "#AFBFAE";
        
        Resources res = new Resources();
        File file = res.getXml(XML_FILE);
        XmlParsing xml = new XmlParsing();
        
        for(int step=1;step<=4;step++){
            
            try {
            VBox boxStep = getNeededVBox(step);
            String newGetColor = queryForGetColor.replace('№', (""+step).charAt(0));
            String color = xml.getDataString(file,newGetColor);
            boxStep.setStyle("-fx-background-color: "+color+";");
            for(int i=0;i<labelsForSteps[step-1].length;i++){
                String newGetDo = queryForGetDo.replace('№', (""+step).charAt(0)).replace('#', (""+i).charAt(0));
                String strToDo = xml.getDataString(file, newGetDo);
                boolean toDo = Boolean.parseBoolean(strToDo);
                Label lab = (Label) boxStep.getChildren().get(i);
                if(toDo){
                    lab.setTextFill(Color.web(colorForDo));
                }else {
                    lab.setTextFill(Color.web(colorForNotDo));
                }
                if(lab.getText().isEmpty())lab.setText(labelsForSteps[step-1][i]);
            }
            
            } catch (XPathExpressionException ex) {
                Logger.getLogger(Delegation2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        private VBox getNeededVBox(int step){
            VBox result=null;
            switch(step){
                case 1: result = step1; break;
                case 2: result = step2; break;
                case 3: result = step3; break;
                case 4: result = step4; break;
            }
            return result;
        }
        
}
