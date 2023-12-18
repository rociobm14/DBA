/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicasantaclaus;

import jade.core.Agent;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import java.util.Random;





/**
 *
 * @author Rocío Barragán Moreno, Raúl Rodríguez Fernández, 
 * Alejandro García Pérez, José Manuel Navarro Cuartero
 */

public class SantaClaus extends Agent{
    
    private final double probabilidadConfiable;
    
    private final int mifila = 0;
    private final int micol  = 0;
    
    private final int CLAVE_SECRETA = 33;

    public SantaClaus() {
        this.probabilidadConfiable = 0.95;//0.75;
        
    }
    
    public int getCLAVE()
    {
        return this.CLAVE_SECRETA;
    }
    
    public boolean EsConfiable() {
        if(this.probabilidadConfiable < 0.0 || this.probabilidadConfiable > 1.0)
            throw new IllegalArgumentException("La probabilidad insertada es inválida");
        
        Random random = new Random();
        return random.nextDouble() < this.probabilidadConfiable;
    }
   
    
    @Override
    protected void setup() {   
        System.out.println("HOLA! Soy el agente de santaclaus");
        System.out.println("My local-name is " + getAID().getLocalName());
        System.out.println("My GUID is " + getAID().getName());
        System.out.println("My addresses are: ");
        Iterator  it = getAID().getAllAddresses();
        while ( it.hasNext()) {
            System.out.println("-" + it.next());
        }
        
        addBehaviour(new ComportamientoSanta(this));
    }
    
    
    @Override 
    public void takeDown(){
        System.out.println("\tFin santaclaus");
    } 

    public int getSantaFil() {
        return this.mifila;
    }

    public int getSantaCol() {
        return this.micol;
    }
    
    
    
}
