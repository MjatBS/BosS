package main.java.service;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import main.java.controllers.JumpButton;
import main.java.controllers.ScreenSizeListener;
import main.java.model.JumpButtonFactory;
import main.java.model.ScreenFactory;
import main.java.model.ScreenName;

public class ScreensArrangement {
    /*
              0 -LEFT
              1 -CENTER
              2 -RIGHT
     */
    int[][] SCREEN_POSITIONS_IN_ROOT;
    Pane[] mainScreens;
    JumpButton[] jumpButtons;

    boolean nowIsFullScreen;
    boolean screenCompressionRequired = false; // требуется схатие экрана
    int neededWidth = 1000;

    Root root;
    private static ScreensArrangement instance=null;
    public static ScreensArrangement getInstance(){
        if(instance == null){
            instance = new ScreensArrangement();
            new ScreenSizeListener(BosS.getMainStage(), instance);
        }
        return instance;
    }
    private ScreensArrangement() {
        initPositionsInRoot();

        mainScreens = new Pane[3];
        jumpButtons = new JumpButton[3];
        root = Root.getInstance();
        nowIsFullScreen = false;
    }
        private void initPositionsInRoot(){
            SCREEN_POSITIONS_IN_ROOT = new int[3][4];
            SCREEN_POSITIONS_IN_ROOT[0] = new int[]{0, 1, 3, 1}; // LEFT
            SCREEN_POSITIONS_IN_ROOT[2] = new int[]{3, 1, 3, 1}; // RIGHT
            SCREEN_POSITIONS_IN_ROOT[1] = new int[]{2, 1, 2, 1}; // CENTER
        }

    public void setLeftScreen(Pane pane)   {
        setScreen(pane, 0);
        adjustToScreenWidth(mainScreens[0]);
    }
    public void setRightScreen(Pane pane)  {
        setScreen(pane, 2);
        adjustToScreenWidth(mainScreens[0]);
    }
    public void setCenterScreen(Pane pane) {
        setScreen(pane, 1);
    }
    /**
     *
     * @param pane is Screen
     * @param position 0 -LEFT, 1 -CENTER, 2-RIGHT
     */
    private void setScreen(Pane pane, int position){
        System.out.println("setScreen on position "+ position);
        if((position % 2 == 0 && nowIsFullScreen)
        || (position == 1 && !nowIsFullScreen)){
            clearScreen();
            nowIsFullScreen = position % 2 != 0;
        }
        setMainScreen(pane, position);
        JumpButton butForPane = JumpButtonFactory.getInstance().getButtonForScreen(pane);
        setJumpButton(butForPane, position);
        updateScreen();
    }
        private void clearScreen(){
            fullClear(mainScreens);
            fullClear(jumpButtons);
        }
            private void fullClear(Node[] arr){
                for(int i=0; i<arr.length;i++){
                    if(arr[i]!=null){
                        root.getChildren().remove(arr[i]);
                        arr[i] = null;
                    }
                }
            }

        private void setMainScreen(Pane pane, int position){
            if(mainScreens[position]!=null)root.getChildren().remove(mainScreens[position]);
            int [] pos = SCREEN_POSITIONS_IN_ROOT[position];
            root.add(pane, pos[0],pos[1],pos[2],pos[3]);
            mainScreens[position] = pane;
        }
        private void setJumpButton(JumpButton butForPane, int position){
            if(jumpButtons[position]!=null)root.getChildren().remove(jumpButtons[position]);
            int [] pos = SCREEN_POSITIONS_IN_ROOT[position];
            root.add(butForPane, pos[0],(pos[1] + 1),pos[2],pos[3]); // pos[1]+1 - на уровень ниже экранов
            jumpButtons[position] = butForPane;
        }
        private void updateScreen(){
            for(int i=0;i<mainScreens.length;i++){
                if(mainScreens[i]!=null) setMainScreen(mainScreens[i],i);
                if(jumpButtons[i]!=null) setJumpButton(jumpButtons[i],i);
            }

        }
    public void screenWidthIsChanged(boolean screenCompressionRequired){
        this.screenCompressionRequired = screenCompressionRequired;
        if(!nowIsFullScreen){
            if(screenCompressionRequired){
                adjustToScreenWidth(mainScreens[0]);
            }else{
                adjustToScreenWidth(mainScreens[0]);
                updateScreen();
            }
        }
    }
    public int getNeededWidth(){
        return neededWidth;
    }

    //dangerous code
    ///     .
    ///     .
    ///     .
    private void adjustToScreenWidth(Pane pane){
        if(screenCompressionRequired) {
            root.getChildren().remove(mainScreens[2]);
            root.getChildren().remove(mainScreens[0]);
            setMainScreen(pane,1);
            if(mainScreens[2]!=null) {
                if(jumpToRight==null) jumpToRight = createJumpToRightScreen();
                setJumpButton(jumpToRight, 2);
            }
        }else{
            if(mainScreens[1]!=null)root.getChildren().remove(mainScreens[1]);
            mainScreens[1] = null;
            if(mainScreens[2]!=null) {
                JumpButton jumpForSecondScreen = JumpButtonFactory.getInstance().getButtonForScreen(mainScreens[2]);
                setJumpButton(jumpForSecondScreen, 2);
                jumpToRight = null;
            }
        }
    }
        JumpButton jumpToRight=null;
        private JumpButton createJumpToRightScreen(){
            JumpButton jumpToRightScreen;
            JumpButton c = JumpButtonFactory.getInstance().getButtonForScreen(mainScreens[2]);
            jumpToRightScreen = new JumpButton(c.getNow(),c.getNext(),0);
            jumpToRightScreen.setOnAction((ActionEvent e) -> {
                setVirtualNextScreen(jumpToRightScreen.getNow());
                jumpToRightScreen.replaceNowAndNext();
            });
            return jumpToRightScreen;
        }
            private void setVirtualNextScreen(ScreenName name){
                Node p = ScreenFactory.getInstance().getScreen(name);
                adjustToScreenWidth((Pane) p);
            }
}

