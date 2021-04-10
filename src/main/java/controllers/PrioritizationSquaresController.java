/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.model.Resources;
import main.java.service.DragAndDrop;

/**
 * FXML Controller class
 *
 * @author Jarvis
 */
public class PrioritizationSquaresController implements Initializable{
    double MIN_WIDTH;
    
    List<Labeled> recom;
    List<BorderPane> squares;
    List<Pane> centers;
    
    @FXML FlowPane PanePriorSquares;
    @FXML FlowPane recommendations;
    @FXML HBox prSquares;
    @FXML Button b1,b2,b3;
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recom = new ArrayList(); squares = new ArrayList();
        for(Node n:recommendations.getChildren()) recom.add((Label) n);   
        for(Node n:prSquares.getChildren()) squares.add((BorderPane) n);
        centers = new ArrayList();
        for(BorderPane square:squares){
            centers.add((VBox) square.getCenter());
        }
        setNeededImages();
        setDragAndDrop();
    }
    
    private void setDragAndDrop(){
        DragAndDrop dad = new DragAndDrop() {
            @Override
            public void dockingPaneAndComponent(Pane pane, Labeled component) {
                Label lab = new Label(component.getText());
                setStyleForPhraseInSquare(pane,lab);
                pane.getChildren().add(lab);
            }
        };
        dad.dragAndDropComponents(PanePriorSquares, centers, recom);
        
    }
    
    void setStyleForPhraseInSquare(Pane pane, Labeled lab){
        lab.setPrefWidth(pane.getPrefWidth());
        lab.setStyle("-fx-background-color: white;"
                    + "-fx-border-color: #DDDDDD;"+
                    "-fx-border-width: 0 0 1 0;");
    }

 /*   private void setSquaresLocation(){
        /*
        Важное примечание:
        widthSize не будет являтся константой !!!!
        */
      /*  for(BorderPane square:squares){
            square.setPrefWidth(widthSquare);
            
            Label top = (Label) square.getTop();
            top.setPrefWidth(widthSquare);
            VBox center = (VBox) square.getCenter();
            center.setPrefSize(widthSquare, 250);
            Button bottom = (Button) square.getBottom();
            bottom.setPrefWidth(widthSquare);
        }*/
    
    void setSpacingPaddingForSquares(){
        
    }
    @FXML
    void addPhrase(ActionEvent ae){
        String strNum = (ae.getSource().toString().substring(11, 12));
        int num = Integer.parseInt(strNum)-1;
        BorderPane square = squares.get(num);
        
        Button prev = (Button) ae.getSource();
        TextField forPhrase = new TextField();
        square.setBottom(forPhrase);System.out.println(b1);
        
        forPhrase.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    if(forPhrase.getText()!=null && forPhrase.getText().length()!=0){
                        Label phrase = new Label(forPhrase.getText());
                        VBox center = (VBox) square.getCenter();
                        setStyleForPhraseInSquare(center,phrase);
                        center.getChildren().add(phrase);
                    }
                    
                    squares.get(num).setBottom(prev);
                }
            }
        });
    }

    @FXML void butClicked(ActionEvent e){
        
    }
    void setNeededImages(){
        Resources res = new Resources();
        b1.setGraphic(new ImageView(res.getImage("plusForAdd.png")));
        b2.setGraphic(new ImageView(res.getImage("plusForAdd.png")));
        b3.setGraphic(new ImageView(res.getImage("plusForAdd.png")));
    }
}
