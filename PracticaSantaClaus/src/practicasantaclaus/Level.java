package practicasantaclaus;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; 

/**
 *
 * @author Rocío Barragán Moreno, Raúl Rodríguez Fernández, 
 * Alejandro García Pérez, José Manuel Navarro Cuartero
 */

public class Level {

    private int[][] map;
    private int size_filas;
    private int size_columnas;
    ArrayList<Integer> filaRenos = new ArrayList<>();
    ArrayList<Integer> columnaRenos = new ArrayList<>();


    public Level(String filename) throws FileNotFoundException, IOException {
        loadMap(filename);
    }
    
    private void loadMap(String filename) throws FileNotFoundException, IOException {
        // Read the file containing the map
        BufferedReader br = new BufferedReader(new FileReader(filename));

        // Read rows
        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) {
            throw new IOException("First line (number of rows) is missing or empty.");
        }
        size_filas = Integer.parseInt(line.trim());
        System.out.println("Filas: " + size_filas);

        // Read columns
        line = br.readLine();
        if (line == null || line.trim().isEmpty()) {
            throw new IOException("Second line (number of columns) is missing or empty.");
        }
        size_columnas = Integer.parseInt(line.trim());
        System.out.println("Columnas: " + size_columnas);

        // Fill the map with values from the file
        map = new int[size_filas][size_columnas];
        for (int fila = 0; fila < size_filas; fila++) {
            line = br.readLine();
            if (line == null || line.trim().isEmpty()) {
                throw new IOException("Unexpected end of file or empty line at row " + (fila + 1));
            }
            String[] numeros = line.trim().split("\\s+"); // Handles tabs and spaces
            if (numeros.length != size_columnas) {
                throw new IOException("Incorrect number of columns at line: " + (fila + 3));
            }
            for (int columna = 0; columna < size_columnas; columna++) {
                try {
                    map[fila][columna] = Integer.parseInt(numeros[columna]);
                    if (map[fila][columna] == 1) { //guarda las coordenadas de los renos
                        filaRenos.add(fila);
                        columnaRenos.add(columna);
                    }
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid number format at row " + (fila + 1) + ", column " + (columna + 1), e);
                }
            }
        }
        br.close(); //close the BufferedReader
    }


   
    public int[][] getMap(){
        return map;
    }
    
    public int getFilas(){
        return size_filas;
    }
    
    public int getColumnas(){
        return size_columnas;
    }

    public ArrayList<Integer> getFilaRenos() {
        return filaRenos;
    }

    public ArrayList<Integer> getColumnaRenos() {
        return columnaRenos;
    }
 
    
}

   
    