package ai.envir_modeling.graph;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import cartesian.coordinate.extent.CCExtentPolygon;
import cartesian.coordinate.extent.CCRectangle;

public class JOptionPaneRandomObstacles {
    JPanel pInput;
    JPanel pProgress;
    JSpinner spNumberOfOstacles;
    JSpinner spMaxVertex;
    JProgressBar progressBar;

    Component parentComponent;
    CCRectangle workspaceBoundary;

    SpinnerModel numberOfOstaclesSpinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);
    SpinnerModel maxVertexSpinnerModel = new SpinnerNumberModel(3, 3, 9, 1);

    ArrayList<CCExtentPolygon> result;

    public JOptionPaneRandomObstacles(Component parentComponent, CCRectangle workspaceBoundary) {
        this.parentComponent = parentComponent;
        this.workspaceBoundary = workspaceBoundary;
    }

    public int show() {
        JPanel panel = new JPanel(new BorderLayout());
        pInput = createInputPanel();
        pProgress = createProgressPanel();

        panel.add(pInput, BorderLayout.CENTER);
        panel.add(pProgress, BorderLayout.PAGE_END);

        setJPanelEnable(pProgress, false);

        int choice = JOptionPane.showConfirmDialog(
                this.parentComponent, 
                panel, 
                "Tùy chọn tạo ngẫu nhiên vật cản", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null
        );

        if (choice == JOptionPane.OK_OPTION) {
            setJPanelEnable(pInput, false);
            setJPanelEnable(pProgress, true);
            progressBar.setIndeterminate(true);

            CompletableFuture<ArrayList<CCExtentPolygon>> future = CompletableFuture
                    .supplyAsync(() -> randomObstacles(
                            (int) spNumberOfOstacles.getValue(),
                            (int) spMaxVertex.getValue(), 
                            this.workspaceBoundary
                    ));

            try {
                this.result = future.get();
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }

            // this.result = randomObstacles(
            //         (int) spNumberOfOstacles.getValue(),
            //         (int) spMaxVertex.getValue(), 
            //         this.workspaceBoundary
            // );
        }

        return choice;
    }

    public ArrayList<CCExtentPolygon> getResult() {
        return this.result;
        // return randomObstacles(
        //         (int) spNumberOfOstacles.getValue(),
        //         (int) spMaxVertex.getValue(), 
        //         this.workspaceBoundary
        // );
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel lbNumberObstacles = new JLabel("Số lượng vật cản:"), lbMaxVertex = new JLabel("Số đỉnh tối đa:");

        panel.setLayout(new GridBagLayout());
        spNumberOfOstacles = new JSpinner(numberOfOstaclesSpinnerModel);
        spMaxVertex = new JSpinner(maxVertexSpinnerModel);

        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(lbNumberObstacles, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(lbMaxVertex, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 20, 5, 0);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(spNumberOfOstacles, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(spMaxVertex, constraints);

        return panel;
    }

    private JPanel createProgressPanel() {
        JPanel panel = new JPanel();
        progressBar = new JProgressBar();

        panel.add(progressBar);
        return panel;
    }

    private void setJPanelEnable(JPanel panel, boolean isEnable) {
        for (Component component : panel.getComponents()) {
            component.setEnabled(isEnable);
        }
    }

    /* private JOptionPane getOptionPane(JComponent parent) {
        JOptionPane pane = null;
        if (!(parent instanceof JOptionPane)) {
            pane = getOptionPane((JComponent) parent.getParent());
        } else {
            pane = (JOptionPane) parent;
        }
        return pane;
    } */

    private ArrayList<CCExtentPolygon> randomObstacles(
            int numberOfOstacles, 
            int maxVertex, 
            CCRectangle workspaceBoundary
    ){
        Random rd = new Random();
        final double minX   = workspaceBoundary.getX(),
                     minY   = workspaceBoundary.getY(),
                     maxX   = workspaceBoundary.getWidth(),
                     maxY   = workspaceBoundary.getHeight(),
                     deltaX = maxX - minX,
                     deltaY = maxY - minY;
        ArrayList<CCExtentPolygon> obs = new ArrayList<>();
        for (int i = 0; i < numberOfOstacles; i++) {
            // Java – Generate random integers in a range
            // https://mkyong.com/java/java-generate-random-integers-in-a-range/
            int numVertex = rd.nextInt(maxVertex - 3 + 1) + 3;
            
            // don't need
            // boolean isSatisfy = false;
            // while (isSatisfy) {
            //     // dk du ko t/m nhung van vuot qua ???
            //     if (numVertex >= 3 && numVertex <= maxVertex) {
            //         numVertex = rd.nextInt(10);
            //         isSatisfy = true;
            //     }
            // }

            Point2D[] points = new Point2D[numVertex];
            for (int j = 0; j < numVertex; j++) {
                // Generate a random double in a range
                // https://stackoverflow.com/a/3680648
                double x = minX + deltaX * rd.nextDouble(),
                       y = minY + deltaY * rd.nextDouble();
                points[j] = new Point2D.Double(x, y);

                // Are “while(true)” loops so bad? [closed]
                // https://stackoverflow.com/questions/6850380/are-whiletrue-loops-so-bad
                // while (true) {
                //     if (minX < x && x < maxX) {
                //         x = rd.nextDouble();
                //         continue;
                //     }
                //     if (minY < y && y < maxY) {
                //         y = rd.nextDouble();
                //         continue;
                //     }
                //     points[j] = new Point2D.Double(x, y);
                //     break;
                // }
            }
            obs.add(new CCExtentPolygon("#" + String.valueOf(i), points));
        }
        return obs;
    }
}