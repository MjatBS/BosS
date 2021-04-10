/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.model;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import main.java.controllers.LifeCalendar;

/**
 *
 * @author Bogdan
 */
public class Resources {
    //OR LinksToResources
    /*
        main
            java
                controllers
                service
                model
            resources
                view
                css
        
        images
        data
    */

    
    
    public URL getFxml(String name){
        String pathToFxml = "/main/resources/view/";
        return getClass().getResource(pathToFxml+name);
    }
    public Image getImage(String name){
        String pathToImages = "images/";
        try {
            InputStream input = new FileInputStream(pathToImages+name);
            return new Image(input);
        } catch (FileNotFoundException ex) {
            return null;
        }
    }
    public InputStream getCssAsStream(String name){
        String pathToCss = "/main/resources/css/";
        return getClass().getResourceAsStream(pathToCss+name);
    }
    public File getXml(String name){
        String pathToXml = "data/";
        return new File(pathToXml+name);
    }
    public File[] getListOfImages(String nameOfDir){
        File[] result = null;
        String pathToImages = "images/";
        File dir = new File(pathToImages+nameOfDir);
        if(dir.exists() && dir.isDirectory()){
           FileFilter filter = new FileFilter() {
               @Override
               public boolean accept(File pathname) {
                   String name = pathname.getName();
                   return (name.endsWith(".png")|| name.endsWith(".jpg")||name.endsWith("jpeg")||name.endsWith(".bmp"));
               }
           };
            result = dir.listFiles(filter);
        }
        return result;
    }
}
