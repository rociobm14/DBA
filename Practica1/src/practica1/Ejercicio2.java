/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica1;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/**
 * @author Rocío Barragán Moreno
 * @description Crear un Agente usando comportamientos que muestre un mensaje por consola una única
vez.
 */

public class Ejercicio2 extends Agent{
    
    @Override
    public void setup(){
        System.out.println("\n-----------Agente que use comportamientos con "
                + "mensaje una única vez-----------\n");
        addBehaviour(new MyBehaviour());
    }
    
    private class MyBehaviour extends OneShotBehaviour {
        
        @Override
        public void action() {
            System.out.println("Este mensaje solo se mostrará una vez con"
                        + " el comportamiento One Shot");
            doDelete();
        }
    }
    
    @Override 
    public void takeDown(){
        System.out.println("Terminating agent...");
    }
    
}
