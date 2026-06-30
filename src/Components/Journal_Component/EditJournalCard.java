package Components.Journal_Component;

import Components.RoundedButton;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import senandika.FontManager;
import senandika.Model.JournalData;

public class EditJournalCard extends JPanel {

    private JTextField txtJudul;
    private JTextArea txtIsi;
    private JLabel lblFotoPreview;
    
    private RoundedButton btnSave;
    private RoundedButton btnCancel;
    private RoundedButton btnPilihFoto;

    private int journalId; // Menyimpan ID data yang sedang diedit
    private File selectedFotoFile = null; // Menyimpan file foto baru jika user mengganti gambar
    private String oldFotoUrl = null; // Menyimpan path/URL foto lama dari database

    public EditJournalCard(JFrame parentFrame) {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        initComponents(parentFrame);
    }

    private void initComponents(JFrame parentFrame) {
        // =========================================================================
        // 1. HEADER PANEL
        // =========================================================================
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("Edit Jurnal");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(FontManager.getPoppins(20f));

        JLabel lblSub = new JLabel("Perbarui isi detail dan dokumen jurnal Anda");
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSub.setFont(FontManager.getPoppins(12f));

        header.add(lblTitle);
        header.add(Box.createVerticalStrut(5));
        header.add(lblSub);

        add(header, BorderLayout.NORTH);

        // =========================================================================
        // 2. FORM PANEL (GridBagLayout)
        // =========================================================================
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 0, 5, 0);

        // --- Input Judul ---
        gbc.gridy = 0;
        JLabel lblJudul = new JLabel("Judul");
        lblJudul.setFont(FontManager.getPoppins(12f));
        form.add(lblJudul, gbc);

        txtJudul = new JTextField();
        txtJudul.setPreferredSize(new Dimension(txtJudul.getPreferredSize().width, 35));
        gbc.gridy = 1;
        form.add(txtJudul, gbc);

        // --- Input Isi ---
        gbc.gridy = 2;
        JLabel lblIsi = new JLabel("Isi Jurnal");
        lblIsi.setFont(FontManager.getPoppins(12f));
        form.add(lblIsi, gbc);

        txtIsi = new JTextArea(8, 20);
        txtIsi.setLineWrap(true);
        txtIsi.setWrapStyleWord(true);
        JScrollPane scrollIsi = new JScrollPane(txtIsi);
        gbc.gridy = 3;
        form.add(scrollIsi, gbc);

        // --- Input & Preview Foto ---
        gbc.gridy = 4;
        JLabel lblFoto = new JLabel("Foto Jurnal");
        lblFoto.setFont(FontManager.getPoppins(12f));
        form.add(lblFoto, gbc);

        // Container Upload Gambar
        JPanel fotoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        fotoPanel.setOpaque(false);

        btnPilihFoto = new RoundedButton();
        btnPilihFoto.setText("Ubah Foto");
        btnPilihFoto.setCornerRadius(8);
        btnPilihFoto.setBackground(new Color(240, 240, 240));
        btnPilihFoto.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lblFotoPreview = new JLabel("Tidak ada foto");
        lblFotoPreview.setPreferredSize(new Dimension(100, 100));
        lblFotoPreview.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        lblFotoPreview.setHorizontalAlignment(SwingConstants.CENTER);

        fotoPanel.add(lblFotoPreview);
        fotoPanel.add(btnPilihFoto);

        gbc.gridy = 5;
        form.add(fotoPanel, gbc);

        add(form, BorderLayout.CENTER);

        // =========================================================================
        // 3. ACTION BUTTONS PANEL
        // =========================================================================
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottom.setOpaque(false);

        btnCancel = new RoundedButton();
        btnCancel.setText("Batal");
        btnCancel.setCornerRadius(8);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSave = new RoundedButton();
        btnSave.setText("Simpan Perubahan");
        btnSave.setCornerRadius(8);
        btnSave.setBackground(new Color(137, 126, 255));
        btnSave.setForeground(Color.WHITE);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottom.add(btnCancel);
        bottom.add(btnSave);

        add(bottom, BorderLayout.SOUTH);

        // =========================================================================
        // 4. EVENT LISTENERS
        // =========================================================================
        btnPilihFoto.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Pilih Foto Jurnal");
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
            
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFotoFile = chooser.getSelectedFile();
                updateImagePreview(selectedFotoFile.getAbsolutePath());
            }
        });
    }

    /**
     * Memuat dan mengisi data jurnal lama ke dalam komponen form saat halaman diakses.
     */
    public void setJournalData(JournalData data) {
        if (data == null) return;
        
        this.journalId = data.getId();
        txtJudul.setText(data.getJudul());
        txtIsi.setText(data.getIsi());
        
        // Asumsikan model JournalData memiliki properti URL/Path Foto (e.g., getFoto() atau getGambar())
        // Sesuaikan nama method getter di bawah dengan properti asli pada objek JournalData Anda.
        this.oldFotoUrl = data.getImagePath(); 
        
        if (oldFotoUrl != null && !oldFotoUrl.isEmpty()) {
            updateImagePreview(oldFotoUrl);
        } else {
            lblFotoPreview.setIcon(null);
            lblFotoPreview.setText("Tidak ada foto");
        }
    }

    /**
     * Helper untuk memproses scaling dan menampilkan gambar pada label preview
     */
    private void updateImagePreview(String pathOrUrl) {
        try {
            ImageIcon icon;
            // Mengecek apakah gambar berasal dari path lokal komputer atau server URL web
            if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
                icon = new ImageIcon(new java.net.URL(pathOrUrl));
            } else {
                icon = new ImageIcon(pathOrUrl);
            }
            
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblFotoPreview.setIcon(new ImageIcon(scaledImg));
            lblFotoPreview.setText("");
        } catch (Exception e) {
            lblFotoPreview.setIcon(null);
            lblFotoPreview.setText("Gagal memuat");
        }
    }

    // =========================================================================
    // GETTER & DATA AKSESOR UNTUK CONTROLLER / UI LAYER
    // =========================================================================
    public int getJournalId() { return journalId; }
    public JTextField getTxtJudul() { return txtJudul; }
    public JTextArea getTxtIsi() { return txtIsi; }
    public RoundedButton getBtnSave() { return btnSave; }
    public RoundedButton getBtnCancel() { return btnCancel; }
    public File getSelectedFotoFile() { return selectedFotoFile; }
    public String getOldFotoUrl() { return oldFotoUrl; }
}