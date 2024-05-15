package org.example.balls;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Hole> holes = new ArrayList<>();

    public void add(Ball b){
        this.balls.add(b);
    }
    public void add(Hole h) {this.holes.add(h);}
    public ArrayList<Hole> getHoles() { return this.holes;}
    public ArrayList<Ball> getBalls() { return this.balls;}
    public synchronized void remove(Ball b){ this.balls.remove(b);}


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for(int i=0; i<holes.size();i++){
            Hole h = holes.get(i);
            h.draw(g2);
        }
        for(int i=0; i<balls.size();i++){
            Ball b = balls.get(i);
            b.draw(g2);
        }
    }
}
