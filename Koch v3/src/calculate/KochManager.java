/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 *
 * @author redxice
 */
public class KochManager {
     private JSF31KochFractalFX application;
     private KochFractal koch ;
     private  List<Edge> edges;
     private int counter = 0;
    TimeStamp ts2;
    TimeStamp ts;
public KochManager(JSF31KochFractalFX application)
{
this.application = application;
edges = new ArrayList<Edge>(); 
koch = new KochFractal();
}

public synchronized void AddToCounter(){
    //elke keer dat een thread klaar is met zijn generate edge methode komt er +1 in de counter
    counter++;
   //als alle 3 threads klaar zijn wordt de Calc timestamp beeindigd. Threads worden terminated wanneer ze klaar zijn met run methode.
    while(counter==3){
    ts.setEnd("Done");
   //de requestDrawEdges roept de KochManger.drawEdges aan in de Javafx application thread
    application.requestDrawEdges();
//counter wordt weer gereset voor volgende keer in Changelvl
   counter =0;
    }
}
// is aangemaakt als synchronized omdat Meerdere threads hier gebruik van maken. 
//Dankzij synchronized kan dus maarr 1 thread per keer iets adden in de edges list.
public synchronized void addEdge(Edge edge)
{
   this.edges.add(edge);
}

 
 public  void drawEdges() {
   application.clearKochPanel();
   application.setTextCalc(ts.toString());
        ts2 = new TimeStamp();
        ts2.setBegin("started drawing");
     for (Edge e : edges) {
         application.drawEdge(e);
     }
     ts2.setEnd("Drawing done");
     application.setTextDraw(ts2.toString());
}
   public void changeLevel(int nxt) {
  edges.clear();
   koch.setLevel(nxt);
   // maak meerdere threads aan om de generate edge methodes tegelijker tijd uit te voeren.
   MyRunnable run1 = new MyRunnable(this,"Bottom",nxt);
   Thread Bottom = new Thread(run1);
   MyRunnable run2 = new MyRunnable(this,"Left",nxt);
   Thread Left = new Thread(run2);
   MyRunnable run3 = new MyRunnable(this,"Right",nxt);
   Thread Right = new Thread(run3);
  //ts houd de tijd bij van de berekening
   ts = new TimeStamp();
   ts.setBegin("Begin");
   //Threads starten hier dus worden de run methodes van de MyRunnable uitgevoerd
   Left.start();
   Bottom.start();
   Right.start();
  
   String nr = String.valueOf(koch.getNrOfEdges());
   application.setTextNrEdges(nr);
}

   

    
}


   

    

