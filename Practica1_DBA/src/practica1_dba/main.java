/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica1_dba;

import jade.core.Runtime;
import jade.core.ProfileImpl;
import jade.core.Profile;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
/**
 *
 * @author rorris
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        jade.core.Runtime rt = jade.core.Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost"); // Cambia a tu dirección de host si es diferente
        p.setParameter(Profile.CONTAINER_NAME, "Contenedor1"); // Puerto predeterminado de JADE
        
        ContainerController cc = rt.createAgentContainer(p);
        
        try {
            AgentController ac = cc.createNewAgent("rociobarragan", Ejercicio4.class.getCanonicalName(), null);
            ac.start();
        } 
        
        catch (Exception ex) {
            ex.printStackTrace();
        }
 
        
      }
      
}
