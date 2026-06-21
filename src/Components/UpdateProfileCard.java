package Components;

import java.awt.*;
import javax.swing.*;
import senandika.FontManager;

public class UpdateProfileCard extends JPanel {

    public JTextField inputUsername;
    public JTextField inputFullName;

    public JComboBox<String> cbGender;
    public JComboBox<String> cbStress;
    public JComboBox<String> cbActivity;

    public RoundedButton btnBatal;
    public RoundedButton btnSimpan;

    public UpdateProfileCard() {
        initComponents();
    }

    private void initComponents() {

        setLayout(new BorderLayout(15, 15));

        setOpaque(true);
        setBackground(Color.WHITE);

        setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(230, 230, 230),
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                25,
                                25,
                                25,
                                25
                        )
                )
        );

        createHeader();
        createForm();
        createButtons();
    }

    private void createHeader() {

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblTitle =
                new JLabel("Update Profile");

        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTitle.setFont(
                FontManager.getPoppins(16f)
                        .deriveFont(Font.BOLD)
        );

        lblTitle.setForeground(
                new Color(30, 41, 59)
        );

        JLabel lblSub =
                new JLabel(
                        "Perbarui informasi profil Anda"
                );

        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSub.setFont(
                FontManager.getPoppins(12f)
        );

        lblSub.setForeground(
                new Color(100, 116, 139)
        );

        header.add(lblTitle);
        header.add(Box.createVerticalStrut(5));
        header.add(lblSub);

        add(header, BorderLayout.NORTH);
    }

    private void createForm() {

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        10,
                        0,
                        10,
                        0
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        inputUsername =
                new JTextField();

        inputFullName =
                new JTextField();

        cbGender =
                new JComboBox<>(
                        new String[]{
                            "Pilih Salah Satu",
                            "Laki-laki",
                            "Perempuan"
                        }
                );

        cbStress =
                new JComboBox<>(
                        new String[]{
                            "Pilih Salah Satu",
                            "Sangat Santai",
                            "Santai",
                            "Normal",
                            "Stres",
                            "Sangat Stres"
                        }
                );

        cbActivity =
                new JComboBox<>(
                        new String[]{
                            "Pilih Salah Satu",
                            "Mendengarkan musik",
                            "Olahraga",
                            "Menonton film",
                            "Bermain game",
                            "Membaca buku",
                            "Meditasi",
                            "Jalan-jalan"
                        }
                );

        styleField(inputUsername);
        styleField(inputFullName);

        styleCombo(cbGender);
        styleCombo(cbStress);
        styleCombo(cbActivity);

        addField(
                formPanel,
                gbc,
                0,
                "Username",
                inputUsername
        );

        addField(
                formPanel,
                gbc,
                1,
                "Full Name",
                inputFullName
        );

        addField(
                formPanel,
                gbc,
                2,
                "Gender",
                cbGender
        );

        addField(
                formPanel,
                gbc,
                3,
                "Stress Level",
                cbStress
        );

        addField(
                formPanel,
                gbc,
                4,
                "Aktivitas Favorit",
                cbActivity
        );

        add(formPanel, BorderLayout.CENTER);
    }

    private void createButtons() {
        // Menggunakan GridBagLayout agar tombol terkunci di satu baris horizontal dan tidak melorot ke bawah
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        btnBatal = new RoundedButton();
        btnBatal.setText("Batal");
        btnBatal.setCornerRadius(12);
        btnBatal.setPreferredSize(new Dimension(110, 45));
        btnBatal.setBackground(new Color(229, 231, 235));

        btnSimpan = new RoundedButton();
        btnSimpan.setText("Simpan Perubahan");
        btnSimpan.setCornerRadius(12);
        btnSimpan.setPreferredSize(new Dimension(180, 45));
        btnSimpan.setBackground(new Color(137, 126, 255));
        btnSimpan.setForeground(Color.WHITE);

        // Pengaturan GridBagConstraints untuk meratakan tombol ke kanan
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;

        // Beri dorongan (weightx) ke kolom pertama agar mendorong tombol ke sudut kanan
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 10); // Jarak antar tombol Batal dan Simpan
        buttonPanel.add(btnBatal, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 0);
        buttonPanel.add(btnSimpan, gbc);

        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JComponent field
    ) {

        gbc.gridx = 0;
        gbc.gridy = row * 2;

        JLabel lbl =
                new JLabel(label);

        lbl.setFont(
                FontManager.getPoppins(13f)
        );

        panel.add(lbl, gbc);

        gbc.gridy = row * 2 + 1;

        panel.add(field, gbc);
    }

    private void styleField(
            JTextField field
    ) {

        field.setPreferredSize(
                new Dimension(
                        350,
                        38
                )
        );

        field.setFont(
                FontManager.getPoppins(11f)
        );
    }

    private void styleCombo(
            JComboBox<String> combo
    ) {

        combo.setPreferredSize(
                new Dimension(
                        350,
                        39
                )
        );

        combo.setFont(
                FontManager.getPoppins(11f)
        );
    }
}