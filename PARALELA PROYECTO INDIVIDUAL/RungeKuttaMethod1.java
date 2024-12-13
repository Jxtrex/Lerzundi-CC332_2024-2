public class RungeKuttaMethod1 {
    public static double funcion(double x) {
        return x;  
    }

    static class Oper extends Thread {
        private double x;
        private double h;
        private double kResult;
        private int kIndex;

        public Oper(double x, double h, int kIndex) {
            this.x = x;
            this.h = h;
            this.kIndex = kIndex;
        }

        @Override
        public void run() {
            kResult = funcion(x);  
        }

        public double getResultado() {
            return kResult;
        }
    }

    public static void RungeKuttaParalelo(double x0, double h, int n) {
        double x = x0;
        for (int i = 0; i < n; i++) {
            Thread[] threads = new Thread[4];
            Oper[] kThreads = new Oper[4];

            for (int j = 0; j < 4; j++) {
                kThreads[j] = new Oper(x, h, j + 1);
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

            double y = (k1 + 2 * k2 + 2 * k3 + k4) / 6;
            x += h;

            System.out.println("x: " + x + ", y: " + y);
        }
    }

    public static void main(String[] args) {
        RungeKuttaParalelo(0.0, 0.1, 10);
    }
}
