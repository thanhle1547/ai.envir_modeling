package ai.envir_modeling.graph;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cartesian.coordinate.extent.CCExtentPoint;

public class JOptionPaneSEPoints {
    protected JTextField xStartPointField = new JTextField();
    protected JTextField yStartPointField = new JTextField();
    protected JTextField xEndPointField = new JTextField();
    protected JTextField yEndPointField = new JTextField();
    
    Component parentComponent;

    Object[] message = {
        "Tọa độ x của điểm bắt đầu:", xStartPointField,
        "Tọa độ y của điểm bắt đầu:", yStartPointField,
        "Tọa độ x của điểm kết thúc:", xEndPointField,
        "Tọa độ y của điểm kết thúc:", yEndPointField,
    };

    public JOptionPaneSEPoints(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    public int show(CCExtentPoint startPoint, CCExtentPoint endPoint) {
        xStartPointField.setText(String.valueOf(startPoint.getX()));
        yStartPointField.setText(String.valueOf(startPoint.getY()));
        xEndPointField.setText(String.valueOf(endPoint.getX()));
        yEndPointField.setText(String.valueOf(endPoint.getY()));

        return JOptionPane.showConfirmDialog(
            this.parentComponent, 
            this.message, 
            "Thay đổi tọa độ của điểm bắt đầu và điểm kết thúc", 
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public CCExtentPoint getStartPointResult() {
        return new CCExtentPoint(
            Double.parseDouble(this.xStartPointField.getText()), 
            Double.parseDouble(this.yStartPointField.getText()));
    }
    
    public CCExtentPoint getEndPointResult() {
        return new CCExtentPoint(
            Double.parseDouble(this.xEndPointField.getText()), 
            Double.parseDouble(this.yEndPointField.getText()));
    }
}