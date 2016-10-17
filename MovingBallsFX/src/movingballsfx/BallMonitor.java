/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author redxice
 */
public class BallMonitor
{
    private final  Lock lock =new ReentrantLock(); ;
    private final  Condition okToRead= lock.newCondition(); ;
    private final  Condition okToWrite = lock.newCondition(); ;
    private int writersActive=0;
    private int readersActive=0;
    private int readersWaiting=0;
    private int writersWaiting=0;
    
    public BallMonitor(){
   
    }
    public void removeWaitingWriter(){
        writersWaiting --;
    }
    public void removeWaitingReader(){
        readersWaiting --;
    }
    public void enterReader() throws InterruptedException{
        lock.lock();
        try{
            while(writersActive!=0){
            readersWaiting++;
            okToRead.await();
            readersWaiting--;
            }
             readersActive++;
         
        }
        finally{
            lock.unlock();
        
        }
    }
    public void exitReader(){
        lock.lock();
        try{
            readersActive--;
            if(readersActive == 0)
            { 
                okToWrite.signal();
            }
        }
        finally{
        lock.unlock();
        
    }
    }
    public void enterWriter() throws InterruptedException{
        lock.lock();
        try{
            while (writersActive>0|| readersActive>0){
                writersWaiting++;
                okToWrite.await();
                writersWaiting--;
            }
            writersActive++;
        }
        finally{
            lock.unlock();
        }
  }
    public void exitWriter(){
        lock.lock();
        try{
            
            writersActive--;
            System.out.println("writersWaiting:"+String.valueOf(writersWaiting));
            if(readersWaiting>0 && writersWaiting == 0){
                okToRead.signalAll();
            }
            else {
                okToWrite.signal(); 
            }
        }
        finally{
            lock.unlock();
        }
    }
    
}
