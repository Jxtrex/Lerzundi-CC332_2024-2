package PC2_Lerzundi;

import java.io.IOException;
import java.io.RandomAccessFile;

public class P2 {
    // Dimensiones de la matriz
    private static final int M = 10;
    private static final int N = 10;
    static final int MTX[][] = new int[M][N];

    // Para leer datos
    private static String FILENAME = "PC2_Lerzundi/PC2_DATOS.TXT";
    private static String CADENA;
    private static int BLOCK = 4;
    private static byte[] RECORD = new byte[BLOCK];

    public static int DeterminanteGeneralSerial(int MTX[][], int M, int N) {
        if (M == 1 && N == 1)
            return MTX[0][0];
        if (M == N) {
            int DET = 0;
            if (M == 2) {
                DET = DET + MTX[0][0] * MTX[1][1] - MTX[0][1] * MTX[1][0];
            } else if (M == 3) {
                DET = DET +
                        MTX[0][0] * MTX[1][1] * MTX[2][2]
                        + MTX[0][1] * MTX[1][2] * MTX[2][0]
                        + MTX[0][2] + MTX[1][0] * MTX[2][1]
                        - MTX[0][2] * MTX[1][1] * MTX[2][0]
                        - MTX[0][0] * MTX[1][2] * MTX[2][1]
                        - MTX[0][1] * MTX[1][0] * MTX[2][2];

                return DET;
            }
        }
        return -1;
    }

    // ------------------------------------------
    private static String GetString() {
        String CAD;
        CAD = "";
        for (int i = 0; i < BLOCK - 1; i++) {
            CAD = CAD + (char) (RECORD[i]);
        }
        return CAD;
    }

    // ------------------------------------------
    public static void PrintMatrixToScreen(int MTX[][], String TITLE) {
        int N = MTX.length;
        System.out.println(TITLE);
        for (int i = 0; i <= N - 1; i++) {
            for (int j = 0; j <= N - 1; j++) {
                System.out.print(MTX[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    // ------------------------------------------
    public static int[][] SubMatrix(int MTX[][], int I1, int I2, int J1, int J2) {

        int rows = I2 - I1 + 1;
        int cols = J2 - J1 + 1;
        int[][] MTX_SUB = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                MTX_SUB[i][j] = MTX[I1 + i][J1 + j];
            }
        }
        return MTX_SUB;

    }

    // ------------------------------------------
    public static void ReadMatrixFromFile() {
        long n, P, T;
        int i;
        try {
            RandomAccessFile RAF = new RandomAccessFile(FILENAME, "r");

            T = RAF.length();
            n = T / BLOCK;
            i = 0;

            int row = 0;
            int col = 0;
            while (row < M) {

                while ((i <= n - 1) && (col < M)) {
                    RAF.seek(i * BLOCK);
                    RAF.read(RECORD);

                    CADENA = GetString();
                    MTX[row][col] = Integer.valueOf(CADENA);

                    col++;
                    i++;
                }
                col = 0;
                row++;

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static int[][] DeterminanteGeneralParalelo(int MTX[][], int M, int N) {
        throw new IllegalArgumentException("Not defined");
    }

    public static void main(String[] args) {
        P2 Main = new P2();
        int[][] MTX_SUB;

        P2.ReadMatrixFromFile();

        PrintMatrixToScreen(MTX, "\tMATRIZ RECUPERADA DE UN TXT");
        MTX_SUB = SubMatrix(MTX, 2, 4, 2, 4);
        PrintMatrixToScreen(MTX_SUB, "SUBMATRIZ");
        System.out.println("DETERMINANTE DE LA SUB MATRIZ");
        System.out.println("DET: " + DeterminanteGeneralSerial(MTX_SUB, 3, 3));

        System.out.println("LARGO MTX: " + MTX.length);
        System.out.println("DETERMINANTE DE TODAS LAS SUBMATRICES");

        for (int i1 = 0; i1 < M; i1++) {
            for (int i2 = i1; i2 < M; i2++) {
                for (int j1 = 0; j1 < N; j1++) {
                    for (int j2 = j1; j2 < N; j2++) {
                        int[][] MTX_SUBMATRIX = SubMatrix(MTX, i1, i2, j1, j2);

                        if (MTX_SUBMATRIX.length == MTX_SUBMATRIX[0].length) {
                            int DET = DeterminanteGeneralSerial(MTX_SUBMATRIX, MTX_SUBMATRIX.length,
                                    MTX_SUBMATRIX[0].length);

                            PrintMatrixToScreen(MTX_SUBMATRIX, "SUB MATRIX");
                            if (MTX_SUBMATRIX.length > 3)
                                System.out.println("DET: No Definido para orden mayor a 3");
                            else
                                System.out.println("DET: " + DET);
                        }
                    }
                }
            }
        }
    }
}
