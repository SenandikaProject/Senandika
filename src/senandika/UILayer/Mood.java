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
public class Mood extends javax.swing.JFrame {
    private String idMood;
    private LocalDate tanggal;
    private int tingkatMood;
    private String catatan;
    private String userId;

    public Mood(String idMood, LocalDate tanggal, int tingkatMood,
                String catatan, String userId) {
        this.idMood = idMood;
        this.tanggal = tanggal;
        this.tingkatMood = tingkatMood;
        this.catatan = catatan;
        this.userId = userId;
    }

    public void saveMood() {
        System.out.println("Mood berhasil disimpan!");
    }

    public int getTingkatMood() {
        return tingkatMood;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public String getIdMood() {
        return idMood;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getUserId() {
        return userId;
    }
    
    public Mood() {
        initComponents();
        setLocationRelativeTo(null);
    }

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Slide1 = new javax.swing.JPanel();
        profil_nav = new javax.swing.JLabel();
        jurnal_nav = new javax.swing.JLabel();
        home_nav = new javax.swing.JLabel();
        navbar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Slide1.setBackground(new java.awt.Color(246, 255, 248));
        Slide1.setPreferredSize(new java.awt.Dimension(816, 546));
        Slide1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        profil_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profil_navMouseClicked(evt);
            }
        });
        Slide1.add(profil_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 570, 60, 50));

        jurnal_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jurnal_navMouseClicked(evt);
            }
        });
        Slide1.add(jurnal_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 570, 60, 50));

        home_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_navMouseClicked(evt);
            }
        });
        Slide1.add(home_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 570, 60, 50));

        navbar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/component/navbar/nav-mood.png"))); // NOI18N
        Slide1.add(navbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 538, -1, -1));

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

    private void profil_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profil_navMouseClicked
        Profile profil = new Profile();
        profil.setVisible(true);
        dispose();
    }//GEN-LAST:event_profil_navMouseClicked

    private void jurnal_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jurnal_navMouseClicked
        Journal jurnal = new Journal();
        jurnal.setVisible(true);
        dispose();
    }//GEN-LAST:event_jurnal_navMouseClicked

    private void home_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_navMouseClicked
        Home home = new Home();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_home_navMouseClicked

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
            java.util.logging.Logger.getLogger(Mood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mood().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Slide1;
    private javax.swing.JLabel home_nav;
    private javax.swing.JLabel jurnal_nav;
    private javax.swing.JLabel navbar;
    private javax.swing.JLabel profil_nav;
    // End of variables declaration//GEN-END:variables
}
