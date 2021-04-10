/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import main.java.controllers.*;

public class ScreenFactory {
    private Resources res;
    
    private static ScreenFactory instance = null;
    public static ScreenFactory getInstance(){
        if(instance == null)instance = new ScreenFactory();
        return instance;
    }
    
    private Map<ScreenName, Node> screens;
    private Map<Node, ScreenName> sneercs;

    private ScreenFactory(){
        res = new Resources();
        initScreens();
        initReverse();
    }
    private void initScreens(){
        try {
            screens = new HashMap();
            screens.put(ScreenName.LIFE_CALENDAR, new LifeCalendar());
            screens.put(ScreenName.PRIORITIZATION_SQUARES, FXMLLoader.load(res.getFxml("PrioritizationSquares.fxml")));
            screens.put(ScreenName.DAY_TIMING, FXMLLoader.load(res.getFxml("DayTiming.fxml")));
            //screens.put(ScreenName.LIFE_BALANCE_WHEEL, LifeBalanceWheelController.getInstance());
            screens.put(ScreenName.LIFE_BALANCE_WHEEL, new LifeBalanceWheelPane());
            screens.put(ScreenName.DELEGATION_1, FXMLLoader.load(res.getFxml("Delegation1.fxml")));
            screens.put(ScreenName.DELEGATION_2, FXMLLoader.load(res.getFxml("Delegation2.fxml")));

        } catch (IOException ex) {
            Logger.getLogger(ScreenFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void initReverse(){
        sneercs = new HashMap();
        screens.forEach((k, m) -> {
            sneercs.put(m, k);
        });
    }
    
    public Node getScreen(ScreenName name){
        return screens.get(name);
    }
    public ScreenName getScreenNameByNode(Node node){
        return sneercs.get(node);
    }
}
