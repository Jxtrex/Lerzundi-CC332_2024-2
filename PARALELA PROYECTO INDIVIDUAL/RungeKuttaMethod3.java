public class RungeKuttaMethod3 {
    public static double funcion(double x, double y) {
        return x + y;
    }

    static class Oper extends Thread {
        private double x;
        private double y;
        private double h;
        private double result;
        private int blockSize;

        public Oper(double x, double y, double h, int blockSize) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.blockSize = blockSize;
        }

        @Override
        public void run() {
            double k1 = funcion(x, y);
            double k2 = funcion(x + 0.5 * h, y + 0.5 * k1 * h);
            double k3 = funcion(x + 0.5 * h, y + 0.5 * k2 * h);
            double k4 = funcion(x + h, y + k3 * h);
            result = y + h / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
        }

        public double getResultado() {
            return result;
        }
    }

    public static void RungeKuttaParalelo(double x0, double y0, double h, int n, int numThreads) {
        double x = x0;
        double y = y0;

        for (int i = 0; i < n; i++) {
            Thread[] threads = new Thread[numThreads];
            Oper[] kThreads = new Oper[numThreads];
            for (int j = 0; j < numThreads; j++) {
                kThreads[j] = new Oper(x, y, h, j + 1);
                threads[j] = kThreads[j];
                threads[j].start();
            }
            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            double result = 0;
            for (Oper thread : kThreads) {
                result += thread.getResultado();
            }
            y = result / numThreads;
            x += h;
            System.out.println("x: " + x + ", y: " + y);
        }
    }

    public static void main(String[] args) {
        RungeKuttaParalelo(0.0, 1.0, 0.1, 10, 4);
    }
}
