package Components.Journal_Component;

import Components.RoundedButton;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import senandika.FontManager;
import senandika.Model.JournalData;

public class EditJournalCard extends JPanel {
    public static final String BASE_URL = "http://localhost:5000";
    private JTextField txtJudul;
    private JTextArea txtIsi;
    private JLabel lblFotoPreview;
    
    private RoundedButton btnSave;
    private RoundedButton btnCancel;
    private RoundedButton btnPilihFoto;

    private int journalId; 
    private File selectedFotoFile = null; 
    private String oldFotoUrl = null; 

    private final JFrame parentFrame;
    private final int CONTENT_WIDTH = 320;

    public EditJournalCard(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setOpaque(true);

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        initComponents();
        initActionListeners();
    }

    private void initComponents() {
        createHeaderSection();
        createFormSection();
        createButtonSection();
    }

    private void createHeaderSection() {
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("Edit Jurnal");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(FontManager.getPoppins(20f).deriveFont(Font.BOLD));
        lblTitle.setForeground(new Color(30, 41, 59));

        JLabel lblSub = new JLabel("Perbarui isi detail dan dokumen jurnal Anda");
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSub.setFont(FontManager.getPoppins(12f));
        lblSub.setForeground(new Color(100, 116, 139));

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(4));
        headerPanel.add(lblSub);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void createFormSection() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // 1. Input Judul
        txtJudul = new JTextField();
        styleField(txtJudul);

        // 2. Input Isi Jurnal
        txtIsi = new JTextArea(5, 20);
        txtIsi.setLineWrap(true);
        txtIsi.setWrapStyleWord(true);
        txtIsi.setFont(FontManager.getPoppins(13f));
        txtIsi.setBackground(new Color(250, 250, 250));
        
        JScrollPane textScroll = new JScrollPane(txtIsi);
        textScroll.setPreferredSize(new Dimension(CONTENT_WIDTH, 120));
        textScroll.setMinimumSize(new Dimension(CONTENT_WIDTH, 120));
        textScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        textScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        // 3. Tombol Ubah Gambar
        btnPilihFoto = new RoundedButton();
        btnPilihFoto.setText("Ubah Foto Jurnal");
        btnPilihFoto.setCornerRadius(12);
        btnPilihFoto.setPreferredSize(new Dimension(CONTENT_WIDTH, 38));
        btnPilihFoto.setBackground(new Color(241, 245, 249));
        btnPilihFoto.setForeground(new Color(51, 65, 85));
        btnPilihFoto.setFont(FontManager.getPoppins(12f));
        btnPilihFoto.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 4. Preview Gambar Modern
        lblFotoPreview = new JLabel();
        lblFotoPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblFotoPreview.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true));
        lblFotoPreview.setPreferredSize(new Dimension(CONTENT_WIDTH, 180));
        lblFotoPreview.setMinimumSize(new Dimension(CONTENT_WIDTH, 180));
        lblFotoPreview.setFont(FontManager.getPoppins(12f));
        lblFotoPreview.setForeground(new Color(148, 163, 184));
        lblFotoPreview.setText("Tidak ada foto");

        // Menyusun elemen ke panel
        addField(formPanel, gbc, 0, "Judul Jurnal", txtJudul);
        addField(formPanel, gbc, 1, "Isi Jurnal", textScroll);
        addField(formPanel, gbc, 2, "Unggah Media", btnPilihFoto);
        addField(formPanel, gbc, 3, "", lblFotoPreview);

        add(formPanel, BorderLayout.CENTER);
    }

    private void createButtonSection() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        btnCancel = new RoundedButton();
        btnCancel.setText("Batal");
        btnCancel.setCornerRadius(12);
        btnCancel.setPreferredSize(new Dimension(100, 40));
        btnCancel.setBackground(new Color(241, 245, 249));
        btnCancel.setForeground(new Color(100, 116, 139));
        btnCancel.setFont(FontManager.getPoppins(13f));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSave = new RoundedButton();
        btnSave.setText("Simpan Perubahan");
        btnSave.setCornerRadius(12);
        btnSave.setPreferredSize(new Dimension(150, 40));
        btnSave.setBackground(new Color(137, 126, 255));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(FontManager.getPoppins(13f));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;

        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        buttonPanel.add(btnCancel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(btnSave, gbc);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row * 2;
        if (!label.isEmpty()) {
            JLabel lbl = new JLabel(label);
            lbl.setFont(FontManager.getPoppins(12f).deriveFont(Font.BOLD));
            lbl.setForeground(new Color(71, 85, 105));
            panel.add(lbl, gbc);
        }
        gbc.gridy = row * 2 + 1;
        panel.add(field, gbc);
    }

    private void styleField(JTextField field) {
        field.setPreferredSize(new Dimension(CONTENT_WIDTH, 42));
        field.setMinimumSize(new Dimension(CONTENT_WIDTH, 42));
        field.setFont(FontManager.getPoppins(14f));
        field.setBackground(new Color(250, 250, 250));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));
    }

    private void initActionListeners() {
        btnPilihFoto.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "webp"));
            int result = chooser.showOpenDialog(parentFrame);

            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFotoFile = chooser.getSelectedFile();
                updateImagePreview(selectedFotoFile.getAbsolutePath());
            }
        });
    }

    public void setJournalData(JournalData data) {
        if (data == null) return;
        
        this.journalId = data.getId();
        txtJudul.setText(data.getJudul());
        txtIsi.setText(data.getIsi());
        this.oldFotoUrl = data.getImagePath(); 
        
        if (oldFotoUrl != null && !oldFotoUrl.trim().isEmpty()) {
            updateImagePreview(oldFotoUrl);
        } else {
            lblFotoPreview.setIcon(null);
            lblFotoPreview.setText("Tidak ada foto");
        }
    }

    private void updateImagePreview(String pathOrUrl) {
        try {
            String resolvedUrl = resolveImageUrl(pathOrUrl);

            ImageIcon rawIcon;
            if (resolvedUrl.startsWith("http://") || resolvedUrl.startsWith("https://")) {
                rawIcon = new ImageIcon(new java.net.URL(resolvedUrl));
            } else {
                rawIcon = new ImageIcon(resolvedUrl);
            }

            int imgWidth = rawIcon.getIconWidth();
            int imgHeight = rawIcon.getIconHeight();

            if (imgWidth <= 0 || imgHeight <= 0) {
                throw new Exception("Gambar tidak valid");
            }

            double ratioX = (double) CONTENT_WIDTH / imgWidth;
            double ratioY = (double) 180 / imgHeight;
            double minRatio = Math.min(ratioX, ratioY);

            int newWidth = (int) (imgWidth * minRatio);
            int newHeight = (int) (imgHeight * minRatio);

            Image scaledImg = rawIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            lblFotoPreview.setIcon(new ImageIcon(scaledImg));
            lblFotoPreview.setText("");
        } catch (Exception e) {
            lblFotoPreview.setIcon(null);
            lblFotoPreview.setText("Gagal memuat gambar");
        }
        
        if (parentFrame != null) {
            parentFrame.getContentPane().revalidate();
            parentFrame.getContentPane().repaint();
        }
    }
    
    // Helper baru: gabungkan base URL kalau path masih relatif & bukan path file lokal
    private String resolveImageUrl(String pathOrUrl) {
        if (pathOrUrl == null || pathOrUrl.trim().isEmpty()) return pathOrUrl;
        if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
            return pathOrUrl; // sudah URL lengkap
        }
        if (new File(pathOrUrl).exists()) {
            return pathOrUrl; // path file lokal valid (misal baru pilih dari JFileChooser)
        }
        // Path relatif dari server (misal "/uploads/journals/xxx.jpg")
        return BASE_URL + pathOrUrl;
    }
    // =========================================================================
    // GETTER & SETTER
    // =========================================================================
    public int getJournalId() { return journalId; }
    public JTextField getTxtJudul() { return txtJudul; }
    public JTextArea getTxtIsi() { return txtIsi; }
    public RoundedButton getBtnSave() { return btnSave; }
    public RoundedButton getBtnCancel() { return btnCancel; }
    public File getSelectedFotoFile() { return selectedFotoFile; }
    public String getOldFotoUrl() { return oldFotoUrl; }
}