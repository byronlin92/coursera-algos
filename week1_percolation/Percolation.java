import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int openSiteCount = 0;
    private WeightedQuickUnionUF quickUnion;
    final private int virtualTopSiteIndex = 0;
    final private int virtualBottomSiteIndex = 1; // preset virtual top and bottom to 0,1 index because they will never
                                                  // be overwritten by grid

    private int xyTo1D(int row, int col) {
        return ((row * grid.length - 1) + col);
    }

    private boolean isValidIndices(int row, int col) {
        return (row >= 1 && row <= grid.length - 1 && col >= 1 && col <= grid.length - 1);
    }

    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        if (n < 1) {
            throw new java.lang.IllegalArgumentException("input cannot be non-negative");
        } else {
            int newSize = n + 1; // use new size for 1-based indexing
            grid = new int[newSize][newSize];
            quickUnion = new WeightedQuickUnionUF(newSize * newSize);
        }

        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid.length - 1; j++) {
                grid[i][j] = 0; // 0 = closed
            }
        }
    }

    public void open(int row, int col) { // open site (row, col) if it is not open already
        if (isValidIndices(row, col)) {
            grid[row][col] = 1;
            openSiteCount++;
            int currentIndex = xyTo1D(row, col);

            if (row == 1) { // union virtualTopSiteIndex with open sites in top row
                quickUnion.union(virtualTopSiteIndex, currentIndex);
            } else if (row == grid.length - 1) { // union virtualBottomSiteIndex with open sites in bottom row
                quickUnion.union(currentIndex, virtualBottomSiteIndex);
            }

            // if a site's neighbors are also open, then union them
            if (isValidIndices(row - 1, col)) {
                if (isOpen(row - 1, col)) {
                    int leftIndex = xyTo1D(row - 1, col);
                    quickUnion.union(leftIndex, currentIndex);
                }
            }
            if (isValidIndices(row + 1, col)) {
                if (isOpen(row + 1, col)) {
                    int rightIndex = xyTo1D(row + 1, col);
                    quickUnion.union(rightIndex, currentIndex);
                }
            }
            if (isValidIndices(row, col + 1)) {
                if (isOpen(row, col + 1)) {
                    int downIndex = xyTo1D(row, col + 1);
                    quickUnion.union(downIndex, currentIndex);
                }
            }
            if (isValidIndices(row, col - 1)) {
                if (isOpen(row, col - 1)) {
                    int upIndex = xyTo1D(row, col - 1);
                    quickUnion.union(upIndex, currentIndex);
                }
            }
        } else {
            throw new java.lang.IllegalArgumentException("input is outside prescribed range");
        }
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        if (isValidIndices(row, col)) {
            return (grid[row][col] == 1);
        } else {
            throw new java.lang.IllegalArgumentException(
                    "input is outside prescribed range" + " ,row: " + row + " col: " + col);
        }
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        if (isValidIndices(row, col)) {
            int currentIndex = xyTo1D(row, col);
            return (quickUnion.connected(currentIndex, virtualTopSiteIndex));
        } else {
            throw new java.lang.IllegalArgumentException("input is outside prescribed range");
        }
    }

    public int numberOfOpenSites() { // number of open sites
        return openSiteCount;
    }

    public boolean percolates() { // does the system percolate?
        return quickUnion.connected(virtualTopSiteIndex, virtualBottomSiteIndex);
    }

    public static void main(String[] args) { // test client (optional)
    }
}
