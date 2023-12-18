/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package practicasantaclaus;

import jade.core.Agent;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 *
 * @author Rocío Barragán Moreno, Raúl Rodríguez Fernández, 
 * Alejandro García Pérez, José Manuel Navarro Cuartero
 */

public class Rudolf extends Agent{
    
    

    public Rudolf() {
    }
   
    
    @Override
    protected void setup() {   
        System.out.println("HOLA! Soy el agente Rudolf");
        System.out.println("My local-name is " + getAID().getLocalName());
        System.out.println("My GUID is " + getAID().getName());
        System.out.println("My addresses are: ");
        Iterator  it = getAID().getAllAddresses();
        while ( it.hasNext()) {
            System.out.println("-" + it.next());
        }
        
        ////////////////////  LECTURA DE MAPA ////////////////////
        Level level = null;
        try {
            level = new Level("MapaLaponia.txt");
        } catch (IOException ex) {
            Logger.getLogger(Rudolf.class.getName()).log(java.util.logging.Level.SEVERE, "Archivo no leido", ex);
        }

        addBehaviour(new ComportamientoRudolf(level));
    
    
    
    
    }
    
    
    @Override 
    public void takeDown(){
        System.out.println("\tFin rudolf");
    } 
    
    
    
}