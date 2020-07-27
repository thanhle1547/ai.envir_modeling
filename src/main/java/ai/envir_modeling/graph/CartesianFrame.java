package ai.envir_modeling.graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Paint;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import ai.envir_modeling.graph.JOptionPaneTableOfPoints.ACTION;
import cartesian.coordinate.CCExtentSystem;
import cartesian.coordinate.extent.CCExtentPoint;
import cartesian.coordinate.extent.CCExtentPolygon;
import cartesian.coordinate.extent.CCRectangle;
import cartesian.coordinate.extent.ExtentPointStyles;
import cartesian.coordinate.extent.RectangleStyles;

/**
 * Lớp con của Container
 * 
 * @see <a href="https://findoutcode.wordpress.com/2012/07/10/gioi-thieu-ve-jframe/">
 *          Giới thiệu về JFrame.
 *      </a>
 * @author thanhle1547
 */
public class CartesianFrame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    CCExtentSystem cartesianPanel;
    JPanel  controlPanel,
            customActionPanel;
    JButton btnImportFromFile, 
            btnRandomObstacles, 
            btnClearAll,
            btnAddObstacle,
            btnChangeStartEndPoints,
            btnWorkspaceBoundary;
    JList<String> jListObstacle;
    /**
     * customActionPanel preferred size
     */
    Dimension capPreferredSize;

    List<CCExtentPolygon> obstacles;

    DefaultListModel<String> obstacleListModel;
    Integer jListObstacleSelectedId = 0;

    ExtentPointStyles startPointStyles = new ExtentPointStyles.ExtentPointStylesBuilder()
            .withInnerRadius(4).withOuterRadius(8)
            .withInnerPaint(Color.BLACK).withInnerStroke(new BasicStroke(0.4f))
            .withOuterPaint((Paint) null).withOuterStroke(new BasicStroke(0.8f))
            .build();
    ExtentPointStyles endPointStyles = new ExtentPointStyles.ExtentPointStylesBuilder()
            .withInnerRadius(5).withOuterRadius(8)
            .withInnerPaint((Paint) null).withInnerStroke(new BasicStroke(0.4f))
            .withOuterPaint((Paint) null).withOuterStroke(new BasicStroke(0.8f))
            .build();
    RectangleStyles workspaceBoundaryStyles = 
            new RectangleStyles((Paint) null, Color.MAGENTA, new BasicStroke(5.0f));

    public CartesianFrame(JPanel customActionPanel, Dimension capPreferredSize) {
        this.customActionPanel = customActionPanel;
        this.capPreferredSize = capPreferredSize;

        cartesianPanel = CCExtentSystem.getInstance().setMinMax(0.0, 0.0, 10.0, 10.0);
        controlPanel = createControlPanel();

        obstacles = new ArrayList<>();

        cartesianPanel.setNiceGraphics(true);
        cartesianPanel.setStartPoint(new CCExtentPoint(0.0, 0.0, startPointStyles));
        cartesianPanel.setEndPoint(new CCExtentPoint(5.0, 5.0, endPointStyles));
        cartesianPanel.setWorkspaceBoundary(
            new CCRectangle(0.0, 0.0, 100.0, 100.0, workspaceBoundaryStyles));

        /**
         * [JavaSwing] BorderLayout
         * https://cachhoc.net/2014/02/26/javaswing-borderlayout/
         */
        setLayout(new BorderLayout());
        // thêm thành phần vào ContentPane
        add(controlPanel, BorderLayout.WEST);
        add(cartesianPanel, BorderLayout.CENTER);
    }

    public void showUI() {
        // Khi JFrame đã được nhìn thấy thì khó mà đóng lại được nếu không nhờ tới
        // phương thức setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cartesian");
        setSize(700, 700);
        // JFrame sau khi được tạo thì chúng ta sẽ không nhìn thấy được nó. Để nhìn được
        // nó chúng ta phải nhờ đến phương thức setVisible(boolean value)
        setVisible(true);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);
        panel.setBorder(
            new CompoundBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY),
            new EmptyBorder(10, 10, 10, 10) // padding
        ));

        // panel components
        btnImportFromFile = new JButton("Nhập từ file");
        btnRandomObstacles = new JButton("Tạo vật cản ngẫu nhiên");
        btnClearAll = new JButton("Xóa tất cả");
        btnAddObstacle = new JButton("Thêm 1 chướng ngại vật");
        // btnChangeStartEndPoints = new JButton("<html><center>Thay đổi điểm<br/>bắt đầu và kết thúc</center></html>");
        btnChangeStartEndPoints = new JButton("Thay đổi điểm bắt đầu và kết thúc");
        btnWorkspaceBoundary = new JButton("Thay đổi ranh giới");

        // components style
        btnImportFromFile.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRandomObstacles.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClearAll.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddObstacle.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnChangeStartEndPoints.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnWorkspaceBoundary.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnClearAll.setToolTipText("Ngoại trừ đường biên, điểm bắt đầu và điểm kết thúc sẽ không bị xóa");

        customActionPanel.setPreferredSize(capPreferredSize);

        // events
        btnImportFromFile.addActionListener(this);
        btnRandomObstacles.addActionListener(this);
        btnClearAll.addActionListener(this);
        btnAddObstacle.addActionListener(this);
        btnChangeStartEndPoints.addActionListener(this);
        btnWorkspaceBoundary.addActionListener(this);

        panel.add(btnImportFromFile);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnRandomObstacles);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnClearAll);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JSeparator());

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnAddObstacle);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createObstaclePanel());
        
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnChangeStartEndPoints);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnWorkspaceBoundary);
        
        panel.add(customActionPanel);
        
        return panel;
    }

    private JPanel createObstaclePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel lbjListObstacle = new JLabel("Danh sách các vật cản"),
                lbjListObstacleTip = new JLabel("Ctrl + Chuột trái vào item để bỏ chọn");
        obstacleListModel = new DefaultListModel<>();
        jListObstacle = new JList<>(obstacleListModel);

        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        lbjListObstacle.setHorizontalAlignment(SwingConstants.LEFT);
        lbjListObstacleTip.setHorizontalAlignment(SwingConstants.LEFT);
        lbjListObstacleTip.setForeground(Color.RED);

        // JList.VERTICAL_WRAP: which specifies that the data be displayed from top to
        // bottom (as usual) before wrapping to a new column.
        jListObstacle.setLayoutOrientation(JList.VERTICAL_WRAP);
        jListObstacle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // jListObstacle.setMinimumSize(new Dimension(15, 10));
        // jListObstacle.setPreferredSize(new Dimension(15, 10));
        // jListObstacle.setMaximumSize(new Dimension(15, 10));
        // jListObstacle.setVisibleRowCount(5);

        jListObstacle.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                /*
                 * old way too complicating try {
                 * cartesianPanel.setPolygonHighlightState(jListObstacleSelectedId, false); //
                 * "Vật cản: 1" -> index = 9 // 0123456789 Integer id =
                 * Integer.parseInt(jListObstacle.getSelectedValue().substring(9)) - 1;
                 * jListObstacleSelectedId = id; cartesianPanel.setPolygonHighlightState(id,
                 * true); } catch (Exception ex) { // if user deSelected the selected item
                 * cartesianPanel.setPolygonHighlightState(jListObstacleSelectedId, false); }
                 * finally { cartesianPanel.repaint(); }
                 */

                cartesianPanel.setPolygonHighlightState(jListObstacleSelectedId, false);
                Integer index = jListObstacle.getSelectedIndex();
                if (index >= 0) {
                    jListObstacleSelectedId = index;
                    cartesianPanel.setPolygonHighlightState(index, true);
                }
                cartesianPanel.repaint();
            }
        });

        panel.add(lbjListObstacle, BorderLayout.PAGE_START);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JScrollPane(jListObstacle), BorderLayout.CENTER);
        panel.add(lbjListObstacleTip, BorderLayout.PAGE_END);

        return panel;
    }

    private void drawObstacles(List<CCExtentPolygon> obstacleList, CCExtentSystem panel) {
        panel.clear();
        for (CCExtentPolygon obstacle : obstacleList) {
            panel.add(obstacle);
        }
        panel.repaint();
    }

    private void showListObstacles(List<CCExtentPolygon> obstacleList, DefaultListModel<String> listModel) {
        listModel.clear();
        listModel.addAll(obstacleList.stream().map(p -> p.idToText()).collect(Collectors.toList()));
    }

    private ArrayList<CCExtentPolygon> getObsatclesDataFrom(File file) throws FileNotFoundException {
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

    private ArrayList<CCExtentPolygon> importFileThenFetch() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File (*.txt)", "txt");
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
            try {
                return getObsatclesDataFrom(fileChooser.getSelectedFile());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to read file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnImportFromFile) {
            obstacles = importFileThenFetch();
            drawObstacles(obstacles, cartesianPanel);
            showListObstacles(obstacles, obstacleListModel);
        } else if (source == btnRandomObstacles) {
            if (cartesianPanel.getWorkspaceBoundary().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Ranh giới chưa được đặt", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPaneRandomObstacles dialog = new JOptionPaneRandomObstacles(
                    this, cartesianPanel.getWorkspaceBoundary()
            );
            if (dialog.show() == JOptionPane.OK_OPTION) {
                obstacles = dialog.getResult();
                drawObstacles(obstacles, cartesianPanel);
                showListObstacles(obstacles, obstacleListModel);
            }
        } else if (source == btnClearAll) {
            cartesianPanel.clear();
        } else if (source == btnAddObstacle) {
            JOptionPaneTableOfPoints dialog = new JOptionPaneTableOfPoints(this);
            if (dialog.show(new CCExtentPolygon("#" + String.valueOf(obstacles.size()), new double[3], new double[3]),
                    ACTION.ADD) == JOptionPane.OK_OPTION) {
                CCExtentPolygon result = dialog.getResult();
                obstacles.add(result);
                cartesianPanel.add(result);
                obstacleListModel.add(obstacles.size() - 1, result.idToText());
                cartesianPanel.repaint();
            }
        } else if (source == btnChangeStartEndPoints) {
            JOptionPaneSEPoints dialog = new JOptionPaneSEPoints(this);
            if (dialog.show(cartesianPanel.getStartPoint(), cartesianPanel.getEndPoint()) == JOptionPane.OK_OPTION) {
                CCRectangle workspaceBoundary = cartesianPanel.getWorkspaceBoundary();
                CCExtentPoint point = dialog.getStartPointResult();
                
                if (workspaceBoundary.inside(point))
                    cartesianPanel.setStartPoint(new CCExtentPoint(point, startPointStyles));
                else
                    JOptionPane.showMessageDialog(
                        this, "Điểm bắt đầu không nằm trong biên", "Error", JOptionPane.ERROR_MESSAGE);

                point = dialog.getEndPointResult();
                if (workspaceBoundary.inside(point))
                    cartesianPanel.setEndPoint(new CCExtentPoint(point, endPointStyles));
                else
                    JOptionPane.showMessageDialog(
                        this, "Điểm kết thúc không nằm trong biên", "Error", JOptionPane.ERROR_MESSAGE);

                cartesianPanel.repaint();
            }
        } else if (source == btnWorkspaceBoundary) {
            JOptionPaneWorkspaceBoundary dialog = new JOptionPaneWorkspaceBoundary(this);
            if (dialog.show(cartesianPanel.getWorkspaceBoundary()) == JOptionPane.OK_OPTION) {
                /* 
                CCRectangle workspaceBoundary = dialog.getResult();
                if (!workspaceBoundary.inside(cartesianPanel.getStartPoint()))
                    JOptionPane.showMessageDialog(this, "Điểm bắt đầu sẽ không nằm trong biên", "Error",
                            JOptionPane.ERROR_MESSAGE);
                if (!workspaceBoundary.inside(cartesianPanel.getEndPoint()))  
                    JOptionPane.showMessageDialog(this, "Điểm kết thúc sẽ không nằm trong biên", "Error",
                            JOptionPane.ERROR_MESSAGE); 
                */

                cartesianPanel.setWorkspaceBoundary(
                    new CCRectangle(dialog.getResult(), workspaceBoundaryStyles));
            }
        }
    }

    /**
     * The default height = 200
     */
    public static Dimension getDefaultPanelDimension() {
        return new Dimension(0, 200);
    }
}