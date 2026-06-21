package Components.Journal_Component;

import Components.RoundedButton;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import senandika.FontManager;
import senandika.ServiceLayer.JournalService;
import senandika.UILayer.Journal;

public class CreateJournalCard extends JPanel {

    public JTextField inputTitle;
    public JTextArea isiJurnal;
    public JLabel previewGambar;
    public RoundedButton tambahGambar;
    public RoundedButton btnBatal;
    public RoundedButton tambahJurnal;

    private File selectedFile;
    private final JFrame parentFrame;

    // Definisikan konstanta lebar kontainer utama agar konsisten
    private final int CONTENT_WIDTH = 320;

    public CreateJournalCard(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
        initActionListeners();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setOpaque(true);
        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
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

        JLabel lblTitle = new JLabel("Tambah Jurnal");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(FontManager.getPoppins(20f).deriveFont(Font.BOLD));
        lblTitle.setForeground(new Color(30, 41, 59));

        JLabel lblSub = new JLabel("Tulis dan abadikan momen berharga Anda hari ini");
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
        inputTitle = new JTextField();
        styleField(inputTitle);

        // 2. Isi Jurnal - Diberikan JScrollPane khusus agar tidak melar kesamping
        isiJurnal = new JTextArea(5, 20);
        isiJurnal.setLineWrap(true);
        isiJurnal.setWrapStyleWord(true);
        isiJurnal.setFont(FontManager.getPoppins(13f));
        isiJurnal.setBackground(new Color(250, 250, 250));
        
        JScrollPane textScroll = new JScrollPane(isiJurnal);
        textScroll.setPreferredSize(new Dimension(CONTENT_WIDTH, 120));
        textScroll.setMinimumSize(new Dimension(CONTENT_WIDTH, 120));
        textScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        textScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        // 3. Tombol Tambah Gambar
        tambahGambar = new RoundedButton();
        tambahGambar.setText("Pilih Foto Jurnal");
        tambahGambar.setCornerRadius(12);
        tambahGambar.setPreferredSize(new Dimension(CONTENT_WIDTH, 38));
        tambahGambar.setBackground(new Color(241, 245, 249));
        tambahGambar.setForeground(new Color(51, 65, 85));
        tambahGambar.setFont(FontManager.getPoppins(12f));

        // 4. Preview Gambar Modern dengan batasan dimensi maksimum awal
        previewGambar = new JLabel();
        previewGambar.setHorizontalAlignment(SwingConstants.CENTER);
        previewGambar.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true));
        previewGambar.setPreferredSize(new Dimension(CONTENT_WIDTH, 180));
        previewGambar.setMinimumSize(new Dimension(CONTENT_WIDTH, 180));

        // Menyusun elemen form
        addField(formPanel, gbc, 0, "Judul Jurnal", inputTitle);
        addField(formPanel, gbc, 1, "Isi Jurnal", textScroll);
        addField(formPanel, gbc, 2, "Unggah Media", tambahGambar);
        addField(formPanel, gbc, 3, "", previewGambar);

        add(formPanel, BorderLayout.CENTER);
    }

    private void createButtonSection() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        btnBatal = new RoundedButton();
        btnBatal.setText("Batal");
        btnBatal.setCornerRadius(12);
        btnBatal.setPreferredSize(new Dimension(100, 40));
        btnBatal.setBackground(new Color(241, 245, 249));
        btnBatal.setForeground(new Color(100, 116, 139));
        btnBatal.setFont(FontManager.getPoppins(13f));

        tambahJurnal = new RoundedButton();
        tambahJurnal.setText("Tambah Jurnal");
        tambahJurnal.setCornerRadius(12);
        tambahJurnal.setPreferredSize(new Dimension(140, 40));
        tambahJurnal.setBackground(new Color(137, 126, 255));
        tambahJurnal.setForeground(Color.WHITE);
        tambahJurnal.setFont(FontManager.getPoppins(13f));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;

        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        buttonPanel.add(btnBatal, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(tambahJurnal, gbc);

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

    // Fungsi utilitas untuk menskalakan gambar secara proporsional agar tidak gepeng/pecah
    private ImageIcon getScaledImage(File file, int targetWidth, int targetHeight) {
        ImageIcon rawIcon = new ImageIcon(file.getAbsolutePath());
        int imgWidth = rawIcon.getIconWidth();
        int imgHeight = rawIcon.getIconHeight();

        // Hitung aspek rasio agar pas di dalam bounding box area preview
        double ratioX = (double) targetWidth / imgWidth;
        double ratioY = (double) targetHeight / imgHeight;
        double minRatio = Math.min(ratioX, ratioY);

        int newWidth = (int) (imgWidth * minRatio);
        int newHeight = (int) (imgHeight * minRatio);

        Image scaledImg = rawIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    private void initActionListeners() {
        tambahGambar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "webp"));
            int result = chooser.showOpenDialog(parentFrame);

            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                
                // Gunakan fungsi kalkulasi skala gambar proporsional baru
                ImageIcon modernScaledIcon = getScaledImage(selectedFile, CONTENT_WIDTH, 180);
                previewGambar.setIcon(modernScaledIcon);
                
                parentFrame.getContentPane().revalidate();
                parentFrame.getContentPane().repaint();
            }
        });

        tambahJurnal.addActionListener(e -> {
            try {
                String judul = inputTitle.getText().trim();
                String isi = isiJurnal.getText().trim();

                if (judul.isEmpty() || isi.isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, "Semua field wajib diisi", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                JournalService service = new JournalService();
                service.createJournal(judul, isi, selectedFile);

                JOptionPane.showMessageDialog(parentFrame, "Jurnal berhasil disimpan");
                
                inputTitle.setText("");
                isiJurnal.setText("");
                previewGambar.setIcon(null);
                selectedFile = null;
                
                Journal jurnalWindow = new Journal();
                jurnalWindow.setVisible(true);
                parentFrame.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBatal.addActionListener(e -> {
            Journal jurnalWindow = new Journal();
            jurnalWindow.setVisible(true);
            parentFrame.dispose();
        });
    }
}