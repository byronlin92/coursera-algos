import edu.princeton.cs.algs4.MinPQ;
import java.util.Stack;

public class Solver {
    private MinPQ<SearchNode> initPQ;
    private MinPQ<SearchNode> twinPQ;
    private boolean solvable;
	private SearchNode finalBoard;
	
	private class SearchNode implements Comparable<SearchNode> {
		private SearchNode previous;
		private Board board;
		private int numMoves;
		
		public SearchNode(Board board, SearchNode previous, int numMoves) {
			this.board = board;
			this.previous = previous;
			this.numMoves = numMoves;	
		}
		
		public int compareTo(SearchNode other) {
			int thisManhattan = this.board.manhattan() + this.numMoves;
			int otherManhattan = other.board.manhattan() + other.numMoves;
			return thisManhattan - otherManhattan;
		}
	}
	 
	public Solver(Board initial) {  // find a solution to the initial board (using the A* algorithm)
		initPQ = new MinPQ<SearchNode>();
		twinPQ = new MinPQ<SearchNode>();
		initPQ.insert(new SearchNode(initial, null, 0));
	    twinPQ.insert(new SearchNode(initial.twin(), null, 0));
	    SearchNode initSN;
	    SearchNode twinSN;
	    
	    while (true) {
	        initSN = initPQ.delMin();
	        twinSN = twinPQ.delMin();
	        
	        if (initSN.board.isGoal()) {
	            finalBoard = initSN;
	            solvable = true;
	            break;
	        }
	        
	        if (twinSN.board.isGoal()) {
	            finalBoard = twinSN;
	            solvable = false;
	            break;
	        }
	        
	        for (Board initBoard : initSN.board.neighbors()) {
	            if (initSN.previous == null || !initBoard.equals(initSN.previous.board)) {
	                initPQ.insert(new SearchNode(initBoard, initSN, initSN.numMoves + 1));
	            }
	        }
        
           for (Board twinBoard : twinSN.board.neighbors()) {
                if (twinSN.previous == null || !twinBoard.equals(twinSN.previous.board)) {
                    twinPQ.insert(new SearchNode(twinBoard, twinSN, twinSN.numMoves + 1));
                }
            }

	    }

	}


	
	public boolean isSolvable() { // is the initial board solvable?
	    return solvable;
	}
	
	public int moves() { // min number of moves to solve initial board; -1 if unsolvable
	    if (this.solvable) {
	        return finalBoard.numMoves;
	    }
	    return -1;
	}
	
	public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if unsolvable
	    if (this.solvable) {
	        Stack<Board> s = new Stack<Board>();
	        SearchNode curr = finalBoard;
	        while (curr != null) {
	            s.push(curr.board);
	            curr = curr.previous;
	        }
	        return s;
	    }
	    return null;
	    
	}
	
//	public static void main(String[] args) {
//	}

}
