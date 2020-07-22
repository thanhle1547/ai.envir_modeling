package cartesian.coordinate.extent;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;

public class RectangleStyles {
    protected Paint paint;
    protected Color borderColor;
    protected Stroke stroke;

    public RectangleStyles(Paint paint, Color borderColor, Stroke stroke) {
        this.paint = paint;
        this.borderColor = borderColor;
        this.stroke = stroke;
    }
    
    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }
}