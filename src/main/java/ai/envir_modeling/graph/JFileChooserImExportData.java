package ai.envir_modeling.graph;

import java.awt.Component;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import cartesian.coordinate.extent.CCExtentPolygon;

abstract public class JFileChooserImExportData {
    static enum ACTION {
        IMPORT, EXPORT
    };

    JFileChooser fileChooser;
    FileNameExtensionFilter textFileFilter = new FileNameExtensionFilter("Text File (*.txt)", "txt");
    FileNameExtensionFilter javaDataFileFilter = new FileNameExtensionFilter("Java Data File (*.jdata)", "jdata");

    Component parentComponent;

    List<CCExtentPolygon> obstacles;

    private ACTION action;

    public JFileChooserImExportData(ACTION action, Component parentComponent, List<CCExtentPolygon> obstacles) {
        this.parentComponent = parentComponent;
        this.obstacles = obstacles;
        this.action = action;

        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(action == ACTION.IMPORT ? "Nhập file" : "Xuất file");
        if (action == ACTION.EXPORT)
            fileChooser.setSelectedFile(new File("Untitled.txt"));
        fileChooser.addChoosableFileFilter(textFileFilter);
        fileChooser.addChoosableFileFilter(javaDataFileFilter);
        fileChooser.setFileFilter(textFileFilter);
    }

    public void show() {
        int response = fileChooser.showSaveDialog(parentComponent);

        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (action == ACTION.EXPORT) {
                String fileName = selectedFile.getName();
                String path;
                int indexOfExtension = fileName.lastIndexOf('.');
                if (indexOfExtension == -1) {
                    path = fileChooser.getSelectedFile().getAbsolutePath() 
                        + "." 
                        + ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0];
                } else {
                    String extension = fileName.substring(indexOfExtension, fileName.length());
                    path = fileChooser.getSelectedFile().getAbsolutePath() 
                        + (!(extension == "txt" || extension == "jdata")
                            ? "." + ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0]
                            : ""
                        );
                }
                
                selectedFile = new File(path);
            }

            if (selectedFile == null) return;

            if (action == ACTION.EXPORT && selectedFile.exists()) {
                int result = JOptionPane.showConfirmDialog(
                        parentComponent, // nếu để là fileChooser -> ko hiển thị giữa form chính
                        "File đã tồn tại, ghi đè?", 
                        "File đã tồn tại",
                        JOptionPane.OK_CANCEL_OPTION
                );
                switch (result) {
                    case JOptionPane.CLOSED_OPTION:
                    case JOptionPane.CANCEL_OPTION:
                        return;
                    // case JOptionPane.OK_OPTION: // do nothing
                }
            }

            if (fileChooser.getFileFilter() == textFileFilter) {
                onSelectTextFile(selectedFile);
            } else if (fileChooser.getFileFilter() == javaDataFileFilter) {
                onSelectJavaDataFile(selectedFile);
            }
        }
    }

    abstract protected void onSelectTextFile(File selectedFile);

    abstract protected void onSelectJavaDataFile(File selectedFile);
}