package senandika.UILayer;

import Components.Journal_Component.JournalCard;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ScrollPaneConstants;

public class Journal extends javax.swing.JFrame {
    private JournalCard journalCard;

    public Journal() {

        initComponents();

        setLocationRelativeTo(null);

        initUI();

    }

    private void initUI() {
        // 1. Atur kebijakan scrollbar pada jScrollPane1 bawaan frame
        jScrollPane1.getViewport().setBackground(new Color(246, 255, 248));
        jScrollPane1.setBorder(null);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane1.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        jScrollPane1.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        // 2. Bersihkan content panel
        content.removeAll();
        content.setBackground(new Color(246,255,248));

        // 3. Ubah layout content menjadi BorderLayout agar komponen di dalamnya (jScrollPane1) otomatis full-screen
        content.setLayout(new BorderLayout());

        // 4. Inisialisasi JournalCard
        journalCard = new JournalCard(this);

        // 5. Masukkan journalCard langsung ke dalam viewport jScrollPane1
        jScrollPane1.setViewportView(journalCard);

        // 6. Masukkan kembali jScrollPane1 ke content panel (Center)
        content.add(jScrollPane1, BorderLayout.CENTER);

        // Load data dari API
        journalCard.loadData();

        content.revalidate();
        content.repaint();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        content = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        navbar_panel = new javax.swing.JPanel();
        mood_nav = new javax.swing.JLabel();
        profile_nav = new javax.swing.JLabel();
        home_nav = new javax.swing.JLabel();
        navbar = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        content.setBackground(new java.awt.Color(246, 255, 248));
        content.setPreferredSize(new java.awt.Dimension(398, 550));
        content.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setBackground(new java.awt.Color(246, 255, 248));
        jScrollPane1.setBorder(null);
        content.add(jScrollPane1, new java.awt.GridBagConstraints());

        navbar_panel.setBackground(new java.awt.Color(246, 255, 248));
        navbar_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mood_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mood_navMouseClicked(evt);
            }
        });
        navbar_panel.add(mood_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 60, 50));

        profile_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profile_navMouseClicked(evt);
            }
        });
        navbar_panel.add(profile_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 60, 50));

        home_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_navMouseClicked(evt);
            }
        });
        navbar_panel.add(home_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 60, 50));

        navbar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/component/navbar/nav-jurnal.png"))); // NOI18N
        navbar.setToolTipText("");
        navbar_panel.add(navbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navbar_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(content, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(navbar_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void home_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_navMouseClicked
        Home home = new Home();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_home_navMouseClicked

    private void profile_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profile_navMouseClicked
        Profile profil= new Profile();
        profil.setVisible(true);
        dispose();
    }//GEN-LAST:event_profile_navMouseClicked

    private void mood_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mood_navMouseClicked
        Mood mood = new Mood();
        mood.setVisible(true);
        dispose();
    }//GEN-LAST:event_mood_navMouseClicked

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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Journal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.JLabel home_nav;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mood_nav;
    private javax.swing.JLabel navbar;
    private javax.swing.JPanel navbar_panel;
    private javax.swing.JLabel profile_nav;
    // End of variables declaration//GEN-END:variables
}
