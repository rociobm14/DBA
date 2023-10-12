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
 *
 * @author rorris
 */
public class Ejercicio4 extends Agent {
    
    private int numberOfElements;
    private double [] numbers;
    
    @Override
    public void setup(){
        
        addBehaviour(new AskHowManyNumbers());
         addBehaviour(new GetNumbersToAdd());
         addBehaviour(new AddNumbersandAverage());

         //addBehaviour(new AddNumbersandAverage());
    }
    
    private class AskHowManyNumbers extends OneShotBehaviour{
        
        @Override
        public void action(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduzca el número de elementos a sumar:");
            numberOfElements = scanner.nextInt();
            numbers = new double[numberOfElements];
        }
        
    }
    
    private class GetNumbersToAdd extends OneShotBehaviour{
        
        @Override
        public void action(){
            System.out.println("Introduzca los números que se van a sumar");
            Scanner scanner = new Scanner(System.in);
            for (int i=0; i<numberOfElements; i++){
                numbers[i] = scanner.nextDouble();
            }
        }
    }
    

    private class AddNumbersandAverage extends OneShotBehaviour{
        
        @Override 
        public void action(){
            double suma = 0;
            for (int i=0; i<numberOfElements; i++)
                suma += numbers[i];
            
            System.out.println("La suma de los numeros es " + suma);
            
            double average = suma / numberOfElements;
            
            System.out.println("La media de los numeros es " + average);
            
           
        }
        
    }
   
}
