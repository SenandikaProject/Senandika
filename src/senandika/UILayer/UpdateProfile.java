/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package senandika.UILayer;

import Components.UpdateProfileCard;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import senandika.ServiceLayer.ProfileService;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.io.File;
import java.net.URL;

/**
 *
 * @author SAHABAT-IT
 */
public class UpdateProfile extends javax.swing.JFrame {
    private final String BASE_URL =
        "http://localhost:5000";
    private UpdateProfileCard card;
    private File selectedPhotoFile;
    private JScrollPane scrollPane;
    
    public UpdateProfile() {
        initComponents();

        initUI();

        initEvents();

        loadProfile();

        setLocationRelativeTo(null);
    }

    private void initUI() {
        content.setBackground(
            new Color(246,255,248)
        );

        content.setOpaque(true);

        // Tinggi diperbesar untuk menampung blok preview foto + tombol + seluruh form
        int lastY = 820;
        content.setPreferredSize(
                new java.awt.Dimension(
                        398,
                        lastY
                )
        );

        content.setLayout(
                new GridBagLayout()
        );

        content.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        card = new UpdateProfileCard();
        content.add(card, gbc);

        // ===== TAMBAHAN: BUNGKUS CONTENT DENGAN SCROLLPANE =====
        scrollPane = new JScrollPane(content);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(246, 255, 248));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        getContentPane().removeAll();
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);
        // ===== AKHIR TAMBAHAN =====

        // Atur ukuran window yang muncul ke layar (lebih tinggi dari sebelumnya, tapi tetap dibatasi agar muat di layar)
        setSize(420, 720);

        content.revalidate();
        content.repaint();
    }

    private void initEvents() {
        card.btnSimpan.addActionListener(e -> {
            btnSimpanActionPerformed();
        });

        card.btnBatal.addActionListener(e -> {
            btnBatalActionPerformed();
        });
        
        card.btnUploadFoto.addActionListener(e -> {
            btnUploadFotoActionPerformed();
        });
    }
    
    private void btnUploadFotoActionPerformed() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "Gambar (jpg, jpeg, png)", "jpg", "jpeg", "png"
                )
        );

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedPhotoFile = chooser.getSelectedFile();
            try {
                java.awt.Image img = new ImageIcon(selectedPhotoFile.getAbsolutePath()).getImage();
                card.setPreviewImage(img);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal memuat preview: " + ex.getMessage());
            }
        }
    }
    
    private void btnSimpanActionPerformed() {
        try {

            String username =
                    card.inputUsername.getText();

            String fullName =
                    card.inputFullName.getText();

            String gender =
                    card.cbGender
                            .getSelectedItem()
                            .toString();

            String stressText =
                    card.cbStress
                            .getSelectedItem()
                            .toString();

            int stressLevel =
                    convertStressLevel(
                            stressText
                    );

            String activity =
                    card.cbActivity
                            .getSelectedItem()
                            .toString();

            ProfileService service =
                    new ProfileService();

            String response =
                service.updateProfile(
                        username,
                        fullName,
                        gender,
                        stressLevel,
                        activity
                );
            
            if (selectedPhotoFile != null) {
                String photoResponse = service.uploadProfilePhoto(selectedPhotoFile);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Profile berhasil diperbarui"
            );

            Profile profile =
                    new Profile();

            profile.setVisible(true);

            dispose();

            System.out.println(response);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
    
    private void btnBatalActionPerformed() {
        Profile profile =
                new Profile();

        profile.setVisible(true);

        dispose();
    }
    
    private void loadProfile() {

        try {

            ProfileService service =
                    new ProfileService();

            String response =
                    service.getProfile();

            JsonObject json =
                    JsonParser.parseString(response)
                            .getAsJsonObject();

            JsonObject data =
                    json.getAsJsonObject("data");

            card.inputUsername.setText(
                    data.get("username")
                            .getAsString()
            );

            card.inputFullName.setText(
                    data.get("full_name")
                            .getAsString()
            );

            card.cbGender.setSelectedItem(
                    data.get("gender")
                            .getAsString()
            );

            int stressLevel =
                    data.get("stress_level")
                            .getAsInt();

            switch (stressLevel) {

                case 1:
                    card.cbStress.setSelectedItem(
                            "Sangat Santai"
                    );
                    break;

                case 2:
                    card.cbStress.setSelectedItem(
                            "Santai"
                    );
                    break;

                case 3:
                    card.cbStress.setSelectedItem(
                            "Normal"
                    );
                    break;

                case 4:
                    card.cbStress.setSelectedItem(
                            "Stres"
                    );
                    break;

                case 5:
                    card.cbStress.setSelectedItem(
                            "Sangat Stres"
                    );
                    break;
            }

            card.cbActivity.setSelectedItem(
                    data.get("favorite_activity")
                            .getAsString()
            );
            
            if (data.has("profile_picture") && !data.get("profile_picture").isJsonNull()) {
                String imagePath = data.get("profile_picture").getAsString();
                URL photoUrl = new URL(BASE_URL + imagePath);
                card.setPreviewImage(photoUrl);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
    
    private int convertStressLevel(
        String level
    ) {

        switch (level) {

            case "Sangat Santai":
                return 1;

            case "Santai":
                return 2;

            case "Normal":
                return 3;

            case "Stres":
                return 4;

            case "Sangat Stres":
                return 5;

            default:
                return 3;
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        content.setBackground(new java.awt.Color(246, 255, 248));
        content.setPreferredSize(new java.awt.Dimension(816, 546));
        content.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(UpdateProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateProfile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    // End of variables declaration//GEN-END:variables
}
