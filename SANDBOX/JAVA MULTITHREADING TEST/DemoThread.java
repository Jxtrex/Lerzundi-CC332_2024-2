import java.util.Random;

public class DemoThread {
    // --------------------------------------------
    private static final int N = 50;
    // A y B se usarán para las operaciones
    private static int A[][] = new int[N][N];
    private static int B[][] = new int[N][N];
    private static int C[][] = new int[N][N];

    private static Random RND = new Random();
    private static Tools TLS = new Tools();
    /**
     * Retorna el producto de matrices
     * @param MTX1
     * @param MTX2
     * @return PROD
     */
    // --------------------------------------------
    public static int[][] ProductoSerial(int MTX1[][], int MTX2[][]) {
        int PROD[][] = new int[N][N];
        for (int i = 0; i <= N - 1; i++) {
            for (int j = 0; j <= N - 1; j++) {
                PROD[i][j] = 0;
                for (int k = 0; k <= N - 1; k++) {
                    PROD[i][j] = PROD[i][j] + MTX1[i][k] * MTX2[k][j];
                }
            }
        }
        return PROD;
    }
/**
 * MTX[i][j] = A_ik * B_kj (k in {0...(n-1)})
 * @param i
 * @param j
 * @param MTX
 */
    // --------------------------------------------
    public static void ProductoInterno(int i, int j, int MTX[][]) {
        if (MTX[i][j] != 0) {
            System.out.println("FLAG! WRONG INITIALIZATION");
        }
        MTX[i][j] = 0;
        for (int k = 0; k <= N - 1; k++) {
            MTX[i][j] = MTX[i][j] + A[i][k] * B[k][j];
        }
    }
    /**
     * A x B usando @ProductoInterno en Paralelo 
     * @return
     */
    // --------------------------------------------
    public static int[][] ProductoParalelo() {
        int PROD[][] = new int[N][N];
        int R;
        for (int i = 0; i <= N - 1; i++) {
            for (int j = 0; j <= N - 1; j++) {
                R = (i + j) % 4;
                // - - - - - - - - - - - - - - - - - - - - - - - -
                // FINAL COPIES OF i & j
                final int row = i;
                final int col = j;
                // - - - - - - - - - - - - - - - - - - - - - - - -
                if (R == 0) {
                    new Thread(new Runnable() {
                        public void run() {
                            ProductoInterno(row, col, PROD);
                        }
                    }).start();
                } else if (R == 1) {
                    new Thread(new Runnable() {
                        public void run() {
                            ProductoInterno(row, col, PROD);
                        }
                    }).start();
                } else if (R == 2) {
                    new Thread(new Runnable() {
                        public void run() {
                            ProductoInterno(row, col, PROD);
                        }
                    }).start();
                } else {
                    new Thread(new Runnable() {
                        public void run() {
                            ProductoInterno(row, col, PROD);
                        }
                    }).start();
                }
                // - - - - - - - - - - - - - - - - - - - - - - - -
            }
        }
        return PROD;
    }
/**
 * MTX[i][j] = un número aleatorio entre AA y BB-1
 * @param MTX
 * @param AA
 * @param BB
 */
    // --------------------------------------------
    public static void LoadData(int MTX[][], int AA, int BB) {
        for (int i = 0; i <= N - 1; i++) {
            for (int j = 0; j <= N - 1; j++) {
                MTX[i][j] = RND.nextInt(AA, BB + 1);
            }
        }
    }

    // --------------------------------------------
    public static void main(String args[]) {
        LoadData(A, -10, 10);
        LoadData(B, -10, 10);

        TLS.PrintMatrixToScreen(A, "Matriz A");
        TLS.PrintMatrixToScreen(B, "Matriz B");
        TLS.PrintMatrixToScreen(ProductoSerial(A, B), "Producto AxB");
        // TLS.PrintMatrixToScreen(ProductoSerial(B, A), "Producto BxA");

        long inicio, fin;

        inicio = System.currentTimeMillis();
        C = ProductoSerial(A, B);
        fin = System.currentTimeMillis() - inicio;
        System.out.println("Time Execution: " + inicio / 1000 + " segundos");

        // TLS.PrintMatrixToFile(C,"Producto AxB - Serial","AxB.TXT");
        // TLS.PrintMatrixToHTML(ProductoSerial(A,B),"Producto AxB","AxB.HTML");

        inicio = System.currentTimeMillis();
        C = ProductoParalelo();
        fin = System.currentTimeMillis() - inicio;
        System.out.println("Time Execution: " + inicio / 1000 + " segundos");
        // TLS.PrintMatrixToFile(C,"Producto AxB","AxB - Paralelo.TXT");
        // TLS.PrintMatrixToHTML(C,"Producto AxB","AxB - Paralelo.html");

    }
    // --------------------------------------------
    // --------------------------------------------
}