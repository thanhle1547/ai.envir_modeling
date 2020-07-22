package ai.envir_modeling.example;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JPanel;
// import javax.swing.SwingUtilities;

import ai.envir_modeling.graph.CartesianFrame;

public class App 
{
    public static void main( String[] args )
    {
        // SwingUtilities.invokeLater()
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        // Internally SwingUtilities.invokeLater() calls EventQueue.invokeLater()
        EventQueue.invokeLater(new Runnable(){
		    @Override
		    public void run() {
		        JPanel panel = new CustomActionPanel();
		        Dimension preferredSize = CartesianFrame.getDefaultPanelDimension();
		        CartesianFrame frame = new CartesianFrame(panel, preferredSize);
		        frame.showUI();
		    }
		});
    }
}
