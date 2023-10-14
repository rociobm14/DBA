/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica1;

import jade.core.Agent;

/**
 * @author Rocío Barragán Moreno
 * @description Crear un Agente básico que muestre un mensaje por consola.
 */

public class Ejercicio1 extends Agent {
    
    @Override
    public void setup(){
        System.out.println("\n-----------Agente que muestre un mensaje por consola"
                + "-------------\n");
        System.out.println("Hola, mi nombre es Rocío Barragán Moreno, y estoy"
                + " cursando la asignatura de DBA");
        
        doDelete();
    }
    
    @Override 
    public void takeDown(){
        System.out.println("Terminating agent...");
    }
    
}
