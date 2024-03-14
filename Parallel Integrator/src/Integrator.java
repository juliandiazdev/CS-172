/**
 * A parallel integrator.
 *
 * Julian
 * Farjad
 */
public class Integrator extends Thread {  // why do we extend a thread instead of implementing Univariate Function

    static private long INTERVALS = 1 << 50; // Increments the values of the

    // The instance variables for each instance of Thread.
    private int id;
    private UnivariateFunction f;
    private double a;
    private double b;
    private double[] results;

    /**
     * A constructor for our thread.
     *
     * The thread will estimate the Riemann integral of function f on the
     * interval [a, b].  Once of the thread has computed the estimate,
     * it will write it in one entry of results, an array of doubles.  The
     * thread will use its id to determine which entry of results to update.
     * In other words, id better be greater or equal to 0 and less than
     * results.length.
     *
     * @param id  An id for this thread; it will be used to write an entry in results
     * @param f  The function to be integrated
     * @param a  The lower bound of the integration
     * @param b  The upper bound of the integration
     * @param results  A reference to an array of results, an array of doubles
     */
    public Integrator(int id, UnivariateFunction f, double a, double b, double[] results) {
        this.id = id;
        this.f = f;
        this.a = a;
        this.b = b;
        this.results = results;
    }

    /**
     * When the thread is started, it will execute this function.
     */
    public void run() {
        results[id] = integrate(f, a, b);
    }

    /**
     * Compute an estimate of the Riemann integral of function f on the interval [a, b].
     * @param f  the function to integrate
     * @param a  the lower bound of the integration
     * @param b  the upper bound of the integration
     * @return an estimate of the Riemann integral of function f on the interval [a, b]
     */
    static private double integrate(UnivariateFunction f, double a, double b) {
        double step = (b - a)/INTERVALS;
        double integral = 0.0;
        for (int i = 0; i < INTERVALS; ++i) {
            integral += f.eval(a + (i + 0.5)*step) * step;
        }
        return integral;
    }



    /**
     * Compute an estimate of the Riemann integral of function f on the interval [a, b] in parallel.
     *
     * This function breaks the interval [a, b] into numThreads sub-intervals and assigns a separate
     * thread to compute the integral on each of those sub-intervals.  When all threads have completed,
     * this function adds all the results and returns the sum.
     *
     * @param f  the function to integrate
     * @param a  the lower bound of the integration
     * @param b  the upper bound of the integration
     * @param numThreads  the number of threads to use
     * @return an estimate of the Riemann integral of function f on the interval [a, b]
     * @throws InterruptedException
     */
    static private double parallelIntegrate(UnivariateFunction f, double a, double b, int numThreads) throws InterruptedException {

        double notA = a;
        double delta = (b - a)/numThreads;
        double sum = 0;

        double[] results = new double[numThreads];
        Thread[] threads = new Thread[numThreads];

        double notB = a + delta;

        for(int i = 0; i < numThreads; i++){
            threads[i] = new Integrator(i ,f , notA, notB, results);
            notA += delta;
            notB += delta;
        }

        for(int i = 0 ; i < numThreads ; i++){
            threads[i].start();
        }

        for(int i=0;i<numThreads;i++){
            threads[i].join();
        }

        for(int i=0; i<numThreads;i++){
            sum+=results[i];
        }
        return sum;
    }

    static public void main(String[] unused) throws InterruptedException {
        double integral;
        double start;
        double end;
        Stopwatch timer = new Stopwatch();

        StdOut.println("The integral of x squared in the interval [0, 10].");
        start = timer.elapsedTime();
        integral = integrate(x -> x*x, 0, 10);
        end = timer.elapsedTime();
        StdOut.printf("Serial:   integral = %.15f (%.1f milliseconds)\n", integral, (end - start)*1000);

        int numThreads = 4;
        start = timer.elapsedTime();
        integral = parallelIntegrate(x -> x*x, 0, 10, numThreads);
        end = timer.elapsedTime();
        StdOut.printf("Parallel: integral = %.15f (%.1f milliseconds, %d threads)\n", integral, (end - start)*1000, numThreads);
    }

}