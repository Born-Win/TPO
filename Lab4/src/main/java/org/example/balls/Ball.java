package org.example.balls;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

class Ball {
    private BallCanvas canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;
    private int priority = 0;
    private Color color;

    public Ball(BallCanvas c, int priority){
        this.canvas = c;
        this.priority = priority;

        switch(priority){
            case -1:
                this.color = Color.blue;
                break;
            case 1:
                this.color = Color.red;
                break;
            default:
                this.color = Color.darkGray;
                break;
        }

        this.x = 100;
        this.y = 100;
    }

    public void draw (Graphics2D g2){
        g2.setColor(this.color);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public void move(){
        x+=dx;
        y+=dy;
        if(x<0){
            x = 0;
            dx = -dx;
        }
        if(x+XSIZE>=this.canvas.getWidth()){
            x = this.canvas.getWidth()-XSIZE;
            dx = -dx;
        }
        if(y<0){
            y=0;
            dy = -dy;
        }
        if(y+YSIZE>=this.canvas.getHeight()){
            y = this.canvas.getHeight()-YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }

    public boolean intersectsWithHoles()
    {
        for (Hole hole: this.canvas.getHoles()) {
            if (hole.intersection(this.x, this.y)) {
                this.canvas.remove(this);
                return true;
            }
        }
        return false;
    }

    public int getPriority(){
        return this.priority;
    }
}