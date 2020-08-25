package ai.envir_modeling.graph;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.JOptionPane;

import cartesian.coordinate.extent.CCExtentPolygon;

public class JFileChooserImportObstacles extends JFileChooserImExportData {

    public JFileChooserImportObstacles(Component parentComponent, List<CCExtentPolygon> obstacles) {
        super(ACTION.IMPORT, parentComponent, obstacles);
    }

    @Override
    protected void onSelectTextFile(File selectedFile) {
        try {
            this.obstacles = getDataFromTextFile(selectedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this.parentComponent, "Failed to read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<CCExtentPolygon> getResult() {
        return this.obstacles;
    }

    private ArrayList<CCExtentPolygon> getDataFromTextFile(File file) throws FileNotFoundException {
        ArrayList<CCExtentPolygon> obstacleList = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        scanner.useLocale(Locale.US);
        scanner.useDelimiter("\\s*");

        /**
         * Data example: 1#52.0:41.0,72.0:35.5 2#23.0:62.0 ...
         */
        while (scanner.hasNext()) {
            /**
             * obstacle[0] = "" obstacle[1] = "52.0:41.0,72.0:35.5"
             * 
             * strPoints[0] = "52.0:41.0" strPoints[1] = "72.0:35.5"
             */
            String id = scanner.nextLine();
            String[] strPoints = scanner.nextLine().split(",\\n*"); // scanner.skip("\\d+#\\n*")
            List<Double> xpoints = new ArrayList<>(), ypoints = new ArrayList<>();

            for (int i = 0; i < strPoints.length; i++) {
                String[] points = strPoints[i].split(":");

                xpoints.add(Double.parseDouble(points[0]));
                ypoints.add(Double.parseDouble(points[1]));
            }

            obstacleList.add(new CCExtentPolygon(id, xpoints.stream().mapToDouble(d -> d).toArray(),
                    ypoints.stream().mapToDouble(d -> d).toArray()));
        }

        scanner.close();
        return obstacleList;
    }
}