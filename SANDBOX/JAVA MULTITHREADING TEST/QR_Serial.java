public class QR_Serial {

    // --------------------------------------------------------------
    public static void Process_Serial() {
        long Time1, Time2;
        double x;
        double[][] A1;

        // Lee una matriz desde un archivo en A1
        A1 = DataSet.ReadFile(DataSet.filas, DataSet.columnas);
        Time1 = System.currentTimeMillis();
        Matrix A = new Matrix(A1);
        int filas = A.getRows();
        int columnas = A.getCols();
        Matrix R = new Matrix(new double[columnas][columnas]);

        // Iteramos sobre las las columnas de la matriz
        for (int i = 0; i < columnas; i++) {
            for (int j = i; j < columnas; j++) {
                // Calculamos los elementos de R[i][j]
                x = A.prodEsc(i, j) / Math.sqrt(A.prodEsc(i, i));
                R.SetCell(i, j, x);
            }

            for (int k = 0; k < filas; k++) {
                // Normalizamos la columna "i" y actualizamos en A
                x = A.GetCell(k, i) / R.GetCell(i, i);
                A.SetCell(k, i, x);
            }

            for (int j = i + 1; j < columnas; j++) {
                // Actualizamos las columnas restantes de A
                for (int k = 0; k < filas; k++) {
                    x = A.GetCell(k, j) - R.GetCell(i, j) * A.GetCell(k, i);
                    A.SetCell(k, j, x);
                }
            }
        }

        DataSet.WriteFile(A, "Matriz Q - Serial.txt");
        DataSet.WriteFile(R, "Matriz R - Serial.txt");
        Time2 = System.currentTimeMillis();

        double FinalTime = (Time2 - Time1) / 1000.0;
        System.out.println("FLAG FinalTime: " + FinalTime);

        System.out.printf("Tiempo de Procesamiento Serial: %f%n", FinalTime);
    }

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    public static void main(String[] args) {
        Process_Serial();
    }
}// CLASS
