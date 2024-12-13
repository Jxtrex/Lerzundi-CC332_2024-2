import java.util.concurrent.*;

public class RungeKuttaMethod5 {
    public static double funcion(double x, double y) {
        return x + y;  
    }

    static class Oper extends Thread {
        private double x;
        private double y;
        private double h;
        private BlockingQueue<Double> queue;
        
        public Oper(double x, double y, double h, BlockingQueue<Double> queue) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.queue = queue;
        }

        @Override
        public void run() {
            double k1 = funcion(x, y);
            double k2 = funcion(x + 0.5 * h, y + 0.5 * k1 * h);
            double k3 = funcion(x + 0.5 * h, y + 0.5 * k2 * h);
            double k4 = funcion(x + h, y + k3 * h);
            try {
                queue.put((k1 + 2 * k2 + 2 * k3 + k4) / 6);  // Put the result in the queue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void RungeKuttaParalelo(double x0, double y0, double h, int n) {
        double x = x0;
        double y = y0;
        BlockingQueue<Double> queue = new LinkedBlockingQueue<>();

        for (int i = 0; i < n; i++) {
            Oper thread = new Oper(x, y, h, queue);
            thread.start();

            try {
                double result = queue.take();  
                y = result;  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x += h;
            System.out.println("x: " + x + ", y: " + y);
        }
    }

    public static void main(String[] args) {
        RungeKuttaParalelo(0.0, 1.0, 0.1, 10);
    }
}
