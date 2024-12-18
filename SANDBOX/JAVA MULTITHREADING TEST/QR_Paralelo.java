public class QR_Paralelo {
    public static final int HILOS = 2;

    // --------------------------------------------------------------
    public static void main(String[] args) {
        long Time1, Time2;
        // Lee la matriz desde un archivo en A1
        double[][] A1 = DataSet.ReadFile(DataSet.filas, DataSet.columnas);
        Time1 = System.currentTimeMillis();
        Matrix A = new Matrix(A1);
        Matrix R = new Matrix(new double[A.getCols()][A.getCols()]);
        
        for (int i = 0; i < A.getCols(); i++) {
            Thread[] threads = new Thread[HILOS];
            // Ejecuta todos los threads
            for (int t = 0; t < HILOS; t++) {
                threads[t] = new Thread(new Oper1(t + 1, A, R, i));
                threads[t].start();
            }
            // Aquí el thread:main espera a los otros threads antes de continuar su ejecución
            try {
                for (int t = 0; t < HILOS; t++) {
                    threads[t].join();
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            double x = Math.sqrt(R.GetCell(i, i));
            for (int j = i; j < A.getCols(); j++) {
                R.SetCell(i, j, R.GetCell(i, j) / x);
            }
            // De la misma manera , se ejecutan los threads on otra operación
            for (int t = 0; t < HILOS; t++) {
                threads[t] = new Thread(new Oper2(t + 1, A, R, i));
                threads[t].start();
            }
            // Volvemos a esperar a que finalicen los threads antes de seguir
            try {
                for (int t = 0; t < HILOS; t++) {
                    threads[t].join();
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            for (int t = 0; t < HILOS; t++) {
                threads[t] = new Thread(new Oper3(t + 1, A, R, i));
                threads[t].start();
            }
            try {
                for (int t = 0; t < HILOS; t++) {
                    threads[t].join();
                }
            } catch (InterruptedException e) {

                System.out.println(e.getMessage());
            }
        }
        DataSet.WriteFile(A, "Matriz Q - Paralelo.txt");
        DataSet.WriteFile(R, "Matriz R - Paralelo.txt");
        Time2 = System.currentTimeMillis();

        double FinalTime = (Time2 - Time1) / 1000.0;

        System.out.printf("Tiempo de Procesamiento Paralelo: %f%n", FinalTime);
    }
}

class Oper1 implements Runnable {
    private Matrix A;
    private Matrix R;
    private int filIni;
    private int filFin;
    private int i;

    public Oper1(int nroHilo, Matrix A, Matrix R, int i) {
        this.A = A;
        this.R = R;
        filIni = (nroHilo - 1) * A.getRows() / QR_Paralelo.HILOS;
        filFin = nroHilo * A.getRows() / QR_Paralelo.HILOS - 1;
        this.i = i;
    }

    @Override
    public void run() {
        for (int j = i; j < A.getCols(); j++) {
            R.incrementar(i, j, A.prodEsc(i, j, filIni, filFin));
        }
    }
}

class Oper2 implements Runnable {
    private Matrix A;
    private Matrix R;
    private int filIni;
    private int filFin;
    private int i;

    public Oper2(int nroHilo, Matrix A, Matrix R, int i) {
        this.A = A;
        this.R = R;
        filIni = (nroHilo - 1) * A.getRows() / QR_Paralelo.HILOS;
        filFin = nroHilo * A.getRows() / QR_Paralelo.HILOS - 1;
        this.i = i;
    }

    @Override
    public void run() {
        double x = R.GetCell(i, i);
        for (int k = filIni; k <= filFin; k++) {
            A.SetCell(k, i, A.GetCell(k, i) / x);
        }
    }
}

class Oper3 implements Runnable {
    private Matrix A;
    private Matrix R;
    private int filIni;
    private int filFin;
    private int i;

    public Oper3(int nroHilo, Matrix A, Matrix R, int i) {
        this.A = A;
        this.R = R;
        filIni = (nroHilo - 1) * A.getRows() / QR_Paralelo.HILOS;
        filFin = nroHilo * A.getRows() / QR_Paralelo.HILOS - 1;
        this.i = i;
    }

    @Override
    public void run() {
        for (int j = i + 1; j < A.getCols(); j++) {
            double x = R.GetCell(i, j);
            for (int k = filIni; k <= filFin; k++) {
                A.SetCell(k, j, A.GetCell(k, j) - x * A.GetCell(k, i));
            }
        }
    }
}
