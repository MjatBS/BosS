package main.java.controllers;
import javafx.event.ActionEvent;
/**
 *
 * @author Jarvis
 */
public class NavigationMenuController {
    int activeButton = -1;
    NavigationButton [] navButtons;
    
    public NavigationMenuController(NavigationButton[] navButtons, int firstScreen){
        this.navButtons=navButtons;
        setScreen(navButtons[firstScreen]);
        controlButtons();
    }
    private void controlButtons(){
        for (NavigationButton navButton : navButtons) {
            setActionForButton(navButton);
        }
    }
        private void setActionForButton(NavigationButton but){
            but.setOnAction((ActionEvent ActionEvent) -> {
                if(activeButton == -1){
                    activeButton = but.getNumButton();
                    but.setActiveButton(but);
                    but.setMainScreen();
                }
                if(activeButton != but.getNumButton()){
                    NavigationButton prev = navButtons[activeButton];
                    prev.setPassiveButton(prev);
                    
                    setScreen(but);
                }
            });
        }
    private void setScreen(NavigationButton but){//navButton отвечает за свой экран
        activeButton = but.getNumButton();
        but.setActiveButton(but);
        but.setMainScreen();
    }
}
