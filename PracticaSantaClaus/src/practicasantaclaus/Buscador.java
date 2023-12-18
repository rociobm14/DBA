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

public class Buscador extends Agent{
    
    //Posición inicial del agente
    private final int fil_ini;
    private final int col_ini;
    
    //Posición actual del agente
    private int fil_actual;
    private int col_actual;
    
    //Posición objetivo
    private int fil_obj;
    private int col_obj;
    
    //Visión del agente
    private final int[][] myVision;
    
    //Mapa a recorrer
    private final String rutaMapa;
    

    public Buscador() {
        this.myVision = new int [3][3];
        this.fil_ini = 9;
        this.col_ini = 9;
        //La posición actual coincidirá con la inicial al instanciar el agente
        this.fil_actual = fil_ini;
        this.col_actual = col_ini;
        this.fil_obj = 0;
        this.col_obj = 0;
        
        this.rutaMapa = "MapaLaponia.txt";
    }
   
    
    @Override
    protected void setup() {   
        try {
            System.out.println("HOLA! Soy el agente Buscador");
            System.out.println("My local-name is " + getAID().getLocalName());
            System.out.println("My GUID is " + getAID().getName());
            System.out.println("My addresses are: ");
         
            Iterator  it = getAID().getAllAddresses();
            while ( it.hasNext()) {
                System.out.println("-" + it.next());
            }
            
            ////////////////////  LECTURA DE MAPA ////////////////////
            Level level = new Level(this.rutaMapa);
  
            int[][] map = level.getMap();
            
            // Imprimir el mapa
            imprimeMapa(map,level.getFilas(),level.getColumnas());
            
            // Se añade el comportamiento al agente(cíclico)
            addBehaviour(new ComportamientoBuscador(this, map));      
  
        } catch (IOException ex) {
            Logger.getLogger(SantaClaus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }  
    }
    
    private void imprimeMapa(int[][] map, int fil, int col) {
        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getFil_ini() {
        return fil_ini;
    }

    public int getCol_ini() {
        return col_ini;
    }

    public int getFil_actual() {
        return fil_actual;
    }

    public int getCol_actual() {
        return col_actual;
    }

    public int getFil_obj() {
        return fil_obj;
    }

    public int getCol_obj() {
        return col_obj;
    }
    
    public void setFil_obj(int fil_obj) {
        this.fil_obj = fil_obj;
    }

    public void setCol_obj(int col_obj) {
        this.col_obj = col_obj;
    }

    public int[][] getMyVision() {
        return myVision;
    }

    public void setFil_actual(int fil_actual) {
        this.fil_actual = fil_actual;
    }

    public void setCol_actual(int col_actual) {
        this.col_actual = col_actual;
    }
    
}