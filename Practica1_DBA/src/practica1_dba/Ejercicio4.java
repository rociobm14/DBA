/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica1_dba;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Scanner;

/**
 *
 * @author rorris
 */
public class Ejercicio4 extends Agent {
    
    @Override
    public void setup(){
         System.out.println("\nAgente que muestre solicite un número de elementos, los sume, y haga la media \n");
    }
    
    private class AskHowManyNumbers extends OneShotBehaviour{
        
        @Override
        public void action(){
            Scanner elements = new Scanner(System.in);
            System.out.println("Introduzca el número de elementos que se van a sumar");
            int numbers = elements.nextInt();
            getDataStore().put("TotalNumbers", numbers);
        }
        
    }
    
    private class GetNumbersToAdd extends OneShotBehaviour{
        
        @Override
        public void action(){
            Scanner getNumber = new Scanner(System.in);
            System.out.println("Introduzca los números que se van a sumar");
            
            int totalNumbers = (int)getDataStore().get("TotalNumbers");
            
            int[] numeros = new int[totalNumbers];
            
            for (int i=0; i<totalNumbers; i++){
                numeros[i] = getNumber.nextInt();
            }
            
            getDataStore().put("numbers", numeros);
        }
    }
    
   
    
}
