package Components;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import senandika.FontManager;
import senandika.ServiceLayer.ProfileService;
import senandika.ServiceLayer.Session;
import senandika.UILayer.Autentikasi.Login;
import senandika.UILayer.UpdateProfile;

public class ProfileCard extends JPanel {
    // Komponen UI Atas
    private JLabel profileImg;
    private JLabel lblEmailSub;
    
    // Komponen UI Form Data Pengguna
    private JTextField txtUsername;
    private JTextField txtFullName;
    private JComboBox<String> cbGender;
    private JComboBox<String> cbStress;
    private JComboBox<String> cbActivity;
    
    // Komponen Tombol
    private RoundedButton btnChooseFoto;
    private RoundedButton btnSaveFoto;
    private RoundedButton BtnLogout;
    private RoundedButton BtnUpdateProfile;
    // State Internal Gambar
    private File selectedFile;
    private final JFrame parentFrame; // Dibutuhkan untuk context dispose/dialog

    public ProfileCard(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setSize(398, 360); // Menyesuaikan tinggi dengan penambahan komponen form
        setOpaque(false);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        initComponents();
        initActionListeners();
    }

    private void initComponents() {
        // --- BAGIAN ATAS: PROFIL & GAMBAR ---
        profileImg = new JLabel();
        profileImg.setHorizontalAlignment(SwingConstants.CENTER);
        profileImg.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        add(profileImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 100, 100));

        lblEmailSub = new JLabel("email@domain.com");
        lblEmailSub.setFont(new Font("Poppins", Font.PLAIN, 13));
        lblEmailSub.setForeground(new Color(71, 85, 105));
        lblEmailSub.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblEmailSub, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 320, 20));

        btnChooseFoto = new RoundedButton();
        btnChooseFoto.setText("Upload Foto");
        ((RoundedButton) btnChooseFoto).setCornerRadius(8);
        btnChooseFoto.setFocusPainted(false);
        btnChooseFoto.setBackground(new Color(217,217,217));
        add(btnChooseFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 160, 110, 30));

        btnSaveFoto = new RoundedButton();
        btnSaveFoto.setText("Simpan Foto");
        ((RoundedButton) btnSaveFoto).setCornerRadius(8);
        btnSaveFoto.setFocusPainted(false);
        btnSaveFoto.setBackground(new Color(137,126,255));
        btnSaveFoto.setForeground(Color.WHITE);
        add(btnSaveFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 160, 110, 30));

        // --- BAGIAN TENGAH: FORM DATA PENGGUNA ---
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(FontManager.getPoppins(11f));
        add(lblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 205, 260, 15));

        txtUsername = new JTextField();
        txtUsername.setEditable(false);
        txtUsername.setBackground(new Color(237,238,255));
        add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 225, 260, 30));

        JLabel lblName = new JLabel("Full Name");
        lblName.setFont(FontManager.getPoppins(11f));
        add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 260, 15));

        txtFullName = new JTextField();
        txtFullName.setEditable(false);
        txtFullName.setBackground(new Color(237,238,255));
        add(txtFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 260, 30));

        cbGender = new JComboBox<>(new String[]{
            "Pilih Salah Satu",
            "Laki-laki",
            "Perempuan"
        });
        cbGender.setEnabled(false);
        cbGender.setBackground(new Color(237,238,255));
        add(cbGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 260, 30));

        cbStress = new JComboBox<>(new String[]{
            "Pilih Salah Satu",
            "Sangat Santai",
            "Santai",
            "Normal",
            "Stres",
            "Sangat Stres"
        });
        cbStress.setEnabled(false);
        cbStress.setBackground(new Color(237,238,255));
        add(cbStress, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 260, 30));

        cbActivity = new JComboBox<>(new String[]{
            "Pilih Salah Satu",
            "Mendengarkan musik",
            "Olahraga",
            "Menonton film",
            "Bermain game",
            "Membaca buku",
            "Meditasi",
            "Jalan-jalan"
        });
        cbActivity.setEnabled(false);
        cbActivity.setBackground(new Color(237,238,255));
        add(cbActivity, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, 260, 30));

        // --- BAGIAN BAWAH: TOMBOL AKSI ---
        BtnLogout = new RoundedButton();
        BtnLogout.setText("Logout");
        BtnLogout.setBackground(new Color(239, 68, 68));
        ((RoundedButton) BtnLogout).setCornerRadius(8);
        BtnLogout.setForeground(Color.WHITE);
        BtnLogout.setFocusPainted(false);
        add(BtnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 460, 100, 35));

        BtnUpdateProfile = new RoundedButton();
        BtnUpdateProfile.setText("Update Profil");
        BtnUpdateProfile.setBackground(new Color(59, 130, 246));
        ((RoundedButton) BtnUpdateProfile).setCornerRadius(8);
        BtnUpdateProfile.setForeground(Color.WHITE);
        BtnUpdateProfile.setFocusPainted(false);
        add(BtnUpdateProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 460, 120, 35));
    }

    private void initActionListeners() {
        // Event Pilih Gambar
        btnChooseFoto.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "webp"));
            int result = chooser.showOpenDialog(parentFrame);

            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                Image image = icon.getImage().getScaledInstance(
                        profileImg.getWidth() > 0 ? profileImg.getWidth() : 100,
                        profileImg.getHeight() > 0 ? profileImg.getHeight() : 100,
                        Image.SCALE_SMOOTH
                );
                profileImg.setIcon(new ImageIcon(image));
            }
        });

        // Event Simpan Foto ke API
        btnSaveFoto.addActionListener(e -> {
            try {
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(parentFrame, "Pilih gambar terlebih dahulu");
                    return;
                }
                ProfileService service = new ProfileService();
                String response = service.uploadProfilePhoto(selectedFile);
                System.out.println(response);
                JOptionPane.showMessageDialog(parentFrame, "Foto profile berhasil diupload");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame, ex.getMessage());
            }
        });

        // Event Logout
        BtnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(parentFrame, "Yakin ingin logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Session.TOKEN = "";
                Login login = new Login();
                login.setVisible(true);
                parentFrame.dispose();
            }
        });

        // Event Pindah ke Window Update Profile
        BtnUpdateProfile.addActionListener(e -> {
            UpdateProfile update = new UpdateProfile();
            update.setVisible(true);
            parentFrame.dispose();
        });
    }

    // ==========================================
    // SEPERTI PROPS: Method Pengisi Tampilan Data
    // ==========================================
    public void setProfileData(String email, String fullName, String username, String gender, String stressText, String favoriteActivity) {
        lblEmailSub.setText(email);
        txtUsername.setText(username);
        txtFullName.setText(fullName);
        cbGender.setSelectedItem(gender);
        cbStress.setSelectedItem(stressText);
        cbActivity.setSelectedItem(favoriteActivity);
    }

    public void updateAvatar(URL imageUrl) {
        try {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            profileImg.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            System.err.println("Gagal merender gambar profile avatar: " + e.getMessage());
        }
    }
    
    // Antarmuka pembaca ukuran gambar label internal jika dibutuhkan dinamis
    public JLabel getProfileImgLabel() { return profileImg; }
}