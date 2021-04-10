/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.model;
import com.steadystate.css.parser.CSSOMParser;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSStyleDeclaration;
import java.io.*;
import javafx.scene.paint.Color;
import main.java.model.Resources;


public class CssParsingForJava{
    private Color[] colorsForSectors = null;
    final String cssfile = "lifeBalanceWheel.css";
    private String cssFile;
    public CssParsingForJava(String cssFile) {
        this.cssFile = cssFile;
    }
    public void setCssFile(String cssFile){
        this.cssFile = cssFile;
    }

    public CSSStyleSheet parse(){
       CSSStyleSheet stylesheet=null;
        try{
           Resources res = new Resources();
           InputStream stream = res.getCssAsStream(cssfile);
           InputSource source = new InputSource(new InputStreamReader(stream));
           CSSOMParser parser = new CSSOMParser();      
           stylesheet = parser.parseStyleSheet(source, null, null);
        }
        catch (IOException ioe){
           System.err.println ("IO Error: " + ioe);
        }
        catch (Exception e){
           System.err.println ("Error: " + e);
        }
        return stylesheet;
    }
    
    public Color[] getColorsForSectors(){
        if(colorsForSectors==null){
            colorsForSectors = new Color[10];

            CSSStyleSheet stylesheet = parse();
            CSSRuleList ruleList = stylesheet.getCssRules();
            int k=0;
            for (int i = 0; i < ruleList.getLength(); i++){
                CSSStyleRule styleRule =(CSSStyleRule) ruleList.item(i);
                if(styleRule.getSelectorText().startsWith(".value")){
                    CSSStyleDeclaration styleDeclaration = styleRule.getStyle();
                    //There is only one property for value
                    String property = styleDeclaration.item(0);
                    String color = styleDeclaration.getPropertyCSSValue(property).getCssText();
                    colorsForSectors[k]= Color.valueOf(color);
                    k++;
                }
            }
        }
        return colorsForSectors;
    }
    
    

}