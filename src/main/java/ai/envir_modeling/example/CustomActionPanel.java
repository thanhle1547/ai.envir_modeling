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

    public CustomActionPanel() {
        cartesianPanel = CCExtentSystem.getInstance();
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

        setLayout(boxLayout);

        // TODO: Change the name & text of btnPerformSomething
        btnPerformSomething = new JButton("Thực hiện gì đó");

        btnPerformSomething.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnPerformSomething.addActionListener(this);

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(Box.createVerticalGlue());
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JSeparator());
        add(btnPerformSomething);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPerformSomething) {

        }
    }
    
}