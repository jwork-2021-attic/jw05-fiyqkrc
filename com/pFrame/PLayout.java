package com.pFrame;

import java.awt.Color;
import java.util.ArrayList;

import com.pFrame.pwidget.PWidget;
import log.Log;

public class PLayout extends PWidget {
    protected int rownum;
    protected int columnnum;
    protected String rowStyle;
    protected String columnStyle;
    protected PWidget[][] containedWidgets;

    public void setRCNum(int x,int y){
        this.rownum=x;
        this.columnnum=y;
        this.containedWidgets = new PWidget[rownum][columnnum];
    }

    public void setRCNumStyle(int x,int y,String xstyle,String ystyle){
        this.rownum=x;
        this.rowStyle=xstyle;
        this.columnStyle=ystyle;
        this.columnnum=y;
        this.containedWidgets = new PWidget[rownum][columnnum];
    }

    public int getRowNum() {
        return this.rownum;
    }

    public int getColumnNum() {
        return this.columnnum;
    }

    protected String getRowStyle() {
        return this.rowStyle;
    }

    protected String getColumnStyle() {
        return this.columnStyle;
    }

    public PLayout(PWidget parent, Position p, int rownum, int columnnum) {
        super(parent, p);
        this.setLayout(this);
        this.rownum = rownum;
        this.columnnum = columnnum;
        this.rowStyle = "";
        this.columnStyle = "";
        containedWidgets = new PWidget[rownum][columnnum];
        for (int i = 0; i < rownum; i++)
            for (int j = 0; j < columnnum; j++)
                containedWidgets[i][j] = null;
        this.updateWidgetsLayout();
    }

    public PLayout(PWidget parent, Position p) {
        super(parent, p);
        this.setLayout(this);
        this.rownum = 1;
        this.columnnum = 1;
        this.rowStyle = "";
        this.columnStyle = "";
        containedWidgets = new PWidget[rownum][columnnum];
        for (int i = 0; i < rownum; i++)
            for (int j = 0; j < columnnum; j++)
                containedWidgets[i][j] = null;
        this.updateWidgetsLayout();
    }

    @Override
    public Pixel[][] displayOutput() {
        if (this.getWidgetHeight() <= 0 || this.getWidgetWidth() <= 0) {
            return null;
        } else {
            Pixel[][] pixels = new Pixel[this.getWidgetHeight()][this.getWidgetWidth()];
            for(int i=0;i<this.getWidgetHeight();i++){
                for(int j=0;j<this.getWidgetWidth();j++){
                    pixels[i][j]=new Pixel(Color.gray,(char) 0xb1);
                }
            }
            ArrayList<PWidget> childWidget=new ArrayList<>();
            for(int i=0;i<this.getRowNum();i++){
                for(int j=0;j<this.getColumnNum();j++){
                    if(this.containedWidgets[i][j]!=null)
                        childWidget.add(this.containedWidgets[i][j]);
                }
            }
            for (PWidget widget : childWidget) {
                Pixel[][] childPixels = widget.displayOutput();
                if (childPixels != null) {
                    Position pos = widget.getPosition();
                    int x_base = pos.getX();
                    int y_base = pos.getY();
                    int width = widget.getWidgetWidth();
                    int height = widget.getWidgetHeight();
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            pixels[x_base + i][y_base + j] = childPixels[i][j];
                        }
                    }
                }
            }
            return pixels;
        }
    }

    protected void updateWidgetsLayout() {
        int r[];
        int c[];
        if (this.getRowStyle() == "") {
            int[] row = new int[this.getRowNum()];
            for (int i = 0; i < this.getRowNum(); i++) {
                row[i] = this.getWidgetHeight() / this.getRowNum();
            }
            r=row;
        } else {
            String[] row = this.getRowStyle().split(",");
            int[] rowRes=new int[this.getRowNum()];
            if (row.length < this.getRowNum()) {
                Log.WarningLog(this, "rowstyle length is not equal with rownum,will use default style");
                this.setRowLayout("");;
                this.updateWidgetsLayout();
                return ;
            } else {
                try {
                    int numSec=0;
                    int numSum=0;
                    for (int i = 0; i < this.getRowNum(); i++) {
                        if (row[i].endsWith("x")) {
                            row[i]=row[i].substring(0, row[i].length()-1);
                            numSec+=Integer.valueOf(row[i]);
                        } else {
                            numSum+=Integer.valueOf(row[i]);
                            rowRes[i]=Integer.valueOf(row[i]);
                            row[i]="";
                        }
                    }
                    if(numSum>=this.getWidgetHeight()){
                        Log.ErrorLog(this, "fixed rows too big!");
                        this.setRowLayout("");
                        this.updateWidgetsLayout();
                        return;
                    }
                    for(int i=0;i<this.getRowNum();i++){
                        if(row[i]!=""){
                            rowRes[i]=Integer.valueOf(row[i])*(this.getWidgetHeight()-numSum)/numSec;
                        }
                    }
                } catch (Exception e) {
                    Log.ErrorLog(this, "read rowstyle failed");
                    this.setRowLayout("");
                    this.updateWidgetsLayout();
                    return;
                }
            }
            r=rowRes;
        }

        if (this.getColumnStyle() == "") {
            int[] row = new int[this.getColumnNum()];
            for (int i = 0; i < this.getColumnNum(); i++) {
                row[i] = this.getWidgetWidth() / this.getColumnNum();
            }
            c=row;
        } else {
            String[] row = this.getColumnStyle().split(",");
            int[] rowRes=new int[this.getColumnNum()];
            if (row.length < this.getColumnNum()) {
                Log.WarningLog(this, "rowstyle length is not equal with rownum,will use default style");
                this.setColumnLayout("");;
                this.updateWidgetsLayout();
                return ;
            } else {
                try {
                    int numSec=0;
                    int numSum=0;
                    for (int i = 0; i < this.getColumnNum(); i++) {
                        if (row[i].endsWith("x")) {
                            row[i]=row[i].substring(0, row[i].length()-1);
                            numSec+=Integer.valueOf(row[i]);
                        } else {
                            numSum+=Integer.valueOf(row[i]);
                            rowRes[i]=Integer.valueOf(row[i]);
                            row[i]="";
                        }
                    }
                    if(numSum>=this.getWidgetWidth()){
                        Log.ErrorLog(this, "fixed rows too big!");
                        this.setColumnLayout("");
                        this.updateWidgetsLayout();
                        return;
                    }
                    for(int i=0;i<this.getColumnNum();i++){
                        if(row[i]!=""){
                            rowRes[i]=Integer.valueOf(row[i])*(this.getWidgetWidth()-numSum)/numSec;
                        }
                    }
                } catch (Exception e) {
                    Log.ErrorLog(this, "read rowstyle failed");
                    this.setColumnLayout("");
                    this.updateWidgetsLayout();
                    return;
                }
            }
            c=rowRes;
        }
        for(int i=0;i<r.length;i++){
            for(int j=0;j<c.length;j++){
                if(this.containedWidgets[i][j]!=null){
                    this.containedWidgets[i][j].changeWidgetSize(c[j],r[i]);
                    int pos_x=0;
                    int pos_y=0;
                    for(int a=0;a<i;a++){
                        pos_x+=r[a];
                    }
                    for(int b=0;b<j;b++){
                        pos_y+=c[b];
                    }
                    this.containedWidgets[i][j].setPosition(new Position(pos_x, pos_y));
                }
            }
        }
    }

    public void setRowLayout(String args) {
        this.rowStyle = args;
        this.updateWidgetsLayout();
    }

    public void setColumnLayout(String args) {
        this.columnStyle = args;
        this.updateWidgetsLayout();
    }

    public void autoSetPosition(PWidget widget, Position p) {
        boolean operation=false;
        if (p == null) {
            for(int i=0;i<this.getRowNum();i++){
                for(int j=0;j<this.getColumnNum();j++){
                    if(this.containedWidgets[i][j]==null){
                        this.containedWidgets[i][j]=widget;
                        operation=true;
                        break;
                    }
                }
                if(operation==true)
                    break;
            }
        } else {
            if(p.getX()<=this.getRowNum() && p.getY()<=this.getColumnNum() && p.getX()>=1 && p.getY()>=1 && this.containedWidgets[p.getX()-1][p.getY()-1]==null){
                this.containedWidgets[p.getX()-1][p.getY()-1]=widget;
                operation=true;
            }
            else{
                Log.ErrorLog(this, "something else has been put on position ,add widget to layout failed");
            }
        }
        if(!operation){
            Log.ErrorLog(this, "add widget to layout failed");
        }
        else{
            this.updateWidgetsLayout();
        }
    }

    @Override
    public ArrayList<PWidget> getChildWidget() {
        ArrayList<PWidget> res=new ArrayList<>();
        res.add(this);
        for(int i=0;i<this.getRowNum();i++){
            for(int j=0;j<this.getColumnNum();j++){
                if(this.containedWidgets[i][j]!=null){
                    res.addAll(this.containedWidgets[i][j].getChildWidget());
                }
            }
        }
        return res;
    }

    @Override
    public ArrayList<PWidget> getWidgetsAt(Position p) {
        ArrayList<PWidget> res=new ArrayList<>();
        res.add(this);
        for(int i=0;i<this.getRowNum();i++){
            for(int j=0;j<this.getColumnNum();j++){
                if(this.containedWidgets[i][j]!=null){
                    PWidget w=this.containedWidgets[i][j];
                    if(PWidget.WidgetRange.inRange(w.getPosition(), w.getWidgetWidth(), w.getWidgetHeight(), p)==true)
                        res.addAll(this.containedWidgets[i][j].getWidgetsAt(new Position(p.getX()-w.getPosition().getX(), p.getY()-w.getPosition().getY())));
                }
            }
        }
        return res;
    }
}


