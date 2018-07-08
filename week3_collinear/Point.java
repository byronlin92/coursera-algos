/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        double yDiff = that.y - this.y;
        double xDiff = that.x - this.x;
        
        //equal 
        if (yDiff == 0 && xDiff == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        //horizontal 
        if (yDiff == 0 ) {
            return 0;
        }
        //vertical
        if (xDiff == 0) {
            return Double.POSITIVE_INFINITY;
        }
        
        return yDiff/xDiff;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (that.x == this.x & that.y == this.y) {
            return 0;
        }
        
        if (that.y == this.y) {
            if (that.x > this.x) {
                return -1;
            }
        }
        
        if (that.y > this.y) {
            return -1;
        }
        
        return 1;
    
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new Comparator<Point>() {

            @Override
            public int compare(Point p1, Point p2) {
                double cmp = p1.slopeTo(Point.this) - p2.slopeTo(Point.this);
                if (cmp < 0)
                    return -1;
                else if (cmp > 0)
                    return 1;
                else
                    return 0;  
            }
            
        };
        
        
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p1 = new Point(1,1);
        Point p2 = new Point(1,1);
        Point p3 = new Point(2,1);
        Point p4 = new Point(1,0);

        //TEST compareTo
        
        //point are same
        assert p1.compareTo(p2) == 0;
        
        // y are same, that.x > this.x
        assert p1.compareTo(p3) == -1;
        
        //that.y < this.y
        assert p4.compareTo(p1) == -1;

        //that.y > this.y
        assert p1.compareTo(p4) == 1;

        //TEST slopeTo
        
        //same points
        assert p1.slopeTo(p2) == Double.NEGATIVE_INFINITY;        
        
        //horizontal
        assert p2.slopeTo(p3) == 0.0;

        //vertical
        assert p1.slopeTo(p4) == Double.POSITIVE_INFINITY;

        //TEST compare
        
        Point[] points = new Point[4];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        points[3] = p4;

        Comparator<Point> p1order = p1.slopeOrder();
        Arrays.sort(points, p1order);
        
        for (int i =0 ; i < points.length; i++) {
            System.out.print(points[i]);
        }
        assert points[0] == p4;
        assert points[1] == p1;
        assert points[2] == p2;
        assert points[3] == p3;

    }
}