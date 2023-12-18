/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicasantaclaus;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Rocío Barragán Moreno, Raúl Rodríguez Fernández, 
 * Alejandro García Pérez, José Manuel Navarro Cuartero
 */
public class ComportamientoSanta extends CyclicBehaviour{
    private final SantaClaus agente;
    
    public ComportamientoSanta(SantaClaus santa) {
        this.agente = santa;
    }
    
    @Override
    public void action() {
        // Atento a recibir mensajes
        ACLMessage mensajeRecibido = myAgent.blockingReceive();
        
        if (mensajeRecibido != null) {
            String contenido = mensajeRecibido.getContent();
           

            // Evaluar el contenido del mensaje y responder adecuadamente
            if ("¿Soy digno?".equals(contenido)) {
                ACLMessage respuesta = mensajeRecibido.createReply();
                respuesta.setPerformative(ACLMessage.INFORM);
                boolean digno = this.agente.EsConfiable();
                if(digno)
                    respuesta.setContent(String.valueOf(this.agente.getCLAVE()));
                else
                    respuesta.setContent(String.valueOf("NO ERES DIGNO"));
                
                myAgent.send(respuesta);
                
            } else if ("¿Dónde estás, Santa?".equals(contenido)) {
                ACLMessage respuesta = mensajeRecibido.createReply();
                respuesta.setPerformative(ACLMessage.INFORM);

                int fila = this.agente.getSantaFil();
                int columna = this.agente.getSantaCol();

                String coordenadas = fila + "," + columna;

                respuesta.setContent(coordenadas);
                myAgent.send(respuesta);

                
            } else if ("Ya he llegado, ¿todo bien?".equals(contenido)) {
                ACLMessage respuesta = mensajeRecibido.createReply();
                respuesta.setPerformative(ACLMessage.INFORM);
                respuesta.setContent("HO! HO! HO!");
                myAgent.send(respuesta);
                
            } else {
                // Respuesta genérica o manejo de mensajes no esperados
                ACLMessage respuesta = mensajeRecibido.createReply();
                respuesta.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                respuesta.setContent("Mensaje no comprendido o mala pregunta");
                
                myAgent.send(respuesta);
            }
            
            
            
            
        } else {
            block();
        }
    }
    
    
    
    
    // RESPONDER A TRES MENSAJES
}
