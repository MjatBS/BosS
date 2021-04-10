package main.java.controllers;

import javafx.beans.value.ChangeListener;
import javafx.stage.Stage;
import main.java.service.Root;
import main.java.service.ScreensArrangement;

public class ScreenSizeListener {
    /**
     * Notify when width crosses the threshold width
     */
    Stage mainStage;
    ScreensArrangement subscriber;
    int thresholdWidth;
    public ScreenSizeListener(Stage stage, ScreensArrangement subscriber){
        mainStage = stage;
        this.subscriber = subscriber;
        setListener();
    }
    private void setListener(){
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
        {
            thresholdWidth = subscriber.getNeededWidth();
            if((double)oldValue > thresholdWidth  && (double)newValue < thresholdWidth){
                notifySubscriber(true);
            }
            if((double)oldValue < thresholdWidth  && (double)newValue > thresholdWidth){
                notifySubscriber(false);
            }
        };

        mainStage.widthProperty().addListener(stageSizeListener);
    }
        private void notifySubscriber(boolean smallerThreshold){
        subscriber.screenWidthIsChanged(smallerThreshold);
        }

}
