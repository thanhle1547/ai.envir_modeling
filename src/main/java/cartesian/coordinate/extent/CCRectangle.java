package cartesian.coordinate.extent;

import cartesian.coordinate.CCExtentSystem;

/**
 * {@code CCRectangle} represents a rectangle in a Cartesian coordinate system.
 *
 * @author thanhle1547
 * @see CCExtentSystem
 */
public class CCRectangle {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected RectangleStyles styles;
    
    public CCRectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public CCRectangle(double x, double y, double width, double height, RectangleStyles styles) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.styles = styles;
    }
    
    public CCRectangle(CCRectangle rectangle, RectangleStyles styles) {
        this.x = rectangle.getX();
        this.y = rectangle.getY();
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
        this.styles = styles;
    }

    /**
     * @see {@link java.awt.Rectangle#isEmpty()}
     */
    public boolean isEmpty() {
        return (width <= 0) || (height <= 0);
    }

    /**
     * @see {@link java.awt.Rectangle#inside(int X, int Y)}
     */
    public boolean inside(CCExtentPoint point) {
        double X = point.getX();
        double Y = point.getY();
        double w = this.width;
        double h = this.height;
        if (w < 0 || h < 0) {
            // At least one of the dimensions is negative...
            return false;
        }
        // Note: if either dimension is zero, tests below must return false...
        double x = this.x;
        double y = this.y;
        if (X < x || Y < y) {
            return false;
        }
        w += x;
        h += y;
        // overflow || intersect
        return ((w < x || w > X) && (h < y || h > Y));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    
    public RectangleStyles getStyles() {
        return styles;
    }

    public void setStyles(RectangleStyles styles) {
        this.styles = styles;
    }

    public double getXCoordUpperLeft() {
        return this.x < 0.0 ? this.x + this.width : this.x;
    }
    
    public double getYCoordUpperLeft() {
        return this.y < 0.0 ? this.y : this.y + this.height;
    }

    public double[] getPolygonXPoints() {
        return new double[] { this.x, this.x, this.width, this.width };
    }

    public double[] getPolygonYPoints() {
        return new double[] { this.y, this.height, this.height, this.y };
    }
}