package org.example.balls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {

    private BallCanvas canvas;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;

    private static int hitScore = 0;
    private static JLabel hitScoreLabel;
    private static BallThreadJoin prevThread = null;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Balls&Holes");

        this.canvas = new BallCanvas();
//
//        canvas.add(new Hole(380, 10)); //N
//        canvas.add(new Hole(380, 270)); //S
//        canvas.add(new Hole(10, 10)); //NW
//        canvas.add(new Hole(10, 270)); //SW
//        canvas.add(new Hole(755, 10)); //NE
//        canvas.add(new Hole(755, 270)); //SE

        JPanel hitScorePanel =  new JPanel();
        hitScorePanel.setBackground(Color.lightGray.brighter());
        JLabel hitScoreLabel = new JLabel(String.valueOf(BounceFrame.hitScore));
        BounceFrame.hitScoreLabel = hitScoreLabel;
        hitScorePanel.add(this.hitScoreLabel);

        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(hitScorePanel, BorderLayout.NORTH);
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray.brighter());

        JButton buttonStart = new JButton("Add 1");
//        JButton buttonAddTen = new JButton("Add 10");
//        JButton buttonAddHundred = new JButton("Add 100");
//        JButton buttonAddThousand = new JButton("Add 1000");
//        JButton buttonAddRed = new JButton("Red");
//        JButton buttonAddBlue = new JButton("Blue");
//        JButton buttonTest = new JButton("Test");
        JButton buttonJoin = new JButton("Join Gray");
        JButton buttonJoinRed = new JButton("Join Red");
        JButton buttonJoinBlue = new JButton("Join Blue");


        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBall(0);
            }
        });

//        buttonAddTen.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                for(int i = 0; i<10; i++){
//                    createBall(0);
//                }
//            }
//        });
//
//        buttonAddHundred.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                for(int i = 0; i<100; i++){
//                    createBall(0);
//                }
//            }
//        });

//        buttonAddThousand.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                for(int i = 0; i<1000; i++){
//                    createBall(0);
//                }
//            }
//        });

//        buttonAddRed.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                createBall(1);
//            }
//        });
//
//        buttonAddBlue.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                for (int i = 0; i < 100; i++) {
//                    createBall(-1);
//                }
//            }
//        });
//
//        buttonTest.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                for (int i = 0; i < 1000; i++) {
//                    createBall(-1);
//                }
//                createBall(1);
//            }
//        });

        buttonJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 1; i++) {
                    createBallJoin(0);
                }
            }
        });
        buttonJoinRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 1; i++) {
                    createBallJoin(1);
                }
            }
        });
        buttonJoinBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 1; i++) {
                    createBallJoin(-1);
                }
            }
        });
        buttonPanel.add(buttonStart);
//        buttonPanel.add(buttonAddTen);
//        buttonPanel.add(buttonAddHundred);
//        buttonPanel.add(buttonAddThousand);

//        buttonPanel.add(buttonAddBlue);
//        buttonPanel.add(buttonAddRed);
//        buttonPanel.add(buttonTest);

        buttonPanel.add(buttonJoin);
        buttonPanel.add(buttonJoinRed);
        buttonPanel.add(buttonJoinBlue);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
    public static synchronized void hitScoreInc() {
        hitScore++;
        hitScoreLabel.setText(String.valueOf(hitScore));
    }

    public void createBall(int priority){
        Ball b = new Ball(canvas, priority);
        canvas.add(b);

        BallThread thread = new BallThread(b);
        thread.start();
        System.out.println("Created Ball = " + thread.getName());
    }

    public void createBallJoin(int priority){
        Ball b = new Ball(canvas, priority);
        canvas.add(b);

        BallThreadJoin thread;
        if(prevThread == null){
            thread = new BallThreadJoin(b, null);
        } else{
            thread = new BallThreadJoin(b, prevThread);
        }
        thread.start();
        prevThread = thread;
        System.out.println("Created Ball = " + thread.getName());
    }
}
