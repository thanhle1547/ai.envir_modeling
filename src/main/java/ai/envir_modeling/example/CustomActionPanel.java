package ai.envir_modeling.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import cartesian.coordinate.CCExtentSystem;
import cartesian.coordinate.extent.CCExtentLine;

/**
 * TODO: Custom this class as you want
 * 
 * @implSpec You can't set the preferred size in this panel 'cause it will be
 *           calculate when this panel added to frame `CartesianFrame`
 * @implSpec From question <a href=
 *           "https://stackoverflow.com/questions/15317745/zero-width-height-jpanel-on-start"><b>Zero
 *           Width Height jPanel on start</b> on stackoverflow</a> 
 *           <br/>
 *           When you add the panel to a frame, and call pack() on the frame,
 *           that will calculate the correct (preferred) size and set it. Until
 *           then, you won't be able to find the size because it hasn't been
 *           calculated.
 */
public class CustomActionPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    CCExtentSystem cartesianPanel;
    JButton btnPerformSomething;
    JButton btnDrawLines;

    public CustomActionPanel() {
        cartesianPanel = CCExtentSystem.getInstance();
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

        setLayout(boxLayout);

        // TODO: Change the name & text of btnPerformSomething
        btnPerformSomething = new JButton("Thực hiện gì đó");
        btnDrawLines = new JButton("Vẽ các đường thẳng");

        btnPerformSomething.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDrawLines.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnPerformSomething.addActionListener(this);
        btnDrawLines.addActionListener(this);

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(Box.createVerticalGlue());
        add(new JSeparator());
        add(btnDrawLines);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(btnPerformSomething);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPerformSomething) {

        } else if (e.getSource() == btnDrawLines) {
            // Vẽ đường chéo, ngang, thẳng
            cartesianPanel.add(new CCExtentLine(3.0, 3.0, 15.0, 15.0));
            cartesianPanel.add(new CCExtentLine(2.0, 2.0, 10.0, 2.0));
            cartesianPanel.add(new CCExtentLine(8.0, 5.0, 8.0, 10.0));
            cartesianPanel.repaint();
        }
    }
    
}