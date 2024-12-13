public class RungeKuttaSerial {

    public static double FuncionSinY(double x) {
        return x;
    }

    public static double FuncionConY(double x, double y) {
        return x + y;
    }

    public static void RungeKuttaSinY(double x0, double y0, double h, int n) {
        double x = x0;
        double y = y0;

        for (int i = 0; i < n; i++) {
            double k1 = FuncionSinY(x);
            double k2 = FuncionSinY(x + 0.5 * h);
            double k3 = FuncionSinY(x + 0.5 * h);
            double k4 = FuncionSinY(x + h);

            y = y + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4);
            x += h;
            System.out.println("x: " + x + ", y: " + y);
        }
    }

    public static void RungeKuttaConY(double x0, double y0, double h, int n) {
        double x = x0;
        double y = y0;

        for (int i = 0; i < n; i++) {
            double k1 = FuncionConY(x, y);
            double k2 = FuncionConY(x + 0.5 * h, y + 0.5 * h * k1);
            double k3 = FuncionConY(x + 0.5 * h, y + 0.5 * h * k2);
            double k4 = FuncionConY(x + h, y + h * k3);

            y = y + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4);
            x += h;
            System.out.println("x: " + x + ", y: " + y);
        }
    }

    public static void main(String[] args) {
        double x0 = 0.0; 
        double y0 = 1.0; 
        double h = 0.1; 
        int n = 10; 

        System.out.println("RUNGE KUTTA SIN DEPENDENCIA DE Y:");
        RungeKuttaSinY(x0, y0, h, n);
        System.out.println("\nRUNGE KUTTA CON DEPENDENCIA DE Y:");
        RungeKuttaConY(x0, y0, h, n);
    }
}
