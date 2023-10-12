/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica1_dba;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import java.util.Scanner;

/**
 * @author Rocío Barragán Moreno
 * @description Crear un Agente que solicite el número de elementos numéricos a 
 * leer, los sume, y calcule su media. Se deberá implementar utilizando una 
 * secuencia de comportamientos.
 */

public class Ejercicio4 extends Agent {
    
    private int numberOfElements;
    private double [] numbers;
    
    @Override
    public void setup(){
        System.out.println("\n-----------Agente que obtenga x números, los "
                + "sume y relice su media aritmética -----------\n");
        addBehaviour(new AskHowManyNumbers());
        addBehaviour(new GetNumbersToAdd());
        addBehaviour(new AddNumbersandAverage());
        
    }
    
    private class AskHowManyNumbers extends OneShotBehaviour{
        
        @Override
        public void action(){
            Scanner elements = new Scanner(System.in);
            System.out.println("Introduzca el número de elementos a sumar:");
            numberOfElements = elements.nextInt();
            numbers = new double[numberOfElements];
        }
    }
    
    private class GetNumbersToAdd extends OneShotBehaviour{
        
        @Override
        public void action(){
            System.out.println("\nIntroduzca los números que se van a sumar");
            Scanner number = new Scanner(System.in);
            for (int i=0; i<numberOfElements; i++){
                numbers[i] = number.nextDouble();
            }
        }
    }
    
    private class AddNumbersandAverage extends OneShotBehaviour{
        
        @Override 
        public void action(){
            double suma = 0;
            for (int i=0; i<numberOfElements; i++)
                suma += numbers[i];
            
            System.out.println("\nLa suma de los numeros es " + suma);
            
            double average = suma / numberOfElements;
            
            System.out.println("\nLa media de los numeros es " + average);
            doDelete();
        }
    }
    
    @Override 
    public void takeDown(){
        System.out.println("Terminating agent...");
    }
   
}
