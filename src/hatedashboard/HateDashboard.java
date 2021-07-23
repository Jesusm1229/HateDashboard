/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatedashboard;

import Views.Dashboard;
import Views.Intro;

/**
 *
 * @author jesus
 */
public class HateDashboard {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       // Spark sd = new Spark();
        
        Dashboard h = new Dashboard();
        
        
        
          // Haciendo visible el menu de opciones de la aplicacion.
        //Primero se muestra la intro, y pasado 5 seg se pasa al menú principal
        Intro intro = new Intro();
        intro.setVisible(true);
        
        new java.util.Timer().schedule( 
           new java.util.TimerTask() {            
                public void run() {        
                intro.setVisible(false);
                h.setVisible(true);
            }
        }, 
        7000) ; 
        
       
    }
    
}
