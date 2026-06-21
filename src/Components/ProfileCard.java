package Components;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.net.URL;
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
    private final JFrame parentFrame;

    public ProfileCard(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
        initActionListeners();
    }

    private void initComponents() {
        // Menggunakan BorderLayout utama agar seirama dengan UpdateProfileCard
        setLayout(new BorderLayout(20, 20));
        setOpaque(true);
        setBackground(Color.WHITE);

        // Border compound modern (line border tipis abu-abu + empty border di sekeliling isi)
        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
            )
        );

        createHeaderSection();
        createFormSection();
        createButtonSection();
    }

    private void createHeaderSection() {
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        // Wadah Avatar Tengah
        profileImg = new JLabel();
        profileImg.setPreferredSize(new Dimension(100, 100));
        profileImg.setMaximumSize(new Dimension(100, 100));
        profileImg.setMinimumSize(new Dimension(100, 100));
        profileImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileImg.setHorizontalAlignment(SwingConstants.CENTER);
        profileImg.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true));

        // Subtitle Email dengan warna Slate modern
        lblEmailSub = new JLabel("email@domain.com");
        lblEmailSub.setFont(FontManager.getPoppins(13f));
        lblEmailSub.setForeground(new Color(100, 116, 139));
        lblEmailSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel Tombol Upload/Simpan Foto di bawah email
        JPanel photoActionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        photoActionPanel.setOpaque(false);

        btnChooseFoto = new RoundedButton();
        btnChooseFoto.setText("Upload Foto");
        btnChooseFoto.setCornerRadius(12);
        btnChooseFoto.setPreferredSize(new Dimension(110, 32));
        btnChooseFoto.setBackground(new Color(229, 231, 235));
        btnChooseFoto.setFont(FontManager.getPoppins(12f));

        btnSaveFoto = new RoundedButton();
        btnSaveFoto.setText("Simpan Foto");
        btnSaveFoto.setCornerRadius(12);
        btnSaveFoto.setPreferredSize(new Dimension(110, 32));
        btnSaveFoto.setBackground(new Color(137, 126, 255));
        btnSaveFoto.setForeground(Color.WHITE);
        btnSaveFoto.setFont(FontManager.getPoppins(12f));

        photoActionPanel.add(btnChooseFoto);
        photoActionPanel.add(btnSaveFoto);

        // Satukan elemen ke dalam panel header
        headerPanel.add(profileImg);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(lblEmailSub);
        headerPanel.add(Box.createVerticalStrut(12));
        headerPanel.add(photoActionPanel);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void createFormSection() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Inisialisasi komponen form
        txtUsername = new JTextField();
        txtUsername.setEditable(false);
        styleField(txtUsername);

        txtFullName = new JTextField();
        txtFullName.setEditable(false);
        styleField(txtFullName);

        cbGender = new JComboBox<>(new String[]{"Pilih Salah Satu", "Laki-laki", "Perempuan"});
        cbGender.setEnabled(false);
        styleCombo(cbGender);

        cbStress = new JComboBox<>(new String[]{"Pilih Salah Satu", "Sangat Santai", "Santai", "Normal", "Stres", "Sangat Stres"});
        cbStress.setEnabled(false);
        styleCombo(cbStress);

        cbActivity = new JComboBox<>(new String[]{"Pilih Salah Satu", "Mendengarkan musik", "Olahraga", "Menonton film", "Bermain game", "Membaca buku", "Meditasi", "Jalan-jalan"});
        cbActivity.setEnabled(false);
        styleCombo(cbActivity);

        // Penyusunan Grid secara berurutan (Label di atas Field)
        addField(formPanel, gbc, 0, "Username", txtUsername);
        addField(formPanel, gbc, 1, "Full Name", txtFullName);
        addField(formPanel, gbc, 2, "Gender", cbGender);
        addField(formPanel, gbc, 3, "Stress Level", cbStress);
        addField(formPanel, gbc, 4, "Aktivitas Favorit", cbActivity);

        add(formPanel, BorderLayout.CENTER);
    }

    private void createButtonSection() {
        // Menggunakan GridBagLayout modern agar posisi tombol rata & kencang tanpa patah baris
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        BtnLogout = new RoundedButton();
        BtnLogout.setText("Logout");
        BtnLogout.setCornerRadius(12);
        BtnLogout.setPreferredSize(new Dimension(110, 40));
        BtnLogout.setBackground(new Color(239, 68, 68)); // Muted Red
        BtnLogout.setForeground(Color.WHITE);
        BtnLogout.setFont(FontManager.getPoppins(13f));

        BtnUpdateProfile = new RoundedButton();
        BtnUpdateProfile.setText("Update Profil");
        BtnUpdateProfile.setCornerRadius(12);
        BtnUpdateProfile.setPreferredSize(new Dimension(140, 40));
        BtnUpdateProfile.setBackground(new Color(59, 130, 246)); // Theme Blue
        BtnUpdateProfile.setForeground(Color.WHITE);
        BtnUpdateProfile.setFont(FontManager.getPoppins(13f));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;

        // Dorong tombol Logout di ujung kiri panel tombola melalui anchor
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        buttonPanel.add(BtnLogout, gbc);

        // Tempatkan Update Profil di sisi kanan
        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(BtnUpdateProfile, gbc);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row * 2;
        JLabel lbl = new JLabel(label);
        lbl.setFont(FontManager.getPoppins(12f));
        lbl.setForeground(new Color(51, 65, 85)); // Slate 700
        panel.add(lbl, gbc);

        gbc.gridy = row * 2 + 1;
        panel.add(field, gbc);
    }

    private void styleField(JTextField field) {
        field.setPreferredSize(new Dimension(350, 42));
        field.setFont(FontManager.getPoppins(14f));
        field.setBackground(new Color(248, 250, 252)); // Light grayish blue (Disabled look)
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setPreferredSize(new Dimension(350, 42));
        combo.setFont(FontManager.getPoppins(14f));
        combo.setBackground(new Color(248, 250, 252));
    }

    private void initActionListeners() {
        btnChooseFoto.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "webp"));
            int result = chooser.showOpenDialog(parentFrame);

            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                profileImg.setIcon(new ImageIcon(image));
            }
        });

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

        BtnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(parentFrame, "Yakin ingin logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Session.TOKEN = "";
                Login login = new Login();
                login.setVisible(true);
                parentFrame.dispose();
            }
        });

        BtnUpdateProfile.addActionListener(e -> {
            UpdateProfile update = new UpdateProfile();
            update.setVisible(true);
            parentFrame.dispose();
        });
    }

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
    
    public JLabel getProfileImgLabel() { return profileImg; }
}