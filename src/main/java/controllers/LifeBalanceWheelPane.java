package main.java.controllers;

import javafx.scene.layout.Pane;

public class LifeBalanceWheelPane extends Pane {
    public LifeBalanceWheelPane(){
        LifeBalanceWheelController main = LifeBalanceWheelController.getInstance();
        this.getChildren().add(main);
        setPrefSize(main.getWidth(), main.getWidth());
    }
}
