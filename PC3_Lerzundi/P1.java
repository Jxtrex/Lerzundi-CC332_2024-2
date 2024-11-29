import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class P1 {

    private double[] X;
    private double[] Y;
    private double targetX;

    public P1(double[] X, double[] Y, double targetX) {
        this.X = X;
        this.Y = Y;
        this.targetX = targetX;
    }

    public double LagrangeInterpolationSerial() {
        double result = 0.0;
        int n = X.length;

        for (int i = 0; i < n; i++) {
            double term = Y[i];

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    term = term * (targetX - X[j]) / (X[i] - X[j]);
                }
            }

            result += term;
        }

        return result;
    }

    public double LagrangeInterpolationParallelUsingThreads() throws InterruptedException {
        int n = X.length;
        double result = 0.0;

        Thread[] threads = new Thread[n];
        double[] partialResults = new double[n];

        for (int i = 0; i < n; i++) {
            final int index = i;
            threads[i] = new Thread(new Operation_1(X, Y, targetX, index, partialResults));
            threads[i].start();
        }

        for (int i = 0; i < n; i++) {
            threads[i].join();
        }

        for (int i = 0; i < n; i++) {
            result += partialResults[i];
        }

        return result;
    }

    public double LagrangeInterpolationParallelSynchronizedQueue() {
        BlockingQueue<Double> REQUEST_X = new LinkedBlockingQueue<>();
        BlockingQueue<Double> REQUEST_Y = new LinkedBlockingQueue<>();
        BlockingQueue<Result> RESPONSE = new LinkedBlockingQueue<>();
        Operation_2 OBJ = new Operation_2(REQUEST_X, REQUEST_Y, RESPONSE, targetX);
        OBJ.start();

        try {
            for (double x : X) {
                REQUEST_X.put(x);
            }
            for (double y : Y) {
                REQUEST_Y.put(y);
            }

            Result result = RESPONSE.take();
            return result.getInterpolatedValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Double.NaN;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        double[] X = { 1.0, 2.0, 3.0 }; // X values
        double[] Y = { 2.0, 3.0, 5.0 }; // Y values corresponding to X
        double targetX = 2.5;

        P1 p1 = new P1(X, Y, targetX);
        double interpolatedY_1 = p1.LagrangeInterpolationSerial();
        System.out.println("The interpolated value at X = " + targetX + " is: " + interpolatedY_1);
        P1 p2 = new P1(X, Y, targetX);
        double interpolatedY_2 = p2.LagrangeInterpolationParallelUsingThreads();
        System.out.println("The interpolated value at X = " + targetX + " is: " + interpolatedY_2);
        P1 p3 = new P1(X, Y, targetX);
        double interpolatedY_3 = p3.LagrangeInterpolationParallelSynchronizedQueue();
        System.out.println("The interpolated value at X = " + targetX + " is: " + interpolatedY_3);

    }
}

class Operation_1 implements Runnable {
    private double[] X;
    private double[] Y;
    private double targetX;
    private int index;
    private double[] partialResults;

    public Operation_1(double[] X, double[] Y, double targetX, int index, double[] partialResults) {
        this.X = X;
        this.Y = Y;
        this.targetX = targetX;
        this.index = index;
        this.partialResults = partialResults;
    }

    @Override
    public void run() {
        double L = 1.0;
        for (int j = 0; j < X.length; j++) {
            if (j != index) {
                L *= (targetX - X[j]) / (X[index] - X[j]);
            }
        }

        partialResults[index] = Y[index] * L;
    }
}

class Operation_2 {
    private final BlockingQueue<Double> IN_X;
    private final BlockingQueue<Double> IN_Y;
    private final BlockingQueue<Result> OUT;
    private final double targetX;

    public Operation_2(BlockingQueue<Double> REQUEST_X, BlockingQueue<Double> REQUEST_Y, BlockingQueue<Result> RESPONSE,
            double targetX) {
        this.IN_X = REQUEST_X;
        this.IN_Y = REQUEST_Y;
        this.OUT = RESPONSE;
        this.targetX = targetX;
    }

    public void start() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    double interpolatedValue = lagrangeInterpolation();
                    OUT.put(new Result(interpolatedValue)); // Send the result to the queue
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private double lagrangeInterpolation() throws InterruptedException {
        double result = 0.0;
        int n = 0;

        double[] X = new double[IN_X.size()];
        double[] Y = new double[IN_Y.size()];

        while (n < X.length) {
            X[n] = IN_X.take();
            Y[n] = IN_Y.take();
            n++;
        }

        for (int i = 0; i < X.length; i++) {
            double term = Y[i];
            for (int j = 0; j < X.length; j++) {
                if (i != j) {
                    term = term * (targetX - X[j]) / (X[i] - X[j]);
                }
            }
            result += term;
        }

        return result;
    }
}

class Result {
    private final double interpolatedValue;

    public Result(double interpolatedValue) {
        this.interpolatedValue = interpolatedValue;
    }

    public double getInterpolatedValue() {
        return interpolatedValue;
    }

    @Override
    public String toString() {
        return "Interpolated value: " + interpolatedValue;
    }
}
