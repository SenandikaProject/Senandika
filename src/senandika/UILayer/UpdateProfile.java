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

/**
 *
 * @author SAHABAT-IT
 */
public class UpdateProfile extends javax.swing.JFrame {
    private final String BASE_URL =
        "http://localhost:5000";
    private UpdateProfileCard card;
    
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
      
        content.setLayout(new GridBagLayout());

        content.removeAll();

        content.setLayout(
                new GridBagLayout()
        );
        
        GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;

            gbc.anchor = GridBagConstraints.CENTER;

        card = new UpdateProfileCard();
        content.add(card, gbc);

        content.revalidate();
        content.repaint();
        this.pack();
    }

    private void initEvents() {
        card.btnSimpan.addActionListener(e -> {
            btnSimpanActionPerformed();
        });

        card.btnBatal.addActionListener(e -> {
            btnBatalActionPerformed();
        });
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
