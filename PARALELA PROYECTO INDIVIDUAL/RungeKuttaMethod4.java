public class RungeKuttaMethod4 {
    public static double funcion(double x, double y) {
        return x + y;
    }

    static class Oper extends Thread {
        private double x;
        private double y;
        private double h;
        private double error;
        private double newH;

        public Oper(double x, double y, double h) {
            this.x = x;
            this.y = y;
            this.h = h;
        }

        @Override
        public void run() {
            double k1 = funcion(x, y);
            double k2 = funcion(x + 0.5 * h, y + 0.5 * k1 * h);
            double k3 = funcion(x + 0.5 * h, y + 0.5 * k2 * h);
            double k4 = funcion(x + h, y + k3 * h);
            double newY = y + h / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
            error = Math.abs(newY - y);
            newH = (error < 0.1) ? h * 2 : h / 2;
        }

        public double getNewH() {
            return newH;
        }

        public double getError() {
            return error;
        }
    }

    public static void RungeKuttaParalelo(double x0, double y0, double h, int n) {
        double x = x0;
        double y = y0;

        for (int i = 0; i < n; i++) {
            Oper thread = new Oper(x, y, h);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            h = thread.getNewH();
            x += h;
            System.out.println("x: " + x + ", y: " + y + ", incremento: " + h);
        }
    }

    public static void main(String[] args) {
        RungeKuttaParalelo(0.0, 1.0, 0.1, 10);
    }
}
