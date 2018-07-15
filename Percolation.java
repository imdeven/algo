import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] grid;
    private int numOpenSites = 0;
    private int gridSide;
    private WeightedQuickUnionUF network;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be positive integer");
        grid = new int[n + 1][n + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                grid[i][j] = 0;
            }
        }
        gridSide = n;
        network = new WeightedQuickUnionUF(n * n + 2);
    }

    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > gridSide || col > gridSide)
            throw new IllegalArgumentException("invalid grid index");
    }

    private boolean isValid(int row, int col) {
        if (row < 1 || row > gridSide) return false;
        if (col < 1 || col > gridSide) return false;
        return true;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (grid[row][col] == 1) return;
        else grid[row][col] = 1;
        numOpenSites++;
        if (isValid(row - 1, col) && isOpen(row - 1, col)) {
            network.union((row - 1) * gridSide + col, (row - 2) * gridSide + col);
        }
        if (isValid(row, col - 1) && isOpen(row, col - 1)) {
            network.union((row - 1) * gridSide + col, (row - 1) * gridSide + col - 1);
        }
        if (isValid(row, col + 1) && isOpen(row, col + 1)) {
            network.union((row - 1) * gridSide + col, (row - 1) * gridSide + col + 1);
        }
        if (isValid(row + 1, col) && isOpen(row + 1, col)) {
            network.union((row - 1) * gridSide + col, row * gridSide + col);
        }
        //connect false nodes to top and bottom row
        if (row == 1) {
            network.union((row - 1) * gridSide + col, 0);
        }
        if (row == gridSide) {
            network.union((row - 1) * gridSide + col, gridSide * gridSide + 1);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return network.connected(0, (row - 1) * gridSide + col);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return network.connected(0, gridSide * gridSide + 1);
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        System.out.println(percolation.numberOfOpenSites());
        for (int i = 1; i <= 5; i++) {
            percolation.open(i, i);
        }
        percolation.open(1, 2);
        percolation.open(2, 3);
        percolation.open(4, 3);
        percolation.open(5, 4);
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
    }
}

