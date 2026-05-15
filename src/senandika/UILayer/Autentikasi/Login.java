/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package senandika.UILayer.Autentikasi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.swing.JOptionPane;
import senandika.ServiceLayer.AuthService;
import senandika.ServiceLayer.Session;
import senandika.UILayer.Home;
import senandika.UILayer.Personalisasi.Personalisasi1;

/**
 *
 * @author SAHABAT-IT
 */
public class Login extends javax.swing.JFrame {

    private boolean passwordVisible = false;
    
    public Login() {
        initComponents();
        setupPlaceholder();
        setupPasswordPlaceholder();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        Slide1 = new javax.swing.JPanel();
        inputEmail = new javax.swing.JTextField();
        inputPw = new javax.swing.JPasswordField();
        hintPw = new javax.swing.JButton();
        btnLogin = new javax.swing.JLabel();
        register = new javax.swing.JLabel();
        forgotPw1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Slide1.setBackground(new java.awt.Color(246, 255, 248));
        Slide1.setPreferredSize(new java.awt.Dimension(816, 546));
        Slide1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inputEmail.setBackground(new java.awt.Color(246, 255, 248));
        inputEmail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        inputEmail.setText("Masukkan Email");
        inputEmail.setBorder(null);
        inputEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputEmailActionPerformed(evt);
            }
        });
        Slide1.add(inputEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 260, 30));

        inputPw.setBackground(new java.awt.Color(246, 255, 248));
        inputPw.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        inputPw.setText("jPasswordField1");
        inputPw.setBorder(null);
        inputPw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputPwActionPerformed(evt);
            }
        });
        Slide1.add(inputPw, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 230, 30));

        hintPw.setBackground(new java.awt.Color(255, 255, 248));
        hintPw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/hintPassword.png"))); // NOI18N
        hintPw.setBorder(null);
        hintPw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hintPwActionPerformed(evt);
            }
        });
        Slide1.add(hintPw, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, -1, -1));

        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
        });
        Slide1.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 290, 50));

        register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerMouseClicked(evt);
            }
        });
        Slide1.add(register, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 80, 20));

        forgotPw1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forgotPw1MouseClicked(evt);
            }
        });
        Slide1.add(forgotPw1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 120, 20));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/Login.png"))); // NOI18N
        Slide1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 640));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Slide1, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Slide1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputEmailActionPerformed

    private void inputPwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputPwActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputPwActionPerformed

    private void hintPwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hintPwActionPerformed
        if (passwordVisible) {

            inputPw.setEchoChar('•');

            passwordVisible = false;

        } else {

            inputPw.setEchoChar((char) 0);

            passwordVisible = true;

        }
    }//GEN-LAST:event_hintPwActionPerformed

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked
        String email = inputEmail.getText();
        String password = new String (inputPw.getPassword());

        // VALIDASI INPUT
        if (email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Email tidak boleh kosong!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password tidak boleh kosong!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            // PESAN LOADING
            JOptionPane.showMessageDialog(
                    this,
                    "Sedang melakukan login...",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE
            );

            AuthService authService = new AuthService();

            String response =
                    authService.login(email, password);

            // PARSING JSON
            JsonObject jsonObject =
                    JsonParser.parseString(response)
                            .getAsJsonObject();

            boolean success =
                    jsonObject.get("success").getAsBoolean();

            String message =
                    jsonObject.get("message").getAsString();

            // LOGIN BERHASIL
            if (success) {

                String token =
                        jsonObject.get("token").getAsString();

                // SIMPAN TOKEN SESSION
                Session.TOKEN = token;

                JOptionPane.showMessageDialog(
                        this,
                        message,
                        "Login Berhasil",
                        JOptionPane.INFORMATION_MESSAGE
                );

                boolean profileCompleted =
                jsonObject
                .get("profile_completed")
                .getAsBoolean();
                if (!profileCompleted) {
                    Personalisasi1 personalization =
                            new Personalisasi1();
                    personalization.setVisible(true);

                    this.dispose();

                } else {

                    Home dashboard =
                            new Home();

                    dashboard.setVisible(true);

                    this.dispose();
                }
                Session.PROFILE_COMPLETED = profileCompleted;                
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        message,
                        "Login Gagal",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Terjadi kesalahan:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    }//GEN-LAST:event_btnLoginMouseClicked

    private void registerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseClicked
        Register signup = new Register();
        signup.setVisible(true);
        dispose();
    }//GEN-LAST:event_registerMouseClicked

    private void forgotPw1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgotPw1MouseClicked
    // INPUT EMAIL
    String email = JOptionPane.showInputDialog(
            this,
            "Masukkan email akun Anda",
            "Forgot Password",
            JOptionPane.PLAIN_MESSAGE
    );

    // JIKA CANCEL
    if (email == null) {

        return;
    }

    email = email.trim();

    // VALIDASI KOSONG
    if (email.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Email tidak boleh kosong!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    // VALIDASI FORMAT EMAIL
    if (!email.contains("@")) {

        JOptionPane.showMessageDialog(
                this,
                "Format email tidak valid!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    try {
        
        AuthService authService =
                new AuthService();
        
        String response =
        authService.forgotPassword(email);
    
        System.out.println(response);
        
        JsonObject jsonObject =
                JsonParser.parseString(response)
                        .getAsJsonObject();

        boolean success = false;

        if (jsonObject.has("success")
                && !jsonObject.get("success").isJsonNull()) {

            success = jsonObject
                    .get("success")
                    .getAsBoolean();
        }

        String message = "Terjadi kesalahan";

        if (jsonObject.has("message")
                && !jsonObject.get("message").isJsonNull()) {

            message = jsonObject
                    .get("message")
                    .getAsString();
        }
        
        // EMAIL VALID
        if (success) {        
            Session.RESET_EMAIL = email;

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Berhasil",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // PINDAH KE NEW PASSWORD
            ForgotPassword lupaPw = new ForgotPassword();
            lupaPw.setVisible(true);
            dispose();
        } else {

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Gagal",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
        
    }//GEN-LAST:event_forgotPw1MouseClicked

    private void setupPlaceholder() {
        // EMAIL
        inputEmail.setText("Masukkan email");

        inputEmail.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {

                if (inputEmail.getText().equals("Masukkan email")) {

                    inputEmail.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {

                if (inputEmail.getText().isEmpty()) {

                    inputEmail.setText("Masukkan email");
                }
            }
        });
    }
    
    private void setupPasswordPlaceholder() {
        inputPw.setText("Masukkan password");

        inputPw.setEchoChar((char) 0);

        inputPw.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {

                String password =
                        String.valueOf(inputPw.getPassword());

                if (password.equals("Masukkan password")) {

                    inputPw.setText("");

                    inputPw.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {

                String password =
                        String.valueOf(inputPw.getPassword());

                if (password.isEmpty()) {

                    inputPw.setText("Masukkan password");

                    inputPw.setEchoChar((char) 0);
                }
            }
        });
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Slide1;
    private javax.swing.JLabel btnLogin;
    private javax.swing.JLabel forgotPw1;
    private javax.swing.JButton hintPw;
    private javax.swing.JTextField inputEmail;
    private javax.swing.JPasswordField inputPw;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel register;
    // End of variables declaration//GEN-END:variables
}
