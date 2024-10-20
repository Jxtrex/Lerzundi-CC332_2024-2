
/**
 * Este archivo sirve para:
 * - Generar un archivo con números aleatorios
 * - Búsqueda lineal de una @KEY en ese archivo
 * - Medir el tiempo necesario para realizar esas dos cosas
 */
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BusquedaSecuencial {
    private static String FILENAME = "DATOS.TXT";
    private static int N = 1000000;
    private static String KEY = "01234567890";
    private static String CADENA;
    private static int BLOCK = 11;
    private static byte[] RECORD = new byte[BLOCK];

    /**
     * Itera sobre los @BLOCK bytes de @RECORD y los agrega a @CAD como caracter
     * 
     * @return CAD
     */
    // ------------------------------------------------
    private static String GetString() {
        String CAD;
        CAD = "";
        for (int i = 0; i < BLOCK; i++) {
            CAD = CAD + (char) (RECORD[i]);
        }
        return CAD;
    }

    // ------------------------------------------------
    /*
     * private static void PrintRecord() {
     * CADENA = "";
     * for(int i=0;i<=10;i++) {
     * CADENA = CADENA + (char)(RECORD[i]);
     * }
     * System.out.println(CADENA);
     * }
     */
    /**
     * Búsqueda lineal de @KEY en @FILENAME
     */
    // ------------------------------------------------
    private static void ProcesoSerial() {
        long n, P, T, Time1, Time2;
        int i;
        try {
            // Abre el archivo @FILENAME para leer ("r")
            RandomAccessFile RAF = new RandomAccessFile(FILENAME, "r");
            Time1 = System.currentTimeMillis();
            T = RAF.length();
            // n es la cantidad de bloques de tamaño @BLOCK
            n = T / BLOCK;
            P = -1;
            i = 0;
            while ((i <= n - 1) && (P == -1)) {
                // Ubica el offset en i*@BLOCK
                RAF.seek(i * BLOCK);
                // Lee el bloque en @RECORD
                RAF.read(RECORD);
                // Transforma el bloque en String y compara con @KEY
                CADENA = GetString();
                if (CADENA.equals(KEY) == true) {
                    P = i;
                }
                i++;
            }
            RAF.close();
            if (P >= 0) {
                System.out.println("Elemento " + KEY + " Existente en la Posicion " + P);
            } else {
                System.out.println("Elemento " + KEY + " No Existe");
            }
            Time2 = System.currentTimeMillis();
            System.out.printf("Tiempo de Procesamiento Serial: %d%n", (Time2 - Time1));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Lee todo el archivo @FILENAME.txt y lo retorna como String
     * 
     * @return
     */
    public static String ReadFile() {
        String LINE = "", CADENA = "";
        try {
            File FILE = new File(FILENAME);
            BufferedReader BR = new BufferedReader(new FileReader(FILE));
            while ((LINE = BR.readLine()) != null) {
                CADENA = CADENA + LINE;
            }
        } catch (IOException e) {
        }
        return CADENA;
    }

    /**
     * Genera N números aleatorios y los escribe en el archivo ,
     * separados por espacios, luego agrega la @KEY al final
     * 
     * @param N
     */
    public static void WriteData(int N) {
        double X;
        long num;
        try {
            FileWriter FW = new FileWriter(FILENAME);
            for (int i = 1; i <= N - 1; i++) {
                X = Math.random() * 9000000000.0;
                num = 1000000000 + (long) X;
                FW.write(num + " ");
            }
            FW.write("01234567890");
            FW.close();
        } catch (IOException E) {
            System.out.print(E.getMessage());
        }
    }

    /**
     * 
     * @param args
     */
    // --------------------------------------------------
    public static void main(String[] args) {
        WriteData(N);
        ProcesoSerial();
    }

}
