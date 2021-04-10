/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.service;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Bogdan
 */
public abstract class DragAndDrop {
    /*final double opacity = 0.4;
    
    int difX=-1,difY=-1;
    int firstX,firstY;
    public void dragAndDropComponents(Pane root,List<Pane> panes,List<Node> components){
        
        for(Node lab:components){
            lab.setOnMouseDragged((MouseEvent m) -> {
                lab.setTranslateX(m.getSceneX()-difX);
                lab.setTranslateY(m.getSceneY()-difY);
                
                
            });
            lab.setOnMousePressed((MouseEvent m) -> {
                System.out.println("MousePressed");
                if(difX==-1){
                    difX = (int) (m.getSceneX());
                    difY = (int) (m.getSceneY());
                }
                
                for(Pane pane:panes)pane.setOpacity(opacity);//Устанавивает прозрачность для панелей, чтобы было видно переносимую лэбел
            });
            lab.setOnMouseReleased((MouseEvent m) -> {
                System.out.println("MouseReleased");
                lab.setTranslateX(0);
                lab.setTranslateY(0);
                difX=-1;
                difY=-1;
                Pane foundedPane = belongToPanes(panes,m.getSceneX(),m.getSceneY());
                if(foundedPane != null){
                    removeComponentFromPane(root,lab);
                    dockingPaneAndComponent(foundedPane,lab);
                    lab.setOnMouseDragged(null);
                    lab.setOnMousePressed(null);
                    lab.setOnMouseReleased(null);
                }
                
                for(Pane pane:panes)pane.setOpacity(1);//Устанавивает прозрачность для панелей, чтобы было видно переносимую лэбел
            });
        }
    }
    private Pane belongToPanes(List<Pane> panes, double x, double y){
        Pane foundedPane = null;
        for(Pane p:panes){
            if(belongToPane(p,x,y)){
                foundedPane = p;
            }
        }
        return foundedPane;
    }
        private boolean belongToPane(Pane paen, double x,double y){
            double paenX,paenY,paenWidth,paenHeight;
            paenX = paen.localToScene(paen.getBoundsInLocal()).getMinX();
            paenY = paen.localToScene(paen.getBoundsInLocal()).getMinY();
            paenWidth = paen.localToScene(paen.getBoundsInLocal()).getWidth();
            paenHeight = paen.localToScene(paen.getBoundsInLocal()).getHeight();

            return  (x>paenX && x<(paenX+paenWidth) && y>paenY && y<(paenY+paenHeight));
        }
        private void removeComponentFromPane(Pane root, Node component){
            root.getChildren().remove(component);
        }
    public abstract void dockingPaneAndComponent(Pane pane, Node component);*/
    
    /*
     В этом случае используется Labeled так как он позволяет устанавливать размер объекта
    */
    
    final double opacity = 0.4;
    
    int difX=-1,difY=-1;
    int firstX,firstY;
    public void dragAndDropComponents(Pane root,List<Pane> panes,List<Labeled> components){
        
        for(Labeled lab:components){
            lab.setOnMouseDragged((MouseEvent m) -> {
                lab.setTranslateX(m.getSceneX()-difX);
                lab.setTranslateY(m.getSceneY()-difY);
                
                
            });
            lab.setOnMousePressed((MouseEvent m) -> {
                System.out.println("MousePressed");
                if(difX==-1){
                    difX = (int) (m.getSceneX());
                    difY = (int) (m.getSceneY());
                }
                
                for(Pane pane:panes)pane.setOpacity(opacity);//Устанавивает прозрачность для панелей, чтобы было видно переносимую лэбел
            });
            lab.setOnMouseReleased((MouseEvent m) -> {
                System.out.println("MouseReleased");
                lab.setTranslateX(0);
                lab.setTranslateY(0);
                difX=-1;
                difY=-1;
                Pane foundedPane = belongToPanes(panes,m.getSceneX(),m.getSceneY());
                if(foundedPane != null){
                    removeComponentFromPane(root,lab);
                    dockingPaneAndComponent(foundedPane,lab);
                    lab.setOnMouseDragged(null);
                    lab.setOnMousePressed(null);
                    lab.setOnMouseReleased(null);
                }
                
                for(Pane pane:panes)pane.setOpacity(1);//Устанавивает прозрачность для панелей, чтобы было видно переносимую лэбел
            });
        }
    }
    private Pane belongToPanes(List<Pane> panes, double x, double y){
        Pane foundedPane = null;
        for(Pane p:panes){
            if(belongToPane(p,x,y)){
                foundedPane = p;
            }
        }
        return foundedPane;
    }
        private boolean belongToPane(Pane paen, double x,double y){
            double paenX,paenY,paenWidth,paenHeight;
            paenX = paen.localToScene(paen.getBoundsInLocal()).getMinX();
            paenY = paen.localToScene(paen.getBoundsInLocal()).getMinY();
            paenWidth = paen.localToScene(paen.getBoundsInLocal()).getWidth();
            paenHeight = paen.localToScene(paen.getBoundsInLocal()).getHeight();

            return  (x>paenX && x<(paenX+paenWidth) && y>paenY && y<(paenY+paenHeight));
        }
        private void removeComponentFromPane(Pane root, Labeled component){
            root.getChildren().remove(component);
        }
    public abstract void dockingPaneAndComponent(Pane pane, Labeled component);
}
