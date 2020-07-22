package ai.envir_modeling.graph;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cartesian.coordinate.extent.CCRectangle;

public class JOptionPaneWorkspaceBoundary {
    protected JTextField xPointField = new JTextField();
    protected JTextField yPointField = new JTextField();
    protected JTextField widthField = new JTextField();
    protected JTextField heightField = new JTextField();
    
    Component parentComponent;

    Object[] message = {
        "Tọa độ x của điểm bắt đầu:", xPointField,
        "Tọa độ y của điểm bắt đầu:", yPointField,
        "Chiều rộng đường biên:", widthField,
        "Chiều cao đường biên: ", heightField,
    };

    public JOptionPaneWorkspaceBoundary(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    public int show(CCRectangle workspaceBoundary) {
        xPointField.setText(String.valueOf(workspaceBoundary.getX()));
        yPointField.setText(String.valueOf(workspaceBoundary.getY()));
        widthField.setText(String.valueOf(workspaceBoundary.getWidth()));
        heightField.setText(String.valueOf(workspaceBoundary.getHeight()));

        return JOptionPane.showConfirmDialog(
            this.parentComponent, 
            this.message, 
            "Thay đổi đường biên", 
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public CCRectangle getResult() {
        return new CCRectangle(
            Double.parseDouble(this.xPointField.getText()), 
            Double.parseDouble(this.yPointField.getText()),
            Double.parseDouble(this.widthField.getText()),
            Double.parseDouble(this.heightField.getText())
        );
    }
}