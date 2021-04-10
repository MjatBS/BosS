/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.service;

import main.java.service.Sector;
import main.java.model.XmlParsing;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.xpath.XPathExpressionException;
import main.java.model.Resources;
import main.java.controllers.LifeBalanceWheelController;

/**
 *
 * @author Jarvis
 */
public class DeletingSector extends Stage {
    ListView listOfNames;
    ArrayList<String> names;
    public DeletingSector(){
        this.setScene(new Scene(createPane()));
        this.setWidth(200);
        this.setHeight(200);
        this.initModality(Modality.APPLICATION_MODAL);this.showAndWait();
    }
    private Pane createPane(){
        ArrayList<Sector> sectors = LifeBalanceWheelController.getInstance().getSectors();
        names = new ArrayList();
        for(Sector s:sectors){
            names.add(s.title);
        }
        listOfNames = new ListView(FXCollections.observableArrayList(names));
        
        Button delete = new Button("Удалить");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = listOfNames.getSelectionModel().getSelectedIndex();
                if(index!=-1 && confirmation(names.get(index))){
                    XmlParsing parser = new XmlParsing();
                    LifeBalanceWheelController contr = LifeBalanceWheelController.getInstance();
                    
                    String condition= "[@name='"+names.get(index)+"']";
                    StringBuffer pathTo = new StringBuffer(contr.XML_WAY_TO_CHARACTERISTICS);
                    pathTo.insert(pathTo.lastIndexOf("/"), condition);
                    String query = pathTo.substring(0, pathTo.length()-2);
                    System.out.println(query);
                    try {
                        parser.deleteNode(new Resources().getXml(contr.XML_FILE), query);
                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(DeletingSector.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    closeStage();
                }
            }
        });
        VBox pane = new VBox(listOfNames,delete);
        return pane;
    }
    void closeStage(){
        this.close();
    }
    
    boolean confirm=false;
    Stage stageConfirm;
    boolean confirmation(String name){
        int width = 200;
        stageConfirm = new Stage();
        Label someText = new Label("Вы уверены, что хотите удалить "+ name+" из своей жизни?");
        someText.setMaxWidth(180);someText.setWrapText(true);
        Button yes = new Button("Да"); Button no = new Button("Нет");
        FlowPane pane = new FlowPane(someText,yes,no);
        pane.setVgap(10);pane.setHgap(20); pane.setPadding(new Insets(10,10,10,10));
        
        yes.setOnAction((ActionEvent e) -> {
            confirm = true;
            stageConfirm.close();
        });
        no.setOnAction((ActionEvent e) -> {
            confirm = false;
            stageConfirm.close();
        });
        stageConfirm.setScene(new Scene(pane));
        stageConfirm.setWidth(width);
        stageConfirm.setHeight(150);
        stageConfirm.initModality(Modality.APPLICATION_MODAL);stageConfirm.showAndWait();
        
        return confirm;
    }
    
}
