package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Vertices {
    private double show_x = 0.0;

    public int getLong_path() {
        return long_path;
    }

    public int getMax_weight() {
        return max_weight;
    }

    public void setMax_weight(int max_weight) {
        this.max_weight = max_weight;
    }



    public void setLong_path(int long_path) {
        this.long_path = long_path;
    }

    private double show_y = 0.0;
    private int number_vertice;

    public boolean isArculation_point() {
        return arculation_point;
    }

    public void setArculation_point(boolean arculation_point) {
        this.arculation_point = arculation_point;
    }

    private int long_path=0;
    private int max_weight;
    private boolean arculation_point=false;
    public Color color;

    public int getNumber_vertice() {
        return number_vertice;
    }

    public void setNumber_vertice(int number_vertice) {
        this.number_vertice = number_vertice;
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

    public void setColor(Color color) {
        this.color = color;
    }

    public Vertices(GraphicsContext gc,double show_x, double show_y, int number_vertice){
        this.show_x=show_x;
        this.show_y=show_y;
        this.color=Color.BLACK;
        this.number_vertice=number_vertice;
        gc.fillText(Integer.toString(number_vertice),show_x+10,show_y-5);

    }
    public void show(GraphicsContext gc){
        gc.setFill(getColor());
        gc.fillOval(getShow_x(), getShow_y(), 25,25);
    }
}
