/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.Observer;



/**
 *
 * @author redxice
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class MyRunnable implements Observer,Runnable{
    KochFractal koch;
    String name;
    KochManager manager;
    public MyRunnable(KochManager manager,String name,int nxt){
        this.koch = new KochFractal();
        this.koch.setLevel(nxt);
        this.name = name;
        this.manager = manager;
        this.koch.addObserver(this);   
    }
    @Override
    public  void  run() {
        
           switch (this.name) {
            case "Bottom":
                this.koch.generateBottomEdge();
                this.manager.AddToCounter();
                break;
            case "Left":
                this.koch.generateLeftEdge();
                 this.manager.AddToCounter();
                break;
            case "Right":
                this.koch.generateRightEdge();
                 this.manager.AddToCounter();
                break;
            default:
                break;
        }
 
    }
   

    @Override
    public synchronized void update(java.util.Observable o, Object o1) {
        manager.addEdge((Edge)o1);
    }



  
    
}
