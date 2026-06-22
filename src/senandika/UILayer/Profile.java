package senandika.UILayer;

import Components.ProfileCard;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.ScrollPane;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
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
        setLocationRelativeTo(null);
        initUI();
        loadProfile();
    }
    
    private void initUI() {
        // 1. Matikan scrollbar horizontal secara permanen
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(content);
        jScrollPane1.getViewport().setBackground(new Color(246, 255, 248));
        jScrollPane1.setBorder(null);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // 2. Gunakan variabel 'content' bawaan
        content.setBackground(new Color(246, 255, 248));
        content.setOpaque(true);

        // 3. Atur Layout content menjadi GridBagLayout agar card otomatis berada di tengah
        content.setLayout(new GridBagLayout());
        content.removeAll();

        // 4. Perbesar PreferredSize vertikal agar menampung seluruh isi komponen tanpa terpotong
        content.setPreferredSize(new java.awt.Dimension(398, 720));

        // 5. Inisialisasi ke VARIABEL GLOBAL 'profileCard', jangan membuat variabel lokal baru!
        profileCard = new ProfileCard(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        content.add(profileCard, gbc);

        // 6. Sinkronisasi ulang hierarki layout Swing
        content.revalidate();
        content.repaint();

        // Jalankan pack() di akhir setelah semua komponen masuk ke dalam antrean window
        this.pack();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        content = new javax.swing.JPanel();
        navbar_panel = new javax.swing.JPanel();
        mood_nav = new javax.swing.JLabel();
        jurnal_nav = new javax.swing.JLabel();
        home_nav = new javax.swing.JLabel();
        navbar = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setBorder(null);

        content.setBackground(new java.awt.Color(246, 255, 248));
        content.setPreferredSize(new java.awt.Dimension(398, 550));
        content.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(content);

        navbar_panel.setBackground(new java.awt.Color(246, 255, 248));
        navbar_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mood_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mood_navMouseClicked(evt);
            }
        });
        navbar_panel.add(mood_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 60, 50));

        jurnal_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jurnal_navMouseClicked(evt);
            }
        });
        navbar_panel.add(jurnal_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 60, 50));

        home_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_navMouseClicked(evt);
            }
        });
        navbar_panel.add(home_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 60, 50));

        navbar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/component/navbar/nav-profile.png"))); // NOI18N
        navbar_panel.add(navbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(navbar_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navbar_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

            profileCard = new ProfileCard(this);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;

            gbc.anchor = GridBagConstraints.CENTER;

            content.add(profileCard, gbc);
        }

        try {
            ProfileService service = new ProfileService();
            String response = service.getProfile();

            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            JsonObject data = json.getAsJsonObject("data");

            // Ekstraksi data String dasar
            String email = data.get("email").getAsString();
            String username = data.get("full_name").getAsString();
            String fullName = data.get("username").getAsString();
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jurnal_nav;
    private javax.swing.JLabel mood_nav;
    private javax.swing.JLabel navbar;
    private javax.swing.JPanel navbar_panel;
    // End of variables declaration//GEN-END:variables
}
