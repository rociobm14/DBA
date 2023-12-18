/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicasantaclaus;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.util.Arrays;

/**
 *
 * @author Rocío Barragán Moreno, Raúl Rodríguez Fernández, 
 * Alejandro García Pérez, José Manuel Navarro Cuartero
 */

public class ComportamientoBuscador extends CyclicBehaviour {
    
    private final Buscador agente;
    private final int[][] map;
    private final int[][] mapaOriginal;
    
    private final int OUT_OF_BOUNDS = 1000;
    private final int MURO = -1;
    private final int DEFECTO = 100;
    
    private int contadorRenosAux = 0;
    private boolean todos_renos_enviados = false;
    private boolean yendoaSanta = false;
    
    private int paso = 0;
    private int clave = 0;
    
    
    private final AID rudolfAID;
    private final AID santaAID;
    
    
    public ComportamientoBuscador( Buscador agente, int[][] map){
        this.agente = agente;
        
        this.map = map;
        this.mapaOriginal = copiarMapa(map);
        
        this.rudolfAID = new AID("Rudolf", AID.ISLOCALNAME);
        this.santaAID = new AID("SantaClaus", AID.ISLOCALNAME);
        
    }
    
    
    
    
    
    @Override
    public void action(){
        
        boolean respuesta_afirmativa;
        
        switch(paso) {
            
            // Hablar con santa claus y pedirle la clave, si somos dignos 
            case 0:     
                respuesta_afirmativa = SolicitudSantaClave();
                
                if(respuesta_afirmativa){
                   System.out.println(" Bien! Santa me considera digno ! ");
                   paso = 1;
                }else{
                   System.out.println("Santa Claus NO CONSIDERA DIGNO al buscador");
                   myAgent.doDelete();
                    
                }
                break;
                
                
            // Enviar a Rudolf solicitud para obtener la ubicación de un reno    
            case 1:

                // Si todos los renos han sido encontrados Rudolf nos lo dirá
                ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
                if (msg != null && "Ya se han enviado todos los renos".equals(msg.getContent())) {
                    System.out.println("\nBuscador: Confirmacion recibida. Todas las ubicaciones de los renos han sido enviadas.");
                    todos_renos_enviados = true;
                    paso = 3;
                }
                    

                if(!todos_renos_enviados){
                    // Preguntar a Rudolf
                    respuesta_afirmativa = SolicitudRudolfCoordReno();//coordenadas;

                    if(respuesta_afirmativa){
                        paso = 2; // ve a por los renos
                    }
                }
                break;
                
                
                
            // Ir a por el reno en cuestión
            case 2:
                boolean reno_atrapado = comenzarDestino();
                
                if(reno_atrapado){
                    this.contadorRenosAux++;
                    
                    System.out.println("Buscador: he atrapado un reno! llevo " + this.contadorRenosAux);
                    paso = 1;
  
                }
                
                break;
            
                
            // Atrapados, los renos, ir a llevarlos a santa
            case 3:
                
                if(!yendoaSanta){
                    System.out.println("\nBuscador: donde esta Santa?.....");
                    boolean recibido_coord_santa = SolicitudSantaUbicacion();
                    if(recibido_coord_santa){
                        System.out.println("Recibidas las coordenadas de Santa: " + this.agente.getFil_obj() + "," + this.agente.getCol_obj());
                        System.out.println("yendo hacia Santa...");
                        yendoaSanta = true;
                    }else
                        System.out.println("Santa no me da sus coordenadas....");
                    
                }else{
                    boolean llegado_a_santa = comenzarDestino();

                    if(llegado_a_santa){
                        preguntaTodoBienSanta();
                    }
                }
                break;
                
        }
        
    }
    
    
    private boolean SolicitudSantaClave()
    {
        boolean eres_digno = false;
        
        // Enviar solicitud a Santa Claus
        ACLMessage mensajeSolicitud = new ACLMessage(ACLMessage.REQUEST);
        mensajeSolicitud.addReceiver(santaAID);
        mensajeSolicitud.setContent("¿Soy digno?");
        myAgent.send(mensajeSolicitud);

        // Esperar y recibir la respuesta de Santa Claus
        ACLMessage respuesta = myAgent.blockingReceive(MessageTemplate.MatchSender(new AID("SantaClaus", AID.ISLOCALNAME)));
        
        
        if (respuesta != null && respuesta.getPerformative() == ACLMessage.INFORM) {
            String contenido = respuesta.getContent();
            if( contenido.equals("NO ERES DIGNO"))
                return false;
            
            try {
                this.clave = Integer.parseInt(respuesta.getContent()); // consigue la clave secreta
                System.out.println("Buscador: he conseguido la clave secreta: " + this.clave);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el string a int: " + e.getMessage());
            }
            eres_digno = true;
        }
        
        
        return eres_digno;
    }
    
    
    
    
    private boolean SolicitudSantaUbicacion()
    {
        // Enviar mensaje a Santa solicitando sus coordenadas
        ACLMessage mensajeSolicitud = new ACLMessage(ACLMessage.REQUEST);
        mensajeSolicitud.addReceiver(santaAID);
        mensajeSolicitud.setContent("¿Dónde estás, Santa?");
        myAgent.send(mensajeSolicitud);
        
        
        // Esperar y recibir la respuesta de Santa
        ACLMessage respuesta = myAgent.blockingReceive(MessageTemplate.MatchSender(new AID("SantaClaus", AID.ISLOCALNAME)));
        if (respuesta != null && respuesta.getPerformative() == ACLMessage.INFORM) {
            String[] partes = respuesta.getContent().split(",");
            try {
                int fila = Integer.parseInt(partes[0]);
                int columna = Integer.parseInt(partes[1]);
                this.agente.setFil_obj(fila);
                this.agente.setCol_obj(columna);
                return true;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
    
    private void preguntaTodoBienSanta(){
        ACLMessage mensajeLlegada = new ACLMessage(ACLMessage.INFORM);
        mensajeLlegada.addReceiver(santaAID);
        mensajeLlegada.setContent("Ya he llegado, ¿todo bien?");
        myAgent.send(mensajeLlegada);

        // recibir la respuesta de Santa Claus
        ACLMessage respuesta = myAgent.blockingReceive(MessageTemplate.MatchSender(new AID("SantaClaus", AID.ISLOCALNAME)));
        if (respuesta != null)
            System.out.println("Respuesta de Santa: " + respuesta.getContent()); // HO HO HO

        myAgent.doDelete();
    }
    
    private boolean SolicitudRudolfCoordReno()
    {
        
        
        // Enviar mensaje a Rudolf solicitando las coordenadas del reno
        ACLMessage mensajeSolicitud = new ACLMessage(ACLMessage.REQUEST);
        mensajeSolicitud.addReceiver(rudolfAID);
      
        mensajeSolicitud.setConversationId(String.valueOf(this.clave));
        
        mensajeSolicitud.setContent("Por favor coordenadas para un reno");
        myAgent.send(mensajeSolicitud);
        
         // Esperar y recibir la respuesta de Rudolf
        ACLMessage respuesta = myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        if (respuesta != null) {
            String[] partes = respuesta.getContent().split(",");
            try {
                int fila = Integer.parseInt(partes[0]);
                int columna = Integer.parseInt(partes[1]);
                this.agente.setFil_obj(fila);
                this.agente.setCol_obj(columna);
                return true;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
        
        
        
    }
    
    
    
    

    
    
    ////////////////////////////// MOVIMIENTO Y DIRECCION //////////////////////////////
    
    
    public boolean comenzarDestino()
    {
        if (agente.getFil_actual() != agente.getFil_obj() || agente.getCol_actual() != agente.getCol_obj()){
            see(agente.getMyVision(), agente.getFil_actual(), agente.getCol_actual(), map);
            int [] siguientePos = AlgoritmoDestino();
            Movimiento(siguientePos);
            return false;
            
        } else{ 
            imprimeMatriz(map);
            restablecerMapa();
            return true; // legado al objetivo
        }
        
        
    }
     private int[][] copiarMapa(int[][] mapaOriginal) {
        int[][] copia = new int[mapaOriginal.length][];
        for (int i = 0; i < mapaOriginal.length; i++) {
            copia[i] = mapaOriginal[i].clone();
        }
        return copia;
    }

    private void restablecerMapa() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // Solo copiar si la casilla actual no es un reno
                if (mapaOriginal[i][j] != 1) {
                    map[i][j] = mapaOriginal[i][j];
                }
            }
        }
    }

    
   
    
    
    // Se actualiza la vision del agente, que es una matriz 3x3
    public void see(int[][] vision, int filaActual, int colActual, int[][] map) {
        // Inicializar toda la visión a OUT_OF_BOUNDS o MURO
        for (int[] fila : vision) {
            Arrays.fill(fila, OUT_OF_BOUNDS);
        }

        // Actualizar solo las casillas no diagonales siempre y cuando
        // se encuentren dentro de los límites del mapa
        if (filaActual - 1 >= 0) // Arriba
            vision[0][1] = map[filaActual - 1][colActual];
        if (filaActual + 1 < map.length) // Abajo
            vision[2][1] = map[filaActual + 1][colActual];
        if (colActual - 1 >= 0) // Izquierda
            vision[1][0] = map[filaActual][colActual - 1];
        if (colActual + 1 < map[0].length) // Derecha
            vision[1][2] = map[filaActual][colActual + 1];
    }    
    
    public int[] AlgoritmoDestino() {
        //Inicializar matriz a valores por defecto para ir calculando
        //la distancia manhattan.
        int[][] mapVisionCosto = new int[3][3];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(mapVisionCosto[i], DEFECTO);
        }

        int menorValor = DEFECTO;
        int filaMenor = 1; // Iniciar en la posición central
        int columnaMenor = 1; // Iniciar en la posición central

        // Actualizamos "mapVisionCosto" con la distanciaManhattan
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != 1 || j != 1) { // Ignorar la posición actual del agente
                    int vision = agente.getMyVision()[i][j];
                    if (vision != MURO && vision != OUT_OF_BOUNDS) {
                        int fila = agente.getFil_actual() + i - 1;
                        int columna = agente.getCol_actual() + j - 1;
                        mapVisionCosto[i][j] = DistanciaManhattan(fila, columna) + map[i][j];
                    }
                }
            }
        }

        //imprimeEstado();

        // Buscar la posición con el menor valor
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int fila = agente.getFil_actual() + i - 1;
                int columna = agente.getCol_actual() + j - 1;

                // Verificar los límites del mapa
                if (fila >= 0 && fila < map.length && columna >= 0 && columna < map[0].length) {
                    int valorActual = mapVisionCosto[i][j] + map[fila][columna]; // Sumar el costo actual de la casilla
                    if (valorActual < menorValor) {
                        menorValor = valorActual;
                        filaMenor = i;
                        columnaMenor = j;
                    }
                }
            }
        }

        // Asegurarse de que las nuevas posiciones no están fuera de los límites
        int nuevaFila = agente.getFil_actual() + (filaMenor - 1);
        int nuevaColumna = agente.getCol_actual() + (columnaMenor - 1);
        nuevaFila = Math.max(0, Math.min(nuevaFila, map.length - 1));
        nuevaColumna = Math.max(0, Math.min(nuevaColumna, map[0].length - 1));

        return new int[]{nuevaFila, nuevaColumna};
}
    
    // Distancia absoluta (pitagoras) entre el objetivo y las coordinadas argumento
    public int DistanciaManhattan(int i, int j){
        return Math.abs(i - agente.getFil_obj()) + Math.abs(j - agente.getCol_obj()); 
    }
    
    public void Movimiento(int [] destino){
        agente.setFil_actual(destino[0]);
        agente.setCol_actual(destino[1]);
        
        // Incrementar el valor en el mapa para la casilla a la que se mueve
        if (destino[0] >= 0 && destino[0] < map.length && destino[1] >= 0 && destino[1] < map[0].length) {
            this.map[destino[0]][destino[1]] += 2;
        }      
        //System.out.println("Me muevo a: " + agente.getFil_actual() + " " + agente.getCol_actual() + "\n");
    }
     
     
    public void imprimeMatriz(int[][] matriz){
        
        for (int[] matriz1 : matriz) {
            for (int j = 0; j < matriz[0].length; j++) {
                if(matriz1[j] == 1)
                    System.out.print('R' + " ");
                else if (matriz1[j] == -1)
                    System.out.print('#' + " ");
                else
                    System.out.print(matriz1[j] + " ");
            }
            System.out.println();
        }
        
    }
    
    public void imprimeEstado(){
        System.out.println("Estoy en la posicion: " + agente.getFil_actual() + " " + agente.getCol_actual());
        System.out.println("Esto es lo que veo: ");
        imprimeMatriz(agente.getMyVision());
        System.out.println("Esta es la traza de mi movimiento: ");
        imprimeMatriz(map);
    }
   
    
}
