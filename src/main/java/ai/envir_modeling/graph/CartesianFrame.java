package ai.envir_modeling.graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
            btnExportToFile, 
            btnRandomObstacles, 
            btnClearAll,
            btnAddObstacle,
            btnEditObstacle,
            btnDeleteObstacle,
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
        btnRandomObstacles = new JButton("Tạo vật cản ngẫu nhiên");
        btnClearAll = new JButton("Xóa tất cả");
        // btnChangeStartEndPoints = new JButton("<html><center>Thay đổi điểm<br/>bắt đầu và kết thúc</center></html>");
        btnChangeStartEndPoints = new JButton("Thay đổi điểm bắt đầu và kết thúc");
        btnWorkspaceBoundary = new JButton("Thay đổi ranh giới");

        // components style
        btnRandomObstacles.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClearAll.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnChangeStartEndPoints.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnWorkspaceBoundary.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnClearAll.setToolTipText("Ngoại trừ đường biên, điểm bắt đầu và điểm kết thúc sẽ không bị xóa");

        customActionPanel.setPreferredSize(capPreferredSize);

        // events
        btnRandomObstacles.addActionListener(this);
        btnClearAll.addActionListener(this);
        btnChangeStartEndPoints.addActionListener(this);
        btnWorkspaceBoundary.addActionListener(this);

        panel.add(createImExportPanel());
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnRandomObstacles);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnClearAll);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JSeparator());

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createObstaclePanel());
        
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnChangeStartEndPoints);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnWorkspaceBoundary);
        
        panel.add(customActionPanel);
        
        return panel;
    }

    private JPanel createImExportPanel() {
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createTitledBorder("Nhập/Xuất file thông tin vật cản"));

        btnImportFromFile = new JButton("Nhập từ file");
        btnExportToFile = new JButton("Xuất ra file");

        btnImportFromFile.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExportToFile.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnImportFromFile.addActionListener(this);
        btnExportToFile.addActionListener(this);

        panel.add(btnImportFromFile);
        panel.add(btnExportToFile);

        return panel;
    }

    private JPanel createObstaclePanel() {
        JPanel  panel = new JPanel(new BorderLayout()),
                actionPanel = new JPanel();
        JLabel lbjListObstacleTip = new JLabel("Ctrl + Chuột trái vào item để bỏ chọn");
        BufferedImage btnIcon;
        obstacleListModel = new DefaultListModel<>();
        jListObstacle = new JList<>(obstacleListModel);
        btnAddObstacle = new JButton();
        btnEditObstacle = new JButton();
        btnDeleteObstacle = new JButton();

        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Danh sách các vật cản"));

        // btnAddObstacle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbjListObstacleTip.setHorizontalAlignment(SwingConstants.LEFT);
        lbjListObstacleTip.setForeground(Color.RED);

        btnAddObstacle.setToolTipText("Thêm vật cản");
        btnEditObstacle.setToolTipText("Sửa vật cản được chọn");
        btnDeleteObstacle.setToolTipText("Xóa vật cản được chọn");
        btnAddObstacle.setBackground(Color.WHITE);
        btnAddObstacle.setOpaque(true);
        btnEditObstacle.setBackground(Color.WHITE);
        btnEditObstacle.setOpaque(true);
        btnDeleteObstacle.setBackground(Color.WHITE);
        btnDeleteObstacle.setOpaque(true);

        // JList.VERTICAL_WRAP: which specifies that the data be displayed from top to
        // bottom (as usual) before wrapping to a new column.
        jListObstacle.setLayoutOrientation(JList.VERTICAL_WRAP);
        jListObstacle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        try {
            btnIcon = ImageIO.read(new FileInputStream("resources/icons8-plus-math-Fluent-16.png"));
            btnAddObstacle.setIcon(new ImageIcon(btnIcon));
            btnIcon = ImageIO.read(new FileInputStream("resources/icons8-design-Fluent-16.png"));
            btnEditObstacle.setIcon(new ImageIcon(btnIcon));
            btnIcon = ImageIO.read(new FileInputStream("resources/icons8-delete-Fluent-16.png"));
            btnDeleteObstacle.setIcon(new ImageIcon(btnIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnAddObstacle.addActionListener(this);
        btnEditObstacle.addActionListener(this);
        btnDeleteObstacle.addActionListener(this);
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

        actionPanel.add(btnAddObstacle);
        actionPanel.add(btnEditObstacle);
        actionPanel.add(btnDeleteObstacle);

        panel.add(lbjListObstacleTip, BorderLayout.PAGE_START);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JScrollPane(jListObstacle), BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.PAGE_END);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnImportFromFile) {
            JFileChooserImportObstacles dialog = new JFileChooserImportObstacles(this, obstacles);
            dialog.show();
            obstacles = dialog.getResult();
            drawObstacles(obstacles, cartesianPanel);
            showListObstacles(obstacles, obstacleListModel);
        } else if (source == btnExportToFile) {
            JFileChooserExportObstacles dialog = new JFileChooserExportObstacles(this, obstacles);
            dialog.show();
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
            obstacles.clear();
            cartesianPanel.clear();
            obstacleListModel.clear();
        } else if (source == btnAddObstacle) {
            JOptionPaneTableOfPoints dialog = new JOptionPaneTableOfPoints(this);
            if (dialog.show(new CCExtentPolygon("#" + String.valueOf(obstacles.size() + 1), new double[3], new double[3]),
                    ACTION.ADD) == JOptionPane.OK_OPTION) {
                CCExtentPolygon result = dialog.getResult();
                obstacles.add(result);
                cartesianPanel.add(result);
                obstacleListModel.add(obstacles.size() - 1, result.idToText());
                cartesianPanel.repaint();
            }
        } else if (source == btnEditObstacle) {
            JOptionPaneTableOfPoints dialog = new JOptionPaneTableOfPoints(this);
            int index = jListObstacle.getSelectedIndex();
            if (dialog.show(obstacles.get(index), ACTION.EDIT) == JOptionPane.OK_OPTION) {
                CCExtentPolygon result = dialog.getResult();
                obstacles.set(index, result);
                cartesianPanel.setPolygon(index, result);
                obstacleListModel.set(index, result.idToText());
                cartesianPanel.setPolygonHighlightState(index, true);
                cartesianPanel.repaint();
            }
        } else if (source == btnDeleteObstacle) {
            int[] list = jListObstacle.getSelectedIndices();
            if (list.length > 0) {
                for (int i = list.length - 1; i >= 0; i--) {
                    obstacles.remove(list[i]);
                    obstacleListModel.remove(list[i]);
                }
                cartesianPanel.setPolygonList(obstacles);
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