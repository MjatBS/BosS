package main.java.controllers;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import java.util.Calendar;
import javafx.geometry.Insets;
import main.java.service.BosS;
import main.java.model.PersonalInformation;

/*
 * этот жезненный календарь настроен на 70 лет( это максимум)
 * 
 */
public class LifeCalendar extends GridPane{
    final String COLOR_FOR_FILLED = "#d43521";
    final String COLOR_FOR_UNFILLED = "#ade2c8";

    final int PREF_SIZE_CELL = 10;
    final int FONT_SIZE = 10;
    final int DISTANCE_BETWEEN_SQUARES = 4;
    final int LEFT_PADDING = 40;
    
    final int QUAN_COLUMNS_WITH_SQUARE = 24;
    final int QUAN_ROWS = 35;
    final int MAX_YEAR = 70;
    
    
    public LifeCalendar(){
        createView();
    }
    private void createView(){
        //grid = new GridPane();
        initSize();
        this.setPadding(new Insets(20,0,0,LEFT_PADDING));
        createColumns();
        createRows();
        fillFirstColumnWithYears();
        fillSquares();
        
        setHgap(DISTANCE_BETWEEN_SQUARES);
        setVgap(DISTANCE_BETWEEN_SQUARES);
        //setGridLinesVisible(true);
    }
        private void initSize(){
            setPrefSize(500,400);
        }
        private void createColumns(){
            ObservableList<ColumnConstraints> columns = getColumnConstraints();
            columns.add(new ColumnConstraints(36, 36, 36,Priority.SOMETIMES, HPos.LEFT, true));//Установка значений величины первой колонки, где прописаны года
            for(int i=0;i<QUAN_COLUMNS_WITH_SQUARE;i++){
                /*columns.add(new ColumnConstraints(MIN_SIZE_CELL,
                                                 (MIN_SIZE_CELL+MAX_SIZE_CELL)/2,
                                                  MAX_SIZE_CELL));*/
                columns.add(new ColumnConstraints(PREF_SIZE_CELL));
            }
        }
        private void createRows(){
            ObservableList<RowConstraints> rows = getRowConstraints();
            for(int i=0;i<QUAN_ROWS;i++){
                /*rows.add(new RowConstraints(MIN_SIZE_CELL,
                                           (MIN_SIZE_CELL+MAX_SIZE_CELL)/2,
                                            MAX_SIZE_CELL));*/
                rows.add(new RowConstraints(PREF_SIZE_CELL));
            }
        }
    private void fillFirstColumnWithYears(){
        for(int i=0;i<35;i++){
            Label textYear;
            int year = (i)*2;
            if(year %10 ==2 || year %10 ==4){
                if(year > 14 || year <12 ) textYear = new Label(""+year+" года");
                else textYear = new Label(""+year+" лет");
            }else textYear = new Label(""+year+" лет");
            if(year<10){
                String textWithLeftPadding = "  " + textYear.getText();
                textYear.setText(textWithLeftPadding);
            }
            textYear.setFont(Font.font(FONT_SIZE));
            add(textYear, 0, i);
        }
    }
    private void fillSquares(){
        int quanFilledSquares = getQuantityNeededToFillSquares();
        int k=0;
        boolean maxFilled =false;
        for(int i=0;i<QUAN_ROWS;i++){
            for(int j=1;j<QUAN_COLUMNS_WITH_SQUARE+1;j++){
                Label lab = new Label("");
                lab.setMinSize(PREF_SIZE_CELL,PREF_SIZE_CELL);
                if(!maxFilled)lab.setStyle("-fx-background-color: "+COLOR_FOR_FILLED+";");
                else lab.setStyle("-fx-background-color: "+COLOR_FOR_UNFILLED+";");
                add(lab, j, i);
                k++;
                if(k==quanFilledSquares)maxFilled = true;
            }
        }
    }
    
    
    /**
     * Либо здесь будет находится либо где-то во Model
     */
    int getQuantityNeededToFillSquares(){
        PersonalInformation userData = new PersonalInformation();
        int quanSquares;
        Calendar calendar = Calendar.getInstance();
        quanSquares = (calendar.get(Calendar.YEAR)-userData.getYearOfBirthday())*12+
                       + (calendar.get(Calendar.MONTH)+1)-userData.getMonthOfBirthday();
        return quanSquares;
    }
}
