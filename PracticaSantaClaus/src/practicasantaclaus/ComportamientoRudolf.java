/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicasantaclaus;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Rocío Barragán Moreno, Raúl Rodríguez Fernández, 
 * Alejandro García Pérez, José Manuel Navarro Cuartero
 */
public class ComportamientoRudolf extends CyclicBehaviour{
        private Level map;
        int renosEnviados = 0;
        
        
        
        private final int TOTAL_RENOS;
        private boolean mensajeTodosRenosEnviado = false;
        
        private final int CONVERSATION_ID = 33;
        
        public ComportamientoRudolf(Level map){
            this.map = map;
            TOTAL_RENOS = map.getFilaRenos().size(); // habrá tantos renos como el tamaño de esta lista
        }
        
        @Override
        public void action(){
            if(renosEnviados < TOTAL_RENOS){
                enviaReno(renosEnviados);
                renosEnviados++;
            }else if (!mensajeTodosRenosEnviado){
                ACLMessage mensaje = new ACLMessage(ACLMessage.CONFIRM);
                mensaje.setContent("Ya se han enviado todos los renos");
                mensaje.setConversationId(String.valueOf(CONVERSATION_ID));
                mensaje.addReceiver(new AID("Buscador", AID.ISLOCALNAME));
                System.out.println("Rudolf action sent  this message: " + mensaje.getContent());
                this.myAgent.send(mensaje);
                mensajeTodosRenosEnviado = true;
                this.myAgent.doDelete();
            }
            
        }
        
        public void enviaReno(int n){
            ACLMessage msg = myAgent.blockingReceive();
            msg.setConversationId(String.valueOf(CONVERSATION_ID));
            
            if(msg.getConversationId().equals(String.valueOf(CONVERSATION_ID))) {

                System.out.println("Rudolf enviaReno accepted-received this message: " + msg.getContent());
          
                int fila = map.getFilaRenos().get(n);
                int columna = map.getColumnaRenos().get(n);

                String coordenadas = fila + "," + columna;
                ACLMessage replay = msg.createReply(ACLMessage.INFORM);

                replay.setConversationId(String.valueOf(CONVERSATION_ID));

                replay.setContent(coordenadas);

                this.myAgent.send(replay);

                System.out.println("Rudolf enviaReno " + n + " sent this message: " + replay.getContent());
            }else{
                ACLMessage replay = msg.createReply(ACLMessage.REJECT_PROPOSAL);
                System.out.println("Rudolf enviaReno: CLAVE INCORRECTA!!");
                this.myAgent.send(replay);
                this.myAgent.doDelete();
            }

        }

}
