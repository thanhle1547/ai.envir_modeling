package ai.envir_modeling.graph;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.geom.Point2D;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import cartesian.coordinate.extent.CCExtentPolygon;

public class JOptionPaneTableOfPoints implements ActionListener {
    public static enum ACTION {
        ADD, EDIT
    };

    protected String obstacleId;

    JTable table;
    JButton btnAdd;
    JButton btnRemove;
    JButton btnMoveUp;
    JButton btnMoveDown;

    Component parentComponent;

    DefaultTableModel tableModel;

    String[] columnNames = { "Tọa độ x", "Tọa độ y" };

    public JOptionPaneTableOfPoints(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    public int show(CCExtentPolygon obstacle, ACTION action) {
        JPanel  panel = new JPanel(new BorderLayout()),
                bottomPanel= new JPanel();
        BoxLayout layout = new BoxLayout(bottomPanel, BoxLayout.X_AXIS);
        BufferedImage btnIcon;
        btnAdd = new JButton("Thêm 1 hàng");
        btnRemove = new JButton("Xóa (các) hàng");
        btnMoveUp = new JButton();
        btnMoveDown = new JButton();
        tableModel = new DefaultTableModel(obstacle.to2dObject(), columnNames);
        table = new JTable(tableModel);

        bottomPanel.setLayout(layout);
        bottomPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        table.setFillsViewportHeight(true);

        btnMoveUp.setToolTipText("Chuyển (các) hàng lên trên");
        btnMoveDown.setToolTipText("Chuyển (các) hàng xuống dưới");

        btnMoveUp.setBackground(Color.WHITE);
        btnMoveUp.setOpaque(true);
        btnMoveDown.setBackground(Color.WHITE);
        btnMoveDown.setOpaque(true);

        try {
            btnIcon = ImageIO.read(new FileInputStream("resources/icons8-up-Fluent-16.png"));
            btnMoveUp.setIcon(new ImageIcon(btnIcon));
            btnIcon = ImageIO.read(new FileInputStream("resources/icons8-down-arrow-Fluent-16.png"));
            btnMoveDown.setIcon(new ImageIcon(btnIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        btnAdd.addActionListener(this);
        btnRemove.addActionListener(this);
        btnMoveUp.addActionListener(this);
        btnMoveDown.addActionListener(this);

        obstacleId = obstacle.getId();

        bottomPanel.add(btnAdd);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPanel.add(btnRemove);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(btnMoveUp);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPanel.add(btnMoveDown);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.PAGE_END);

        return JOptionPane.showConfirmDialog(this.parentComponent, panel,
                action == ACTION.ADD ? "Thêm" : "Thay đổi" + " tọa độ các điểm của vật cản " + obstacleId,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    public CCExtentPolygon getResult() {
        Point2D[] points = new Point2D[table.getRowCount()];
        for (int i = 0; i < table.getRowCount(); i++) {
            points[i] = new Point2D.Double(Double.parseDouble(table.getValueAt(i, 0).toString()),
                    Double.parseDouble(table.getValueAt(i, 1).toString()));
        }
        return new CCExtentPolygon(obstacleId, points);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnAdd) {
            tableModel.addRow(new Object[] { 0.0, 0.0 });
        } else if (source == btnRemove) {
            int[] row = table.getSelectedRows();
            if (row.length > 0) {
                // xóa ngược từ cuối, vì sau khi xóa 1 hàng độ dài mảng sẽ bị thay đổi
                for (int i = row.length - 1 ; i >= 0  ; i--) {
                    tableModel.removeRow(row[i]);
                }
            }
            // 1 đa giác phải có ít nhất 3 đỉnh -> nếu số hàng sau khi xóa < 3 -> thêm hàng mới để ko bị lỗi
            // <= 2 vì tableModel sẽ tự tạo thêm 1 hàng nếu tất cả các hàng bị xóa
            for (int i = table.getRowCount(); i <= 2; i++) {
                tableModel.addRow(new Object[] { 0.0, 0.0 });
            }
        } else if (source == btnMoveUp) {
            if (table.getSelectedRow() != -1)
                moveRowBy(-1);
        } else if (source == btnMoveDown) {
            if (table.getSelectedRow() != -1)
                moveRowBy(1);
        }
    }

    /**
     * @see {@link https://stackoverflow.com/a/17597808 Moving a row in jTable}
     */
    private void moveRowBy(int by) {
        int[] rows = table.getSelectedRows();
        int destination = rows[0] + by;
        int rowCount = tableModel.getRowCount();

        if (destination < 0 || destination >= rowCount) {
            return;
        }

        tableModel.moveRow(rows[0], rows[rows.length - 1], destination);
        table.setRowSelectionInterval(rows[0] + by, rows[rows.length - 1] + by);
    }
}