package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Arcs {
    private double show_x = 0.0;
    private double show_y = 0.0;
    private double show_x2 = 0.0;
    private double show_y2 = 0.0;
    public Color color;
    private int long_path=0;
    int from_vertice;
    int  to_vertice;
    private boolean bridge=false;
    public boolean isBridge() {
        return bridge;
    }

    public void setBridge(boolean bridge) {
        this.bridge = bridge;
    }
    public double getShow_x2() {
        return show_x2;
    }

    public void setShow_x2(double show_x2) {
        this.show_x2 = show_x2;
    }

    public double getShow_y2() {
        return show_y2;
    }

    public void setShow_y2(double show_y2) {
        this.show_y2 = show_y2;
    }

    public double getShow_x() {
        return show_x;
    }

    public void setShow_x(double show_x) {
        this.show_x = show_x;
    }

    public double getShow_y() {
        return show_y;
    }

    public void setShow_y(double show_y) {
        this.show_y = show_y;
    }



    public Color getColor() {
        return color;
    }

    public int getFrom_vertice() {
        return from_vertice;
    }

    public int getTo_vertice() {
        return to_vertice;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Arcs(double show_x, double show_y,double show_x2, double show_y2,int from_vertice, int to_vertice){
        this.show_x=show_x;
        this.show_y=show_y;
        this.show_x2=show_x2;
        this.show_y2=show_y2;
        this.from_vertice=from_vertice;
        this.to_vertice=to_vertice;
        this.color=Color.BLACK;


    }
    public void show(GraphicsContext gc){
        gc.setStroke(getColor());
        gc.setLineWidth(3);
        gc.strokeLine(getShow_x()+12.5, getShow_y()+12.5, getShow_x2()+12.5, getShow_y2()+12.5);
    }



}
