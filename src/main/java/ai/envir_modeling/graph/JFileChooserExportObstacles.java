package ai.envir_modeling.graph;

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
// import java.util.StringJoiner;
import java.util.stream.Collectors;

import cartesian.coordinate.extent.CCExtentPolygon;

public class JFileChooserExportObstacles extends JFileChooserImExportData {

    public JFileChooserExportObstacles(Component parentComponent, List<CCExtentPolygon> obstacles) {
        super(ACTION.EXPORT, parentComponent, obstacles);
    }

    @Override
    protected void onSelectTextFile(File selectedFile) {
        try {
            PrintWriter writer = new PrintWriter(selectedFile);
            for (CCExtentPolygon ob : obstacles) {
                writer.println(ob.getId());
                // https://stackoverflow.com/a/49812753
                String coords = Arrays.stream(ob.to2dObject())
                        .map(point -> Arrays.stream(point)
                                            .map(String::valueOf)
                                            .collect(Collectors.joining(":")))
                        .collect(Collectors.joining(","));
                writer.println(coords);

                // OR https://stackoverflow.com/a/15619105
                // StringJoiner coordsJoiner = new StringJoiner(",");
                // for (Object[] coord : ob.to2dObject()) {
                // coordsJoiner.add(coord[0]);
                // coordsJoiner.add(":");
                // coordsJoiner.add(coord[1]);
                // }
                // writer.println(coordsJoiner.toString());
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // https://samderlust.com/dev-blog/java/write-read-arraylist-object-file-java#:~:text=Write%20an%20object%20into%20a%20file.&text=ObjectOutputStream%20writeStream%20%3D%20new%20ObjectOutputStream(writeData)%3A%20ObjectOutputStream%20will%20handle%20the,an%20object%20to%20a%20file.
    @Override
    protected void onSelectJavaDataFile(File selectedFile) {
        try {
            FileOutputStream writeData = new FileOutputStream(selectedFile);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(this.obstacles);
            writeStream.flush();
            writeStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}