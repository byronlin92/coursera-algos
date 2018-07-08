import java.util.LinkedList;

public class Board {
	private int[][] board;

	public Board(int[][] blocks) {
		board = copy(blocks);
	}

	public int dimension() {
		return board.length;
	}

	public int hamming() {
		int hamming = 0;
		for (int i = 0; i < dimension(); i++) {
			for (int j = 0; j < dimension(); j++) {
				if (board[i][j] != getCorrectValue(i,j)) {
					if (i == dimension() - 1 && j == dimension() - 1) break;  // don't count last record
					hamming++;
				}
			}
		}
		return hamming;
	}

	public int manhattan() {
		int manhattan = 0;
		for (int i = 0; i < dimension(); i++) {
			for (int j = 0; j < dimension(); j++) {
				if (board[i][j] != getCorrectValue(i,j) && board[i][j] != 0) {
					int value = board[i][j];
					int row = row(value);
					int col = col(value);
					int rowDiff = Math.abs(row - i);
					int colDiff = Math.abs(col - j);
					manhattan = manhattan + rowDiff + colDiff;
				}
			}
		}
		return manhattan;
	}

	public boolean isGoal() {
		for (int i = 0; i < dimension(); i++) {
			for (int j = 0; j < dimension(); j++) {
				if (board[i][j] != getCorrectValue(i, j)) {
					return false;
				}
			}
		}
		return true;
	}

	public Board twin() { 
	    int[][] newBoard = null;
		for (int i = 0; i < dimension(); i++) {
			for (int j = 0; j < board.length - 1; j++) {
				if (board[i][j] != 0 && board[i][j + 1] != 0) {
					newBoard = swap(i, j, i, j+1);
					break;
				}
			}
		}
		return new Board(newBoard);
	}

	public boolean equals(Object y) {
		if (y == this) {
			return true;
		}
		if (y == null || !(y instanceof Board) || ((Board) y).board.length != board.length) {
			return false;
		}
		for (int row = 0; row < dimension(); row++)
			for (int col = 0; col < dimension(); col++)
				if (((Board) y).board[row][col] != board[row][col])
					return false;
		return true;
	}

	public Iterable<Board> neighbors() {
		LinkedList<Board> list = new LinkedList<Board>();
		int[] coords = getSpaceLocation();
		int row = coords[0];
		int col = coords[1];
        if (row > 0)                    list.add(new Board(swap(row, col, row - 1, col)));        
        if (col < dimension() - 1)      list.add(new Board(swap(row, col, row, col+1)));
        if (row < dimension() - 1)      list.add(new Board(swap(row, col, row + 1, col)));
        if (col > 0)                    list.add(new Board(swap(row, col, row, col - 1)));
		return list;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(dimension() + "\n");
		for (int row = 0; row < dimension(); row++) {
			for (int col = 0; col < dimension(); col++)
				str.append(String.format("%2d ", board[row][col]));
			str.append("\n");
		}
		return str.toString();
	}

	// PRIVATE METHODS
	private int row(int value) {
		return (value - 1) / dimension();
	}

	private int col(int value) {
		return (value - 1) % dimension();
	}

	private int[][] swap(int row, int col, int newRow, int newCol) {
		int[][] copy = copy(board);
		int temp = copy[row][col];
		copy[row][col] = copy[newRow][newCol];
		copy[newRow][newCol] = temp;
		return copy;
	}

	private int getCorrectValue(int col, int row) {
	    if (col == dimension()-1 && row == dimension()-1) {
	        return 0;
	    }
		return col * dimension() + row + 1;
	}

	private int[] getSpaceLocation() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == 0) {
					int[] coordinates = new int[2];
					coordinates[0] = i;
					coordinates[1] = j;
					return coordinates;
				}
			}
		}
		throw new RuntimeException();
	}
	
    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                copy[row][col] = blocks[row][col];

        return copy;
    }

}
