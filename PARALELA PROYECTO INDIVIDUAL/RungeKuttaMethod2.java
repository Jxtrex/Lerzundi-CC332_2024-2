public class RungeKuttaMethod2 {
    public static double funcion(double x, double y) {
        return x + y;
    }

    static class Oper extends Thread {
        private double x;
        private double y;
        private double h;
        private double kResult;
        private int kIndex;

        public Oper(double x, double y, double h, int kIndex) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.kIndex = kIndex;
        }

        @Override
        public void run() {
            switch (kIndex) {
                case 1:
                    kResult = funcion(x, y);
                    break;
                case 2:
                    kResult = funcion(x + 0.5 * h, y + 0.5 * h * kResult);
                    break;
                case 3:
                    kResult = funcion(x + 0.5 * h, y + 0.5 * h * kResult);
                    break;
                case 4:
                    kResult = funcion(x + h, y + h * kResult);
                    break;
            }
        }

        public double getResultado() {
            return kResult;
        }
    }

    public static void RungeKuttaParalelo(double x0, double y0, double h, int n) {
        double x = x0;
        double y = y0;

        for (int i = 0; i < n; i++) {
            Thread[] threads = new Thread[4];
            Oper[] kThreads = new Oper[4];

            for (int j = 0; j < 4; j++) {
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

            double k1 = kThreads[0].getResultado();
            double k2 = kThreads[1].getResultado();
            double k3 = kThreads[2].getResultado();
            double k4 = kThreads[3].getResultado();

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
        // =======================
        RungeKuttaParalelo(x0, y0, h, n);
    }
}
