import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private double result[];

	public PercolationStats(int n, int trials) { // perform trials independent experiments on an n-by-n grid
		if (n <= 0 || trials <= 0) {
			throw new java.lang.IllegalArgumentException();
		} else {
			result = new double[trials];
			for (int i = 0; i < trials; i++) {
				Percolation perc = new Percolation(n);
				while (perc.percolates() == false) {
					int randomRow = StdRandom.uniform(1, n + 1);
					int randomCol = StdRandom.uniform(1, n + 1);
					while (perc.isOpen(randomRow, randomCol) == true) { // only select closed sites to open
						randomRow = StdRandom.uniform(1, n + 1);
						randomCol = StdRandom.uniform(1, n + 1);
					}
					perc.open(randomRow, randomCol);
				}
				double openSites = perc.numberOfOpenSites();
				double fraction = openSites / (n * n);
				result[i] = fraction;
			}
		}
	}

	public double mean() { // sample mean of percolation threshold
		return StdStats.mean(result);
	}

	public double stddev() { // sample standard deviation of percolation threshold
		return StdStats.stddev(result);
	}

	public double confidenceLo() { // low endpoint of 95% confidence interval
		double mean = mean();
		double stddev = stddev();
		double confidenceLo = (mean - 1.960 * (stddev / Math.sqrt(result.length)));
		return confidenceLo;
	}

	public double confidenceHi() { // high endpoint of 95% confidence interval
		double mean = mean();
		double stddev = stddev();
		double confidenceLo = (mean + 1.960 * (stddev / Math.sqrt(result.length)));
		return confidenceLo;
	}

	public static void main(String[] args) { // test client (described below)
		int n = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats percStats = new PercolationStats(n, T);
		System.out.println("mean 			= " + percStats.mean());
		System.out.println("stddev 			= " + percStats.stddev());
		System.out.println(
				"95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
	}

}