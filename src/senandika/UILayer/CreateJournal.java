/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package senandika.UILayer;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import senandika.ServiceLayer.JournalService;

/**
 *
 * @author SAHABAT-IT
 */
public class CreateJournal extends javax.swing.JFrame {
    private File selectedFile;
    public CreateJournal() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Slide3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tambahJurnal = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        inputTitle = new javax.swing.JTextField();
        previewGambar = new javax.swing.JLabel();
        tambahGambar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        isiJurnal = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Slide3.setBackground(new java.awt.Color(246, 255, 248));
        Slide3.setPreferredSize(new java.awt.Dimension(816, 546));
        Slide3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tambah Jurnal");
        Slide3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, -1, -1));

        tambahJurnal.setText("Tambah Jurnal");
        tambahJurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahJurnalActionPerformed(evt);
            }
        });
        Slide3.add(tambahJurnal, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 530, -1, -1));

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
        Slide3.add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 530, -1, -1));

        inputTitle.setBackground(new java.awt.Color(246, 255, 248));
        inputTitle.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inputTitle.setBorder(javax.swing.BorderFactory.createTitledBorder("Judul Jurnal"));
        inputTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputTitleActionPerformed(evt);
            }
        });
        Slide3.add(inputTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 250, 50));

        previewGambar.setText("Preview Gambar");
        Slide3.add(previewGambar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 230, 110));

        tambahGambar.setText("Tambah Gambar");
        tambahGambar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahGambarActionPerformed(evt);
            }
        });
        Slide3.add(tambahGambar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, -1, -1));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Isi Jurnal"));

        isiJurnal.setBackground(new java.awt.Color(246, 255, 248));
        isiJurnal.setColumns(20);
        isiJurnal.setRows(5);
        jScrollPane2.setViewportView(isiJurnal);

        Slide3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, -1, -1));

        jLabel2.setText("Tambah Foto");
        Slide3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Slide3, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Slide3, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputTitleActionPerformed

    private void tambahJurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahJurnalActionPerformed
        try {

        String judul =
                inputTitle.getText();

        String isi =
                isiJurnal.getText();

        if (judul.isEmpty()
                || isi.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Semua field wajib diisi"
            );

            return;
        }

        JournalService service =
        new JournalService();

        service.createJournal(
                judul,
                isi,
                selectedFile
        );

        JOptionPane.showMessageDialog(
                this,
                "Jurnal berhasil disimpan"
        );

        inputTitle.setText("");
        isiJurnal.setText("");

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                e.getMessage()
        );
    }
    }//GEN-LAST:event_tambahJurnalActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        Journal jurnal = new Journal();
        jurnal.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void tambahGambarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahGambarActionPerformed
    JFileChooser chooser =
            new JFileChooser();
    
    chooser.setFileFilter(
        new FileNameExtensionFilter(
                "Image Files",
                "jpg",
                "jpeg",
                "png",
                "webp"
        )
    );
    int result =
            chooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {

        selectedFile =
                chooser.getSelectedFile();

        ImageIcon icon =
                new ImageIcon(
                        selectedFile
                        .getAbsolutePath()
                );

        Image image =
                icon.getImage()
                .getScaledInstance(

                        previewGambar.getWidth(),

                        previewGambar.getHeight(),

                        Image.SCALE_SMOOTH
                );

        previewGambar.setIcon(
                new ImageIcon(image)
        );
    }
    }//GEN-LAST:event_tambahGambarActionPerformed

    
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
            java.util.logging.Logger.getLogger(CreateJournal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateJournal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateJournal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateJournal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateJournal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Slide3;
    private javax.swing.JButton btnBatal;
    private javax.swing.JTextField inputTitle;
    private javax.swing.JTextArea isiJurnal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel previewGambar;
    private javax.swing.JButton tambahGambar;
    private javax.swing.JButton tambahJurnal;
    // End of variables declaration//GEN-END:variables
}
