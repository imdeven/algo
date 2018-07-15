import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by devendra.mandan on 15/07/18.
 */
public class PercolationStats {

    private Percolation percolation;

    private double[] fractionOfOpenSites;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        fractionOfOpenSites = new double[trials];
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int randRow = StdRandom.uniform(1, n + 1);
                int randCol = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(randRow, randCol)) {
                    percolation.open(randRow, randCol);
                }
            }
            fractionOfOpenSites[i] = (double) percolation.numberOfOpenSites() / n;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(fractionOfOpenSites);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (fractionOfOpenSites.length==1) return Double.NaN;
        stddev = StdStats.stddev(fractionOfOpenSites);
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (described below)
    public static void main(String[] args) {

    }
}
