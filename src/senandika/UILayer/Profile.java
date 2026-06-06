package senandika.UILayer;

import Components.ProfileCard;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import senandika.ServiceLayer.ProfileService;
import senandika.ServiceLayer.Session;
import senandika.UILayer.Autentikasi.Login;


public class Profile extends javax.swing.JFrame {
    private ProfileCard profileCard;
    private File selectedFile;
    private final String BASE_URL =
        "http://localhost:5000";
    public Profile() {
        initComponents();
        initUI();
        loadProfile();
    }
    private void initUI(){
        setTitle("Senandika");
        pack();
        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        JPanel content =
                new JPanel();

        content.setBackground(
            new Color(246,255,248)
                
        );
        
        content.revalidate();
        content.repaint();
        
        content.setOpaque(true);
        
        int lastY = 420;
        content.setPreferredSize(
                new java.awt.Dimension(
                        398,
                        lastY + 50
                )
        );
        
       
        content.setLayout(
                new org.netbeans.lib.awtextra.AbsoluteLayout()
        );
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        content = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        mood_nav = new javax.swing.JLabel();
        jurnal_nav = new javax.swing.JLabel();
        home_nav = new javax.swing.JLabel();
        navbar = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        content.setBackground(new java.awt.Color(246, 255, 248));
        content.setPreferredSize(new java.awt.Dimension(816, 546));
        content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(246, 255, 248));

        mood_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mood_navMouseClicked(evt);
            }
        });

        jurnal_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jurnal_navMouseClicked(evt);
            }
        });

        home_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_navMouseClicked(evt);
            }
        });

        navbar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/component/navbar/nav-profile.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(home_nav, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jurnal_nav, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(mood_nav, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(navbar)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(home_nav, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jurnal_nav, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mood_nav, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(navbar)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        content.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 533, -1, 110));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, 390, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void home_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_navMouseClicked
        Home home = new Home();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_home_navMouseClicked

    private void jurnal_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jurnal_navMouseClicked
        Journal jurnal = new Journal();
        jurnal.setVisible(true);
        dispose();
    }//GEN-LAST:event_jurnal_navMouseClicked

    private void mood_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mood_navMouseClicked
        Mood mood = new Mood();
        mood.setVisible(true);
        dispose();
    }//GEN-LAST:event_mood_navMouseClicked

    private void loadProfile() {
        // 1. Bersihkan panel dan tempelkan Komponen ProfileCard kustom kita
        if (profileCard == null) {
            // 'this' mengirimkan context Frame utama agar dialog/dispose bekerja sempurna
            profileCard = new ProfileCard(this); 

            
            content.add(profileCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 600));
            content.setComponentZOrder(profileCard, 1);
            if (jPanel1 != null) {
                content.setComponentZOrder(jPanel1, 0); // Navbar tetap di lapisan paling atas (front)
            }
        }

        try {
            ProfileService service = new ProfileService();
            String response = service.getProfile();

            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            JsonObject data = json.getAsJsonObject("data");

            // Ekstraksi data String dasar
            String email = data.get("email").getAsString();
            String username = data.get("username").getAsString();
            String fullName = data.get("full_name").getAsString();
            String gender = data.get("gender").getAsString();
            String favoriteActivity = data.get("favorite_activity").getAsString();

            // Pemetaan level stress dari int ke teks representasi ComboBox
            int stressLevel = data.get("stress_level").getAsInt();
            String stressText = "Pilih Salah Satu";
            switch (stressLevel) {
                case 1: stressText = "Sangat Santai"; break;
                case 2: stressText = "Santai"; break;
                case 3: stressText = "Normal"; break;
                case 4: stressText = "Stres"; break;
                case 5: stressText = "Sangat Stres"; break;
            }

            // 2. Jalankan fungsi set data ("Passing props") ke komponen kustom
            profileCard.setProfileData(email, username, fullName, gender, stressText, favoriteActivity);

            // 3. Muat gambar profil jika tersedia di server backend
            if (data.has("profile_picture") && !data.get("profile_picture").isJsonNull()) {
                String imagePath = data.get("profile_picture").getAsString();
                String imageUrl = BASE_URL + imagePath;
                URL url = new URL(imageUrl);

                profileCard.updateAvatar(url);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memproses Profile: " + e.getMessage());
            e.printStackTrace();
        }

        // Refresh canvas gambar
        content.revalidate();
        content.repaint();
    }
    
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
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Profile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.JLabel home_nav;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jurnal_nav;
    private javax.swing.JLabel mood_nav;
    private javax.swing.JLabel navbar;
    // End of variables declaration//GEN-END:variables
}
