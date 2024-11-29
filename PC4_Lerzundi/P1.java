import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class P1 {

    int[][] MATRIX;

    public void ReadmFromFile(String DATAFILE, int M, int N) throws IOException {
        String CADENA;
        long T;
        int W;
        byte[] BUFFER;

        MATRIX = new int[M][N];

        try {
            RandomAccessFile RAF = new RandomAccessFile(DATAFILE, "r");
            T = RAF.length();
            if (T % (M * N) == 0) {
                System.out.println("\n" + DATAFILE + "\n-------------");
                W = (int) (T / (M * N));
                BUFFER = new byte[W];

                int index = 0;
                for (int i = 0; i < M; i++) {
                    for (int j = 0; j < N; j++) {
                        RAF.seek(i * N * W + j * W);
                        RAF.read(BUFFER);
                        CADENA = BufferToString(BUFFER);
                        MATRIX[i][j] = Integer.parseInt(CADENA);
                    }
                }

                RAF.close();
            } else {
                System.out.println("ERROR: Dimensión Incorrecta!!!");
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    public void OrdenarDataSet(int M, int N) {
        int[] temp = new int[M * N];
        int index = 0;

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                temp[index++] = MATRIX[i][j];
            }
        }

        Burbuja(temp);

        index = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                MATRIX[i][j] = temp[index++];
            }
        }

        System.out.println("MATRIX:");
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(MATRIX[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void BuscarElementoEnDataSet(int target) {
        int[] temp = new int[MATRIX.length * MATRIX[0].length];
        int index = 0;

        for (int i = 0; i < MATRIX.length; i++) {
            for (int j = 0; j < MATRIX[i].length; j++) {
                temp[index++] = MATRIX[i][j];
            }
        }

        Burbuja(temp);

        int result = BusquedaBinaria(temp, target);

        if (result != -1) {
            System.out.println("Elemento " + target + " en ind: " + result);
        } else {
            System.out.println("Elemento " + target + " no encontrado.");
        }
    }

    public void InsertElement(int M, int N, int row, int col, int element) {
        if (row < 0 || row >= M || col < 0 || col >= N) {
            System.out.println("ERROR: Pos inválida");
            return;
        }

        MATRIX[row][col] = element;

        System.out.println("Matriz:  " + element + " -> (" + row + ", " + col + "):");
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(MATRIX[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void DeleteElement(int M, int N, int row, int col) {
        if (row < 0 || row >= M || col < 0 || col >= N) {
            System.out.println("ERROR: Pos inválida");
            return;
        }

        MATRIX[row][col] = 0;

        System.out.println("Matrix after deleting element at (" + row + ", " + col + "):");
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(MATRIX[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void ModifyElement(int M, int N, int row, int col, int newElement) {
        if (row < 0 || row >= M || col < 0 || col >= N) {
            System.out.println("ERROR: Pos inválida");
            return;
        }

        MATRIX[row][col] = newElement;

        System.out.println("Matrix: -> (" + row + ", " + col + ") to " + newElement + ":");
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(MATRIX[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public String BufferToString(byte[] BUFFER) {
        String CADENA = "";
        int L = BUFFER.length;
        for (int i = 0; i <= L - 1; i++) {
            CADENA = CADENA + (char) BUFFER[i];
        }
        return CADENA;
    }

    public static int BusquedaBinaria(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
            }

            if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    public static void Burbuja(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        /*
         * PP_Tools OBJ = new PP_Tools();
         * String CADENA;
         * int T = 12;
         * int SIZE = 10;
         * int M = 5;
         * int N = 3;
         * int A[] = {10,0,100}; //N
         * int B[] = {99,1,999}; //N
         * int S[] = {4,2,5}; //N
         * 
         * OBJ.CreateDataFileVariable("Datos1.TXT",M,N,'H',A,B,S);
         * OBJ.CreateDataFileVariable("Datos2.TXT",M,N,'V',A,B,S);
         */
    }
}
