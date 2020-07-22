package cartesian.coordinate.extent;

import java.awt.Paint;
import java.awt.Stroke;

public class ExtentPointStyles {
    private double outerRadius;
    private double innerRadius;
    private Stroke outerStroke;
    private Paint outerPaint;
    private Stroke innerStroke;
    private Paint innerPaint;

    private ExtentPointStyles(ExtentPointStylesBuilder builder) {
        this.outerRadius = builder.outerRadius;
        this.innerRadius = builder.innerRadius;
        this.outerStroke = builder.outerStroke;
        this.outerPaint = builder.outerPaint;
        this.innerStroke = builder.innerStroke;
        this.innerPaint = builder.innerPaint;
    }

    public double getOuterRadius() {
        return outerRadius;
    }

    public double getInnerRadius() {
        return innerRadius;
    }

    public Stroke getOuterStroke() {
        return outerStroke;
    }

    public Paint getOuterPaint() {
        return outerPaint;
    }

    public Stroke getInnerStroke() {
        return innerStroke;
    }

    public Paint getInnerPaint() {
        return innerPaint;
    }

    public static class ExtentPointStylesBuilder {
        private double outerRadius;
        private double innerRadius;
        private Stroke outerStroke;
        private Paint outerPaint;
        private Stroke innerStroke;
        private Paint innerPaint;

        public ExtentPointStylesBuilder() {
        }
        
        public ExtentPointStylesBuilder(double outerRadius, double innerRadius, Stroke outerStroke, Paint outerPaint,
                Stroke innerStroke, Paint innerPaint) {
            this.outerRadius = outerRadius;
            this.innerRadius = innerRadius;
            this.outerStroke = outerStroke;
            this.outerPaint = outerPaint;
            this.innerStroke = innerStroke;
            this.innerPaint = innerPaint;
        }
        
        public ExtentPointStylesBuilder withOuterRadius(double outerRadius) {
            this.outerRadius = outerRadius;
            return this;
        }

        public ExtentPointStylesBuilder withInnerRadius(double innerRadius) {
            this.innerRadius = innerRadius;
            return this;
        }

        public ExtentPointStylesBuilder withOuterStroke(Stroke outerStroke) {
            this.outerStroke = outerStroke;
            return this;
        }

        public ExtentPointStylesBuilder withOuterPaint(Paint outerPaint) {
            this.outerPaint = outerPaint;
            return this;
        }

        public ExtentPointStylesBuilder withInnerStroke(Stroke innerStroke) {
            this.innerStroke = innerStroke;
            return this;
        }

        public ExtentPointStylesBuilder withInnerPaint(Paint innerPaint) {
            this.innerPaint = innerPaint;
            return this;
        }
        
        public ExtentPointStyles build() {
            return new ExtentPointStyles(this);
        }
    }
}