/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package senandika.UILayer;

import java.time.LocalDate;

/**
 *
 * @author SAHABAT-IT
 */
public class Journal extends javax.swing.JFrame {

    private String idJournal;
    private LocalDate tanggal;
    private String isi;
    private int streak;
    private String userId;

    public void saveJournal() {
        System.out.println("Journal berhasil disimpan!");
    }

    public int calculateStreak() {
        return streak;
    }

    public String getIsi() {
        return isi;
    }

    public String getIdJournal() {
        return idJournal;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public String getUserId() {
        return userId;
    }
    
    public Journal() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    public Journal(String idJournal, LocalDate tanggal,
                   String isi, int streak, String userId) {
        this.idJournal = idJournal;
        this.tanggal = tanggal;
        this.isi = isi;
        this.streak = streak;
        this.userId = userId;
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Slide1 = new javax.swing.JPanel();
        mood_nav = new javax.swing.JLabel();
        profil_nav = new javax.swing.JLabel();
        home_nav = new javax.swing.JLabel();
        navbar = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        txtEmail = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Slide1.setBackground(new java.awt.Color(246, 255, 248));
        Slide1.setPreferredSize(new java.awt.Dimension(816, 546));
        Slide1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mood_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mood_navMouseClicked(evt);
            }
        });
        Slide1.add(mood_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 570, 60, 50));

        profil_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profil_navMouseClicked(evt);
            }
        });
        Slide1.add(profil_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 570, 60, 50));

        home_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_navMouseClicked(evt);
            }
        });
        Slide1.add(home_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 570, 60, 50));

        navbar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/component/navbar/nav-jurnal.png"))); // NOI18N
        Slide1.add(navbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 538, -1, -1));

        btnLogout.setText("Tambah Data");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        Slide1.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 440, -1, -1));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.setText("HALAMAN JURNAL");
        Slide1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 100, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Slide1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Slide1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mood_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mood_navMouseClicked
        Mood mood = new Mood();
        mood.setVisible(true);
        dispose();
    }//GEN-LAST:event_mood_navMouseClicked

    private void profil_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profil_navMouseClicked
        Profile profil = new Profile();
        profil.setVisible(true);
        dispose();
    }//GEN-LAST:event_profil_navMouseClicked

    private void home_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_navMouseClicked
        Home home = new Home();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_home_navMouseClicked

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        CreateJournal createJurnal= new CreateJournal();
        createJurnal.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

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
            java.util.logging.Logger.getLogger(Journal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Journal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Journal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Journal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Journal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Slide1;
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel home_nav;
    private javax.swing.JLabel mood_nav;
    private javax.swing.JLabel navbar;
    private javax.swing.JLabel profil_nav;
    private javax.swing.JLabel txtEmail;
    // End of variables declaration//GEN-END:variables
}
