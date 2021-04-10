package main.java.model;

import javafx.scene.Node;
import main.java.controllers.JumpButton;

import java.util.ArrayList;

public class JumpButtonFactory {
    private ArrayList<JumpButton> buttons;

    public static JumpButtonFactory instance = null;
    public static JumpButtonFactory getInstance(){
        if (instance == null) {
            instance = new JumpButtonFactory();
        }
        return instance;
    }
    private JumpButtonFactory(){
        initButtons();
    }
        private void initButtons(){
            buttons = new ArrayList();
            buttons.add(new JumpButton(ScreenName.LIFE_CALENDAR, ScreenName.DAY_TIMING, -1));
            buttons.add(new JumpButton(ScreenName.PRIORITIZATION_SQUARES,ScreenName.LIFE_BALANCE_WHEEL, 1));
            buttons.add(new JumpButton(ScreenName.DELEGATION_1, ScreenName.DELEGATION_2, 0));
        }

    public JumpButton getButtonForScreen(Node node){
        JumpButton result = buttons.get(0);
        ScreenFactory scFac = ScreenFactory.getInstance();
        ScreenName name = scFac.getScreenNameByNode(node);
        for(JumpButton but : buttons){
            if(but.getNow().equals(name)){
                result = but.clone();
            }
        }
        for(JumpButton but : buttons){
            if(but.getNext().equals(name)){
                result = but.clone();
                result.replaceNowAndNext();
            }
        }
        //return new JumpButton(result);
        return result;
    }
}
