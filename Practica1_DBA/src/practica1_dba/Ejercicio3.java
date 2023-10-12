/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica1_dba;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

/**
 * @author Rocío Barragán Moreno
 * @description Crear un Agente básico que muestre un mensaje de forma indefinida, pero esperando 2
segundos entre mensaje y mensaje.
 */

public class Ejercicio3 extends Agent{
    
    private int counter = 0;
    
    @Override 
    public void setup(){
        System.out.println("\n-----------Agente que muestre mensaje de forma "
                + "indefinida, esperando 2 segundos entre mensaje-----------\n");
        addBehaviour(new MyBehaviour(this, 2000));
    }
    
    private class MyBehaviour extends TickerBehaviour{
        
        public MyBehaviour(Agent a, long period) {
            super(a, period);
        }
        
        @Override
        public void onTick(){
            if (counter < 5){
                System.out.println("El siguiente mensaje se imprimirá en 2 segundos");
                counter++;
            }
            
            else{
                stop();
                doDelete();
            }
        }
    }
    
    @Override 
    public void takeDown(){
        System.out.println("Terminating agent...");
    }
    
}
