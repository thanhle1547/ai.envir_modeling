package cartesian.coordinate.extent;

import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import cartesian.coordinate.CCPolygon;
import cartesian.coordinate.CCSystem;

/**
 * {@code CCExtentPolygon} represents a polygon in a Cartesian coordinate system, i.e.
 * a shape consisting of straight lines joined through points to form a circuit.
 *
 * @author thanhle1547
 * @see    CCSystem
 */
public class CCExtentPolygon extends CCPolygon {
    protected String id;
    
    /**
     * Create a polygon from a set of points. A polygon needs more than two
     * points.
     * 
     * @param xpoints
     *        x-coordinates for the points that form the polygon.
     * @param ypoints
     *        y-coordinates for the points that form the polygon.
     * @param paint
     *        {@code Paint} to paint the edges of the polygon with.
     * @param fill
     *        {@code Paint} to fill the interior of the polygon with.
     * @param stroke
     *        Draw the edges of the polygon with this {@code Stroke}.
     */
    public CCExtentPolygon(String id, double [] xpoints, double[] ypoints, 
                                    Paint paint, Paint fill, Stroke stroke) {
        super(xpoints, ypoints, paint, fill, stroke);
        this.id = id;
    }
    
    
    
    /**
     * Create a polygon from a set of points. A polygon needs more than two
     * points.
     * 
     * @param points
     *        Array of {@code Point2D} points that form the polygon.
     * @param paint
     *        {@code Paint} to paint the edges of the polygon with.
     * @param fill
     *        {@code Paint} to fill the interior of the polygon with.
     * @param stroke
     *        Draw the edges of the polygon with this {@code Stroke}.
     */
    public CCExtentPolygon(String id, Point2D[] points, Paint paint,
                                            Paint fill, Stroke stroke) {
        super(points, paint, fill, stroke);
        this.id = id;
    }
    
    
    
    /**
     * Create a polygon from a set of points. A polygon needs more than two
     * points.
     * <p>
     * The edges will be painted in black with a 1 pixel thick edge. The
     * interior of the polygon will be filled in pink.
     * 
     * @param xpoints
     *        x-coordinates for the points that form the polygon.
     * @param ypoints
     *        y-coordinates for the points that form the polygon.
     */
    public CCExtentPolygon(String id, double [] xpoints, double [] ypoints) {
        super(xpoints, ypoints);
        this.id = id;
    }
    
    
    
    /**
     * Create a polygon from a set of points. A polygon needs more than two
     * points.
     * <p>
     * The edges will be painted in black with a 1 pixel thick edge. The
     * interior of the polygon will be filled in pink.
     * 
     * @param points
     *        Array of {@code Point2D} points that form the polygon.
     */
    public CCExtentPolygon(String id, Point2D[] points) {
        super(points);
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
    
    /**
     * @param paint {@code Paint} to paint the edges of the polygon with.
     */
    public void setFill(Paint paint) {
        this.fill = paint;
    }
    
    public Object[][] to2dObject() {
        Object[][] result = new Object[xpoints.length][2];
        for (int i = 0; i < result.length; i++) {
            result[i][0] = this.xpoints[i];
            result[i][1] = this.ypoints[i];
        }
        return result;
    }
    
    public String idToText() {
        return "Vật cản: " + this.id.replace("#", "");
    }
}