package cartesian.coordinate.extent;

import cartesian.coordinate.CCPoint;
import cartesian.coordinate.CCSystem;

/**
 * {@code CCExtentPoint} represents a point in a Cartesian coordinate system.
 *
 * @author thanhle1547
 * @see CCSystem
 */
public class CCExtentPoint extends CCPoint {
    protected ExtentPointStyles style;
    
    public CCExtentPoint(double x, double y, ExtentPointStyles style) {
        super(x, y);
        this.style = style;
    }
    
    public CCExtentPoint(CCExtentPoint point, ExtentPointStyles style) {
        super(point.getX(), point.getY());
        this.style = style;
    }
    
    /**
     * Create a point at given coordinates.
     * <p>
     * The point will be painted in black with a 1 pixel thick edge.
     * 
     * @param x
     *        x-coordinate for the location of the point.
     * @param y
     *        y-coordinate for the location of the point.
     */
    public CCExtentPoint(double x, double y) {
        super(x, y);
    }

    public ExtentPointStyles getStyle() {
        return style;
    }

    public void setStyle(ExtentPointStyles style) {
        this.style = style;
    }
}