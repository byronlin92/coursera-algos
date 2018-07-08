import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

/**
 * @author byronlin
 *
 */
public class BruteCollinearPoints {
    private List<LineSegment> list = new ArrayList<LineSegment>();
    
    public BruteCollinearPoints(Point[] points) { 
        
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
        
        for (int i = 0; i < points.length - 3; i++) {  //p
            for (int j = i + 1; j < points.length -2; j++) {  //q
                for (int k = j + 1; k < points.length - 1; k++) {  //r
                    for (int l = k + 1; l < points.length; l++) {  //s
                        if ( points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&  points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                            LineSegment seg = new LineSegment(points[i], points[l]);
                            list.add(seg);
                        }
                    }
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
        
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        
        LineSegment[] list = collinear.segments();
        
        for (int i = 0 ; i < list.length; i ++) {
            list[i].draw();
        }
        System.out.println("size: " + collinear.numberOfSegments());
        StdDraw.show();

    }

}
