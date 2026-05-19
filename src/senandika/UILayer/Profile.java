package senandika.UILayer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import senandika.ServiceLayer.ProfileService;
import senandika.ServiceLayer.Session;
import senandika.UILayer.Autentikasi.Login;


public class Profile extends javax.swing.JFrame {

    private File selectedFile;
    private final String BASE_URL =
        "http://localhost:5000";
    public Profile() {
        initComponents();
        setLocationRelativeTo(null);
        loadProfile();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        Slide1 = new javax.swing.JPanel();
        mood_nav = new javax.swing.JLabel();
        txtUsername = new javax.swing.JLabel();
        txtFullName = new javax.swing.JLabel();
        jurnal_nav = new javax.swing.JLabel();
        home_nav = new javax.swing.JLabel();
        navbar = new javax.swing.JLabel();
        cbGender = new javax.swing.JComboBox<>();
        cbStress = new javax.swing.JComboBox<>();
        cbActivity = new javax.swing.JComboBox<>();
        profileImg = new javax.swing.JLabel();
        btnChooseFoto = new javax.swing.JButton();
        btnSaveFoto = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnUpdateProfile = new javax.swing.JButton();
        txtEmail = new javax.swing.JLabel();

        jButton1.setText("jButton1");

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

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsername.setText("Username");
        Slide1.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        txtFullName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFullName.setText("Full Name");
        Slide1.add(txtFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

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

        navbar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/component/navbar/nav-profile.png"))); // NOI18N
        Slide1.add(navbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, -1, -1));

        cbGender.setBackground(new java.awt.Color(246, 255, 248));
        cbGender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Salah Satu", "Laki-Laki", "Perempuan", " " }));
        cbGender.setBorder(null);
        cbGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGenderActionPerformed(evt);
            }
        });
        Slide1.add(cbGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 160, 20));

        cbStress.setBackground(new java.awt.Color(246, 255, 248));
        cbStress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbStress.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Salah Satu", "Sangat Santai", "Santai", "Normal", "Stres", "Sangat Stres", " " }));
        cbStress.setBorder(null);
        cbStress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStressActionPerformed(evt);
            }
        });
        Slide1.add(cbStress, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 160, 20));

        cbActivity.setBackground(new java.awt.Color(246, 255, 248));
        cbActivity.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbActivity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Salah Satu", "Mendengarkan musik", "Olahraga", "Menonton film", "Bermain game", "Membaca buku", "Meditasi", "Jalan-jalan", " " }));
        cbActivity.setBorder(null);
        cbActivity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbActivityActionPerformed(evt);
            }
        });
        Slide1.add(cbActivity, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 160, 20));

        profileImg.setPreferredSize(new java.awt.Dimension(75, 75));
        Slide1.add(profileImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, -1, -1));

        btnChooseFoto.setText("Upload Foto");
        btnChooseFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFotoActionPerformed(evt);
            }
        });
        Slide1.add(btnChooseFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, -1, -1));

        btnSaveFoto.setText("Simpan Foto");
        btnSaveFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveFotoActionPerformed(evt);
            }
        });
        Slide1.add(btnSaveFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, -1, -1));

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        Slide1.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 440, -1, -1));

        btnUpdateProfile.setText("Update Profile");
        btnUpdateProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateProfileActionPerformed(evt);
            }
        });
        Slide1.add(btnUpdateProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 440, -1, -1));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.setText("Email");
        Slide1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 100, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Slide1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, Short.MAX_VALUE)
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

    private void home_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_navMouseClicked
        Home home = new Home();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_home_navMouseClicked

    private void mood_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mood_navMouseClicked
        Mood mood = new Mood();
        mood.setVisible(true);
        dispose();
    }//GEN-LAST:event_mood_navMouseClicked

    private void jurnal_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jurnal_navMouseClicked
        Journal jurnal = new Journal();
        jurnal.setVisible(true);
        dispose();   
    }//GEN-LAST:event_jurnal_navMouseClicked

    private void cbGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGenderActionPerformed

    private void cbStressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStressActionPerformed

    private void cbActivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbActivityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbActivityActionPerformed

    private void btnChooseFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseFotoActionPerformed
        JFileChooser chooser =
            new JFileChooser();

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

                        profileImg.getWidth(),

                        profileImg.getHeight(),

                        Image.SCALE_SMOOTH
                );

        profileImg.setIcon(
                new ImageIcon(image)
        );
    }
    }//GEN-LAST:event_btnChooseFotoActionPerformed

    private void btnSaveFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveFotoActionPerformed
        try {

            if (selectedFile == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Pilih gambar terlebih dahulu"
                );

                return;
            }

            ProfileService service =
                    new ProfileService();

            String response =
                    service.uploadProfilePhoto(
                            selectedFile
                    );

            System.out.println(response);

            JOptionPane.showMessageDialog(
                    this,
                    "Foto profile berhasil diupload"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }//GEN-LAST:event_btnSaveFotoActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int confirm =
            JOptionPane.showConfirmDialog(
                    this,
                    "Yakin ingin logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

        if (confirm == JOptionPane.YES_OPTION) {

            Session.TOKEN = "";

            Login login =
                    new Login();

            login.setVisible(true);

            this.dispose();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnUpdateProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateProfileActionPerformed
        UpdateProfile update = new UpdateProfile();
        update.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnUpdateProfileActionPerformed

    private void loadProfile() {
        cbGender.setEnabled(false);
        cbStress.setEnabled(false);
        cbActivity.setEnabled(false);
        
        try {
            ProfileService service =
                    new ProfileService();

            String response =
                    service.getProfile();

            JsonObject json =
                    JsonParser
                    .parseString(response)
                    .getAsJsonObject();

            JsonObject data =
                    json.getAsJsonObject("data");
            
            txtEmail.setText(
                    data.get("email")
                    .getAsString()
            );
            
            txtUsername.setText(
                    data.get("username")
                    .getAsString()
            );

            txtFullName.setText(
                    data.get("full_name")
                    .getAsString()
            );

            cbGender.setSelectedItem(
                    data.get("gender")
                    .getAsString()
            );

            int stressLevel =
                    data.get("stress_level")
                    .getAsInt();

            switch (stressLevel) {

                case 1:
                    cbStress.setSelectedItem(
                            "Sangat Santai"
                    );
                    break;

                case 2:
                    cbStress.setSelectedItem(
                            "Santai"
                    );
                    break;

                case 3:
                    cbStress.setSelectedItem(
                            "Normal"
                    );
                    break;

                case 4:
                    cbStress.setSelectedItem(
                            "Stres"
                    );
                    break;

                case 5:
                    cbStress.setSelectedItem(
                            "Sangat Stres"
                    );
                    break;
            }

            cbActivity.setSelectedItem(
                    data.get("favorite_activity")
                    .getAsString()
            );

            // =====================
            // LOAD PROFILE IMAGE
            // =====================

            if (
                data.has("profile_picture")
                &&
                !data.get("profile_picture")
                .isJsonNull()
            ) {

                    String imagePath =
                            data.get("profile_picture")
                            .getAsString();

                    String imageUrl =
                            BASE_URL + imagePath;

                    URL url =
                            new URL(imageUrl);

                    ImageIcon icon =
                            new ImageIcon(url);

                    Image image =
                            icon.getImage()
                            .getScaledInstance(

                                    profileImg.getWidth(),

                                    profileImg.getHeight(),

                                    Image.SCALE_SMOOTH
                            );

                    profileImg.setIcon(
                            new ImageIcon(image)
                    );
                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        this,
                        e.getMessage()
                );
            }
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
    private javax.swing.JPanel Slide1;
    private javax.swing.JButton btnChooseFoto;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSaveFoto;
    private javax.swing.JButton btnUpdateProfile;
    private javax.swing.JComboBox<String> cbActivity;
    private javax.swing.JComboBox<String> cbGender;
    private javax.swing.JComboBox<String> cbStress;
    private javax.swing.JLabel home_nav;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jurnal_nav;
    private javax.swing.JLabel mood_nav;
    private javax.swing.JLabel navbar;
    private javax.swing.JLabel profileImg;
    private javax.swing.JLabel txtEmail;
    private javax.swing.JLabel txtFullName;
    private javax.swing.JLabel txtUsername;
    // End of variables declaration//GEN-END:variables
}
