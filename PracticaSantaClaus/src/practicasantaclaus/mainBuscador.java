/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practicasantaclaus;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.io.IOException;

/**
 *
 * @author Rocío Barragán Moreno, Raúl Rodríguez Fernández, 
 * Alejandro García Pérez, José Manuel Navarro Cuartero
 */
public class mainBuscador {

    public static void main(String[] args) throws IOException {
        System.out.println("Empezando mainBuscador Practica Santa Claus ");
        jade.core.Runtime rt = jade.core.Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost"); // Cambia a tu dirección de host si es diferente
        p.setParameter(Profile.CONTAINER_NAME, "Contenedor1"); // Puerto predeterminado de JADE

        ContainerController cc = rt.createAgentContainer(p);

        try {
            String nombreAgente3 = "Buscador";
            String claseAgente3 = "practicasantaclaus.Buscador";
            AgentController agente3 = cc.createNewAgent(nombreAgente3, claseAgente3, null);
            agente3.start();
        } catch (StaleProxyException ex) {
        }
    }
}
