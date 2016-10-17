/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import javafx.scene.paint.Color;



/**
 *
 * @author Peter Boots
 */
public class BallRunnable implements Runnable {

    private Ball ball;
    private BallMonitor ballMonitor;
    public BallRunnable(Ball ball,BallMonitor ballMonitor) {
        this.ball = ball;
        this.ballMonitor= ballMonitor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if(ball.isEnteringCs()){
                   ball.setWaiting(true);
                    if(ball.getColor()==Color.RED){
                       ballMonitor.enterReader();
                    }
                    else if(ball.getColor()==Color.BLUE)
                    {
                        ballMonitor.enterWriter();
                    }
                    ball.setWaiting(false);
                    ball.setInCs(true);
                }
                else if(ball.isLeavingCs()){
                    ball.setWaiting(true);
                    if(ball.getColor()==Color.RED){
                        ballMonitor.exitReader();
                        
                    }
                    else if(ball.getColor()==Color.BLUE)
                    {
                        ballMonitor.exitWriter();
                    } 
                    ball.setWaiting(false);
                    ball.setInCs(false);
                }
                ball.move();  
                Thread.sleep(ball.getSpeed());
                
            } catch (InterruptedException ex) {
                
                if(ball.isInCs()&&ball.getColor()==Color.RED){
                    ballMonitor.exitReader();
                }
                else if (ball.isInCs()&&ball.getColor()==Color.BLUE)
                {
                ballMonitor.exitWriter();
                }
                else if(ball.IsWaiting()==true){
                    if (ball.getColor() == Color.RED)
                    {
                        ballMonitor.removeWaitingReader();
                    }
                    else if(ball.getColor() == Color.BLUE){
                        ballMonitor.removeWaitingWriter();
                    }
                }
                 Thread.currentThread().interrupt();
            }
        }
    }
}
