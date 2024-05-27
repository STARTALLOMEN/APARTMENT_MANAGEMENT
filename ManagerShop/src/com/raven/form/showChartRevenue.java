/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.form;

import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import com.raven.chartColumn.ModelChart;
import java.awt.Image;
import java.awt.Toolkit;


public class showChartRevenue extends javax.swing.JFrame {

    /**
     * Creates new form showChartRevenue
     */
    public showChartRevenue(DefaultTableModel tableShow) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("src\\com\\raven\\icon\\shop (6).png");
        this.setIconImage(icon);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(250, 250, 250));
        chart.addLegend("Tổng giá bán", new Color(135, 189, 245));
        chart.addLegend("Tổng giá chi", new Color(189, 135, 245));
        chart.addLegend("Tổng giá Nhập", new Color(245, 189, 135));
        chart.addLegend("Doanh thu", new Color(139, 229, 222));

        for (int j = 0; j < tableShow.getRowCount(); j++) {

            chart.addData(new ModelChart((int) tableShow.getValueAt(j, 0) + "",
                    new double[]{
                        //                        (int) tableShow.getValueAt(j, 1),
                        Float.parseFloat(fomartFloat((String) tableShow.getValueAt(j, 2))),
                        Float.parseFloat(fomartFloat((String) tableShow.getValueAt(j, 3))),
                        Float.parseFloat(fomartFloat((String) tableShow.getValueAt(j, 4))),
                        Float.parseFloat(fomartFloat((String) tableShow.getValueAt(j, 5)))

                    }));
        }
        chart.start();

    }

    public String deleteLastKey(String str) {
        if (str.charAt(str.length() - 1) == 'đ') {
            str = str.replace(str.substring(str.length() - 1), "");
            return str;
        } else {
            return str;
        }
    }

    public String fomartFloat(String txt) {
        String pattern = deleteLastKey(txt);
        return pattern = pattern.replaceAll(",", "");
    }

    public showChartRevenue() {
        initComponents();
    }

    public showChartRevenue(DefaultTableModel tableShow, String sql) {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(250, 250, 250));
        chart.addLegend("Sản phẩm", new Color(12, 84, 175));
        for (int j = 0; j < tableShow.getRowCount(); j++) {
            if (j == 10) {
                break;
            }
            chart.addData(new ModelChart((String) tableShow.getValueAt(j, 1), new double[]{(int) tableShow.getValueAt(j, 2)}));
        }
        chart.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chart = new com.raven.chartColumn.Chart();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(showChartRevenue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(showChartRevenue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(showChartRevenue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(showChartRevenue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new showChartRevenue().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.chartColumn.Chart chart;
    // End of variables declaration//GEN-END:variables
}
