package org.example.balls;

public class BallThreadJoin extends Thread{
    private Ball b;
    private BallThreadJoin prevThread;

    public BallThreadJoin(Ball ball, BallThreadJoin prevThread){
        this.b = ball;
        this.prevThread = prevThread;
        switch(ball.getPriority()){
            case -1:
                setPriority(Thread.MIN_PRIORITY);
                break;
            case 1:
                setPriority(Thread.MAX_PRIORITY);
                break;
            default:
                break;
        }
    }
    @Override
    public void run(){
        try {
            if (prevThread != null){
                prevThread.join();
            }

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
            System.out.println("Thread interrupted");
        }
    }
}

