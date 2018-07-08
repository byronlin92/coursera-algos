import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

/**
 * @author byronlin
 *
 */
public class FastCollinearPoints {
    private List<LineSegment> list = new ArrayList<LineSegment>();
    

    public FastCollinearPoints(Point[] points) {
        
        //check input
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Arrays.sort(points);
        //check if point in array is empty or if there are duplicates
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            if (i+1 < points.length -1 && points[i] == points[i+1]) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        
        //make a copy of points array
        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < pointsCopy.length; i++) {
            pointsCopy[i] = points[i];
        }
        
        /**
         * currently this does not check for duplicates 
         */
        for (int p = 0; p < points.length; p++) {  
            Comparator<Point> order = points[p].slopeOrder();
            Arrays.sort(pointsCopy, order); //sort array based on p's slope           
            
            int base = 0;
            //iterate through sorted slope array and find 4 slope duplicates with respect to base
            for (int i = 1; i < pointsCopy.length - 2; i++) {
                if (pointsCopy[base].slopeTo(pointsCopy[i]) == pointsCopy[base].slopeTo(pointsCopy[i+1]) && pointsCopy[base].slopeTo(pointsCopy[i]) == pointsCopy[base].slopeTo(pointsCopy[i+2])) {
                    LineSegment seg = new LineSegment(pointsCopy[i], pointsCopy[i+2]);
                    list.add(seg);
                }
            }
            
        }
    
        
    }
    
    public int numberOfSegments() {
        return list.size();
    }
    
    public LineSegment[] segments() {
        LineSegment[] segments = list.toArray(new LineSegment[list.size()]);
        return Arrays.copyOf(segments, numberOfSegments());
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        
        LineSegment[] list = collinear.segments();
        
        for (int i = 0 ; i < list.length; i ++) {
            list[i].draw();
        }
        System.out.println("size: " + collinear.numberOfSegments());
        StdDraw.show();

    }

}
