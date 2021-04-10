package main.java.service;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.java.controllers.JumpButton;
import main.java.controllers.LifeCalendar;
import main.java.controllers.NavigationButton;
import main.java.controllers.NavigationMenuController;
import main.java.model.JumpButtonFactory;
import main.java.model.ScreenFactory;
import main.java.model.ScreenName;

/*
 * Это корень самого приложения
 * Фасад, на который в дальнейшем будет все вешаться
 * 
 * Состоит из 6 колонок (либо возможно из 3 для уменьшенного случая)
 * И из 2 строчек (уже 3)
*/
public class Root extends GridPane{
    final int HEIGHT_NAVIGATION_BUTTONS = 145;
    final int HEIGHT_JUMP_BUTTON = 100;

    Node leftMainScreen;
    Node rightMainScreen;

    Node leftJumpButton = null;
    Node rightJumpButton = null;
    Node centerJumpButton = null;

    private static Root instance;
    public static Root getInstance(){
        if(instance == null)instance = new Root();
        return instance;
    }
    private Root(){
        int quantityColumns = 6;//количество колонок должно быть кратно 3
        createColumns(quantityColumns);
        createRows();
    }
    
    
    private void createColumns(int quantityColumns){// Необходимо указывать количество, потому что, возможно, что-то может менятся
        ObservableList<ColumnConstraints> columns = getColumnConstraints();
        for(int i=0;i<quantityColumns;i++){
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/quantityColumns +1);
            column.setHalignment(HPos.CENTER);
            columns.add(column);
        }
    }
    
    private void createRows(){
        ObservableList<RowConstraints> rows = getRowConstraints();
        RowConstraints menu,mainPart,jumpButton;
        menu = getRowWithNavigationButtons();
        jumpButton = getRowWithJumpButton();
        mainPart = getRowWithMainPart();
        rows.addAll(menu,mainPart,jumpButton);
    }
        private RowConstraints getRowWithNavigationButtons(){
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(HEIGHT_NAVIGATION_BUTTONS);
            row.setMinHeight(HEIGHT_NAVIGATION_BUTTONS);
            row.setValignment(VPos.CENTER);
            row.setVgrow(Priority.NEVER);
            return row;
        }
        private RowConstraints getRowWithMainPart(){
            RowConstraints row = new RowConstraints();
            //row.setPercentHeight(100);// - главная часть занимает все оставшееся свободное место
            row.setPrefHeight(1000);
            row.setValignment(VPos.CENTER);
            row.setVgrow(Priority.ALWAYS);
            return row;
        }
        private RowConstraints getRowWithJumpButton(){
            RowConstraints row = new RowConstraints(HEIGHT_JUMP_BUTTON);
            row.setValignment(VPos.CENTER);
            row.setVgrow(Priority.SOMETIMES);
            return row;
        }

    public NavigationButton[] createNavigationMenu(){
        int quanNavButtons = 3;
        NavigationButton [] navButtons;
        navButtons = new NavigationButton[quanNavButtons];
        for(int i=0;i<quanNavButtons;i++){
            navButtons[i] = new NavigationButton(i);
        }
        int quanColumnsInRoot = getColumnConstraints().size();
        int colspan = quanColumnsInRoot/quanNavButtons;
        for(int i=0;i<quanNavButtons;i++){
            add(navButtons[i], i*colspan, 0, colspan, 1);
        }
        return navButtons;
    }
    public void initArrangement(){
        ScreensArrangement ar = ScreensArrangement.getInstance();
    }
    public void setLeftScreen(Node pane){
        ScreensArrangement.getInstance().setLeftScreen((Pane) pane);
    }
    public void setRightScreen(Node pane){
        ScreensArrangement.getInstance().setRightScreen((Pane) pane);
    }
    public void setFullScreen(Node pane){
        ScreensArrangement.getInstance().setCenterScreen((Pane) pane);
    }
    /*public void setLeftScreen(Node pane){
        setMainLeftScreen(pane);
        setLeftJumpButton(pane);
        setDimensionOfScreen();
    }*/
        private void setMainLeftScreen(Node pane){
            if(leftMainScreen!=null)this.getChildren().remove(leftMainScreen);
            this.add(pane,0,1,3,1);
            leftMainScreen = pane;
        }
        private void setLeftJumpButton(Node pane){
            deleteJumpButton(centerJumpButton); centerJumpButton = null;
            JumpButton butForPane = JumpButtonFactory.getInstance().getButtonForScreen(pane);
            if(leftJumpButton == null){
                leftJumpButton = butForPane;
                this.add(leftJumpButton,0,2,3,1);
            }
            if(!(leftJumpButton.equals(butForPane))){
                leftJumpButton = butForPane;
            }
        }
    /*public void setRightScreen(Node pane){
        setMainRightScreen(pane);
        setRightJumpButton(pane);
        setDimensionOfScreen();
    }*/
        private void setMainRightScreen(Node pane){
            if(rightMainScreen!=null)this.getChildren().remove(rightMainScreen);
            this.add(pane, 3,1,3,1);
            rightMainScreen = pane;
        }
        private void setRightJumpButton(Node pane){
            deleteJumpButton(centerJumpButton); centerJumpButton = null;
            JumpButton butForPane = JumpButtonFactory.getInstance().getButtonForScreen(pane);
            if(rightJumpButton == null){
                rightJumpButton = butForPane;
                this.add(rightJumpButton,3,2,3,1);
            }
            if(!(rightJumpButton.equals(butForPane))){
                rightJumpButton = butForPane;
            }
        }
        private void deleteJumpButton(Node but){
            if(but!=null){
                this.getChildren().remove(but);
                but = null;
            }
        }
    /*public void setFullScreen(Node pane){
        setMainFullScreen(pane);
        setCenterJumpButton(pane);
        setDimensionOfScreen();
    }*/
        private void setMainFullScreen(Node pane){
            if(leftMainScreen!=null)this.getChildren().remove(leftMainScreen);
            if(rightMainScreen!=null)this.getChildren().remove(rightMainScreen);
            this.add(pane, 2,1,2,1);//располагаем как бы в центре
            leftMainScreen = pane;
            rightMainScreen = null;
        }
        private void setCenterJumpButton(Node pane){
            deleteJumpButton(leftJumpButton);leftJumpButton = null;
            deleteJumpButton(rightJumpButton);rightJumpButton = null;

            JumpButton butForPane = JumpButtonFactory.getInstance().getButtonForScreen(pane);
            if(centerJumpButton == null){
                centerJumpButton = butForPane;
                this.add(centerJumpButton,2,2,2,1);
            }
            if(!(centerJumpButton.equals(butForPane))){
                centerJumpButton = butForPane;
            }

        }
    public void setDimensionOfScreen(){
        int prefWidth = 0;
        if(leftMainScreen != null){
            prefWidth += ((Pane) leftMainScreen).getPrefWidth();
            System.out.println("for left prefwidth is: "+((Pane) leftMainScreen).getPrefWidth());
        }
        if(rightMainScreen != null){
            prefWidth += ((Pane) rightMainScreen).getPrefWidth();
            System.out.println("for right prefwidth is "+((Pane) rightMainScreen).getPrefWidth());
        }
        System.out.println(" ::: prefWidth="+prefWidth);
        Window window = this.getScene().getWindow();
        if(prefWidth > 100){
            this.getScene().getWindow().setWidth(prefWidth);
        }
    }
}
