/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.service;

import main.java.model.CssParsingForJava;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author Bogdan
 */
public class Sector {
    private int value;
    private Color color;
    private double startAngle;
    private double arcExtend;//Угол дуги
    public Image icon;
    public String title;
    final String cssFileWithColors = "design.css";
    final Color COLORS_FOR_SECTORS[];
    
    public Sector(int value,Image icon, String title, double startAngle, double arcExtend){
        CssParsingForJava cssParsing = new CssParsingForJava(cssFileWithColors);
        COLORS_FOR_SECTORS = cssParsing.getColorsForSectors();
        setValue(value);
        this.icon=icon;
        this.title=title;
        this.startAngle = startAngle;
        this.arcExtend = arcExtend;
    }
    public Sector(double startAngle, double arcExtend) {
        this.startAngle = startAngle;
        this.arcExtend = arcExtend;
        CssParsingForJava cssParsing = new CssParsingForJava(cssFileWithColors);
        COLORS_FOR_SECTORS = cssParsing.getColorsForSectors();
    }
    
    public void setValue(int value) {
        this.value = value;
        if(value!=0)color = COLORS_FOR_SECTORS[value-1];
    }

    public int getValue() {
        return value;
    }
    
    public void setArcExtend(double arcExtend){
        this.arcExtend=arcExtend;
    }
    public double getArcExtend(){
        return arcExtend;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public double getStartAngle() {
        return startAngle;
    }
    

    public Color getColor() {
        return color;
    }
    
}
