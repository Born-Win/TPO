package org.example.balls;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball){
        b = ball;
        switch(ball.getPriority()){
            case -1:
                setPriority(Thread.MIN_PRIORITY);
                break;
            case 1:
                setPriority(Thread.MAX_PRIORITY);
                break;
            default:
                setPriority(Thread.NORM_PRIORITY);
                break;
        }
    }
    @Override
    public void run(){
        try {
            while (true){
                if (b.intersectsWithHoles()) {
                    BounceFrame.hitScoreInc();
                    System.out.println("Ball Hit Hole = " + Thread.currentThread().getName());
                    break;
                }
                b.move();
                System.out.println("Moving Ball = " + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch(InterruptedException ex){

        }
    }
}