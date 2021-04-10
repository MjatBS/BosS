/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.controllers;

import main.java.service.DeletingSector;
import main.java.service.AddingSector;
import main.java.service.Sector;
import main.java.model.XmlParsing;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.xpath.XPathExpressionException;
import main.java.model.Resources;

/**
 *
 * @author Bogdan
 */
public class LifeBalanceWheelController extends Canvas {
    final int WIDTH_CANVAS=500,HEIGHT_CANVAS=500,BIGGEST_RADIUS=150;
    final int SIZE_ICON = 35;
    
    final int X0=WIDTH_CANVAS/2,Y0=HEIGHT_CANVAS/2;
    final int MIN_RADIUS = (int) BIGGEST_RADIUS/10;
    final int DISTANCE_FOR_ICONS = BIGGEST_RADIUS + 56;
    final Point CENTER_BUT_ADD = new Point(WIDTH_CANVAS-100,45);
    final Point CENTER_BUT_DELETE = new Point(WIDTH_CANVAS-40,45);
    
    double regularArcExtend;
    int quantitySectors;
    //брать из модельки
    public final String XML_FILE = "lifeBalanceWheelData.xml";
    public final String XML_WAY_TO_CHARACTERISTICS = "/root/categories/category/@";
    public final String[] XML_CHARACTERISTICS = {"name","icon","value"};
    
    ArrayList<Sector> sectors;
    GraphicsContext graph;
    Resources res;
    private static LifeBalanceWheelController instance=null;
    public static LifeBalanceWheelController getInstance(){
        if(instance==null)instance = new LifeBalanceWheelController();
        return instance;
    }
    private LifeBalanceWheelController(){
        init();        
    }
    public ArrayList<Sector> getSectors(){
        return new ArrayList(sectors);
    }
    public void init(){
        res = new Resources();
        setDimensions();
        createSectors();
        graph = getGraphicsContext2D();
        addListener();
        createButtonsAddDelete();
        
        drawWheel();
    }
        private void setDimensions(){
            setWidth(WIDTH_CANVAS);
            setHeight(HEIGHT_CANVAS);
        }
        void createButtonsAddDelete(){
            drawControlButtons();
        }
        
        private void createSectors(){
            XmlParsing parser = new XmlParsing();
            String[] names = getNamesOfSectors(parser);
            Image[] icons = getIconsOfSectors(parser);
            Integer[] values = getValuesOfSectors(parser);
            
            setQuantitySectors(names.length);
            sectors = new ArrayList();
            double startAngle=0;
            for(int i=0;i<quantitySectors;i++){
                sectors.add(new Sector(values[i],icons[i],names[i],startAngle,regularArcExtend));
                startAngle+=regularArcExtend;
            }
        }
            private void setQuantitySectors(int size){
                quantitySectors = size;
                regularArcExtend = 360/quantitySectors;
            }
        
            private String[] getNamesOfSectors(XmlParsing parser){
                try{
                String[] namesOfCategories = 
                        parser.getDataList(res.getXml("lifeBalanceWheelData.xml"),
                                XML_WAY_TO_CHARACTERISTICS+XML_CHARACTERISTICS[0]);
                return namesOfCategories;
                } catch (XPathExpressionException ex) {
                    Logger.getLogger(LifeBalanceWheelController.class.getName()).log(Level.SEVERE, null, ex);
                    getListExceptionHandler();
                    return null;
                }
            }
            private Image[] getIconsOfSectors(XmlParsing parser){
                try{
                    String[] stringIcons = 
                            parser.getDataList(res.getXml("lifeBalanceWheelData.xml"),
                                    XML_WAY_TO_CHARACTERISTICS+XML_CHARACTERISTICS[1]);
                    Image[] iconsOfSectors = new Image[stringIcons.length];
                    for(int i=0;i<stringIcons.length;i++){
                        iconsOfSectors[i]=(res.getImage(stringIcons[i]));
                    }return iconsOfSectors;
                    
                } catch (XPathExpressionException ex) {
                    Logger.getLogger(LifeBalanceWheelController.class.getName()).log(Level.SEVERE, null, ex);
                    getListExceptionHandler();
                    return null;
                }
                
            }
            private Integer[] getValuesOfSectors(XmlParsing parser){
            try {
                String[] stringValues =
                        parser.getDataList(res.getXml("lifeBalanceWheelData.xml"),
                                XML_WAY_TO_CHARACTERISTICS+XML_CHARACTERISTICS[2]);
                Integer[] valuesOfSectors = new Integer[stringValues.length];
                for(int i=0;i<stringValues.length;i++){
                    valuesOfSectors[i]=(Integer.parseInt(stringValues[i]));
                }
                return valuesOfSectors;
            } catch (XPathExpressionException ex) {
                Logger.getLogger(LifeBalanceWheelController.class.getName()).log(Level.SEVERE, null, ex);
                getListExceptionHandler();
                return null;
            }
            }
            private void getListExceptionHandler(){
                System.out.println("-------------------------------------------------------------------");
                System.out.println("Check the structure of lifeBalanceWheelData.xml");
                System.out.println("-------------------------------------------------------------------");
            }
            
    void strokeArc(Color fill, double radius, double startAngle, double arcExtend){
        ArcType closure = ArcType.ROUND;
        graph.setLineWidth(2);//from ---
        graph.setStroke(Color.BLACK);//from ---
        graph.setFill(fill);
        graph.fillArc(X0-radius, Y0-radius, radius*2, radius*2, startAngle, arcExtend, closure);
        graph.strokeArc(X0-radius, Y0-radius, radius*2, radius*2, startAngle, arcExtend, closure);
        graph.setLineWidth(1);
    }
    void drawSector(Sector sector){
        strokeArc(sector.getColor(),sector.getValue()*MIN_RADIUS,sector.getStartAngle(),sector.getArcExtend());
    }
    void strokeCircle(double radius){
        graph.strokeOval(X0-radius, Y0-radius, radius*2, radius*2);
    }
    void fillCircle(Color fill,double radius){
        graph.setFill(fill);
        graph.fillOval(X0-radius, Y0-radius, radius*2, radius*2);
        graph.strokeOval(X0-radius, Y0-radius, radius*2, radius*2);
    }
    /**
     * 
     * @param image
     * @param distance - distance to center of image
     * @param angle - angle to from center Of circle to center of picture
     */
    void drawFullIcon(String text,Image image,double angle){
        Point centerOfImage = getPointInPolarCoordinates(DISTANCE_FOR_ICONS,angle);
        double x = centerOfImage.x - SIZE_ICON/2;
        double y = centerOfImage.y - SIZE_ICON/2;
        graph.drawImage(image, x, y, SIZE_ICON, SIZE_ICON);
        Point startText = getStartOfText(text,centerOfImage);
        
        graph.strokeText(text, startText.x, startText.y);
    }
        private Point getPointInPolarCoordinates(double distance,double angle){
            double x = X0 + distance*Math.cos(Math.toRadians(angle));
            double y = Y0 - distance*Math.sin(Math.toRadians(angle));
            return new Point((int)x,(int)y);
        }
        private Point getStartOfText(String text, Point centerOfImage){
            double onePun = graph.getFont().getSize()/2; 
            double textLength = text.length()*onePun;
            int x =(int) (centerOfImage.x - (SIZE_ICON/2) + ((SIZE_ICON-textLength)/2));
            int y = centerOfImage.y + (SIZE_ICON)-5;
            return new Point(x,y);
        }
    void drawWheel(){
        graph.clearRect(0, 0, this.getWidth(), this.getHeight());
        drawIcons();
        drawEmptySectors();
        drawMarkup();
        drawSectors();
        drawControlButtons();
    }
        private void drawIcons(){
            for(int i=0;i<quantitySectors;i++){
                Sector sec = sectors.get(i);
                drawFullIcon(sec.title,sec.icon,sec.getStartAngle()+sec.getArcExtend()/2);               
            }
        }
        private void drawMarkup(){
            for(int i=1;i<=10;i++){
                strokeCircle(MIN_RADIUS*i);
            } 
        }
        private void drawEmptySectors(){
            double angle=0;
            for(int i=0;i<quantitySectors;i++){
                strokeArc(Color.web("#FFFFFF"),BIGGEST_RADIUS,angle,regularArcExtend);
                angle+=regularArcExtend;
            }
        }
        private void drawControlButtons(){
            drawControlButton("add.png",CENTER_BUT_ADD);
            drawControlButton("delete.png",CENTER_BUT_DELETE);
        }
        private void drawControlButton(String image, Point center){
            double x = center.x-SIZE_ICON/2;
            double y = center.y-SIZE_ICON/2;
            graph.drawImage(res.getImage(image), x, y, SIZE_ICON,SIZE_ICON);
        }
        private void drawSectors(){
            for(int i=0;i<quantitySectors;i++){
                Sector secI = sectors.get(i);
                int coincidences=countCoincidencesSelectedSectors(i);
                if(coincidences!=0){
                    drawBigSectors(i,coincidences);
                    i+=coincidences;
                }
                else{
                    drawSector(secI);
                }
            }
            /*for(int i=0;i<quantitySectors;i++){
                drawSector(sectors.get(i));
            }*/
        }
            private int countCoincidencesSelectedSectors(int i){
                int coincidences=0;
                for(int l=1;l<quantitySectors;l++){
                    int j=i+l;
                    if(j>=quantitySectors)j=j-quantitySectors;
                    if(sectors.get(i).getValue()==sectors.get(j).getValue()
                            &&sectors.get(i).getValue()!=0){
                        coincidences++;
                    }else break;
                }
                return coincidences;
            }
            /**
             * 
             * @param i - num of original sector
             * @param coincidences 
             */
            private void drawBigSectors(int i, int coincidences){
                double startAngel = sectors.get(i).getStartAngle();
                    double arcExtend=0; 
                    if(coincidences==7){
                        fillCircle(sectors.get(i).getColor(),sectors.get(i).getValue()*MIN_RADIUS);
                    }
                    else{
                        for(int j=0;j<coincidences+1;j++){
                            int ind = i+j;
                            if(ind>=8)ind-=8;
                            arcExtend+= sectors.get(ind).getArcExtend();
                        }
                        Sector additionalSector = new Sector(startAngel,arcExtend);
                        additionalSector.setValue(sectors.get(i).getValue());
                        drawSector(additionalSector);
                    }
            }
    
    void addListener(){
        setOnMouseClicked((MouseEvent e)->{
            listenerForBigWheel(e);
            listenerForAdd(e);
            listenerForDelete(e);
        });
    }
    void listenerForBigWheel(MouseEvent e){
        double difX=e.getX()-X0;
        double difY=e.getY()-Y0;
        double distance = Math.sqrt(Math.pow(difX,2) + Math.pow(difY,2));
        if(distance < BIGGEST_RADIUS ){
            int numSector = getNumSector(e);
            int grade = getGrade(e.getX(),e.getY());
            if(grade<=10){
                sectors.get(numSector).setValue(grade);
                setValueOfSectorInXml(sectors.get(numSector).title,grade);
                drawWheel();
            }
        }
    }
        int getNumSector(MouseEvent e){
            double angle = getAngleInDegrees(e.getX(),e.getY());
            int numSector = (int) (angle/regularArcExtend);
            return numSector;
        }
        int getGrade(double x,double y){
            double a=x-X0;
            double b=y-Y0;
            double distance = Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
            return (int) Math.ceil(distance/MIN_RADIUS);
        }
        double getAngleInDegrees(double x,double y){
            double angle;
             double a=x-X0;
             double b=y-Y0;
             double c = Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
             double sinA = b/c, cosA = a/c;
             if(sinA>0){
                 if(cosA>0) angle = -Math.asin(sinA);
                 else angle = -Math.acos(cosA);
             }
             else{
                 if(cosA>0) angle = -Math.asin(sinA);
                 else angle = +Math.acos(cosA);
             }
             angle = Math.toDegrees(angle);
             if(angle<0)return angle+360;
             else return angle;
        }
        private void setValueOfSectorInXml(String name, int value){
            String query = "[@name='"+name+"']";
            StringBuffer pathToAttr = new StringBuffer(XML_WAY_TO_CHARACTERISTICS + XML_CHARACTERISTICS[2]);
            pathToAttr.insert(pathToAttr.lastIndexOf("/"), query);
            XmlParsing parser = new XmlParsing();
            try {
                parser.setAttr(res.getXml(XML_FILE), pathToAttr.toString(), ""+value);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(LifeBalanceWheelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    void listenerForAdd(MouseEvent e){
            double difX=e.getX()-CENTER_BUT_ADD.x;
            double difY=e.getY()-CENTER_BUT_ADD.y;
            double distance = Math.sqrt(Math.pow(difX,2) + Math.pow(difY,2));
            if(distance<=SIZE_ICON){
                new AddingSector();
                
            }
    }    
    public void addNewSector(String name, String icon){
       XmlParsing parser = new XmlParsing();
       Map<String,String> attrs = new HashMap();
       attrs.put("name", name);
       attrs.put("icon",icon);
       attrs.put("value","0");
        try {
            parser.addNode(res.getXml(XML_FILE), "category", attrs);
        } catch (Exception ex) {
            Logger.getLogger(LifeBalanceWheelController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Прикольно, ошибка тамгде ее не должно быть");
        }
        init();
    }
    void listenerForDelete(MouseEvent e){
        double difX=e.getX()-CENTER_BUT_DELETE.x;
        double difY=e.getY()-CENTER_BUT_DELETE.y;
        double distance = Math.sqrt(Math.pow(difX,2) + Math.pow(difY,2));
        if(distance<=SIZE_ICON){
            new DeletingSector();
            init();
        }
    }
}
