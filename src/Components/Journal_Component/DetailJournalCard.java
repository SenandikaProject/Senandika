package Components.Journal_Component;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class DetailJournalCard extends JPanel {

    private static final String BASE_URL = "http://localhost:5000";

    private String imagePath;
    private final JLabel lblTitle;
    private final JLabel lblDate;
    private final JTextArea txtContent;
    private final JPanel headerArea;
    private final JButton btnBack;

    public DetailJournalCard() {
        setOpaque(false);
        setLayout(new BorderLayout());

        // Menggunakan panel kustom dengan sudut melengkung sebagai pembungkus utama
        JPanel mainPanel = new RoundedPanel(20, Color.WHITE, true);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(null);

        // --- 1. HEADER AREA (GELOMBANG GAMBAR / FALLBACK STATE) ---
        headerArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int w = getWidth();
                int h = getHeight();

                try {
                    if (imagePath != null && !imagePath.trim().isEmpty()) {
                        Image image = null;

                        // Resolusi path mentah menjadi URL/path yang benar-benar bisa diakses
                        String resolvedPath = resolveImageUrl(imagePath);

                        System.out.println("[DetailJournalCard] imagePath asli   : " + imagePath);
                        System.out.println("[DetailJournalCard] imagePath resolve: " + resolvedPath);

                        // Solusi 1: Path Web (http/https) - baik dari backend langsung maupun hasil resolve BASE_URL
                        if (resolvedPath.startsWith("http://") || resolvedPath.startsWith("https://")) {
                            image = new ImageIcon(new URL(resolvedPath)).getImage();
                        }
                        // Solusi 2: File Lokal System biasa (misal hasil JFileChooser)
                        else if (new File(resolvedPath).exists()) {
                            image = new ImageIcon(resolvedPath).getImage();
                        }
                        // Solusi 3: Fallback ke Project Resources (Class Loader)
                        else {
                            URL imgURL = getClass().getClassLoader().getResource(resolvedPath);
                            if (imgURL == null) {
                                imgURL = getClass().getResource("/" + resolvedPath);
                            }
                            if (imgURL != null) {
                                image = new ImageIcon(imgURL).getImage();
                            }
                        }

                        if (image != null && image.getWidth(this) > 0) {
                            int imgW = image.getWidth(this);
                            int imgH = image.getHeight(this);

                            if (imgW > 0 && imgH > 0) {
                                double containerRatio = (double) w / h;
                                double imageRatio = (double) imgW / imgH;

                                int renderW, renderH, x = 0, y = 0;
                                if (imageRatio > containerRatio) {
                                    renderH = h;
                                    renderW = (int) (h * imageRatio);
                                    x = (w - renderW) / 2;
                                } else {
                                    renderW = w;
                                    renderH = (int) (w / imageRatio);
                                    y = (h - renderH) / 2;
                                }
                                g2.drawImage(image, x, y, renderW, renderH, this);
                                g2.dispose();
                                return;
                            }
                        } else {
                            System.out.println("[DetailJournalCard] Gambar gagal di-load (null/invalid): " + resolvedPath);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Gagal memuat gambar detail: " + e.getMessage());
                    e.printStackTrace();
                }

                // FALLBACK STATE: Tampilan jika tidak ada lampiran gambar
                drawFallback(g2, w, h);
                g2.dispose();
            }

            private void drawFallback(Graphics2D g2, int w, int h) {
                GradientPaint gradient = new GradientPaint(0, 0, new Color(137, 126, 255), w, h, new Color(110, 98, 230));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, w, h);

                g2.setColor(new Color(255, 255, 255, 180));
                g2.setFont(new Font("Poppins", Font.BOLD, 14));
                String text = "Tidak Ada Gambar Lampiran";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(text, (w - fm.stringWidth(text)) / 2, (h / 2) + 5);
            }
        };
        headerArea.setLayout(null);
        headerArea.setPreferredSize(new Dimension(360, 230));
        headerArea.setOpaque(false);

        // --- BUTTON BACK MELAYANG ---
        btnBack = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, 40, 40);
                g2.setColor(new Color(137, 126, 255));
                g2.setFont(new Font("SansSerif", Font.BOLD, 22));
                FontMetrics fm = g2.getFontMetrics();
                String backSym = "‹";
                g2.drawString(backSym, (40 - fm.stringWidth(backSym)) / 2, (40 - fm.getHeight()) / 2 + fm.getAscent() - 3);
                g2.dispose();
            }
        };
        btnBack.setBounds(15, 15, 40, 40);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        headerArea.add(btnBack);

        mainPanel.add(headerArea, BorderLayout.NORTH);

        // --- 2. CONTENT AREA ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // JUDUL JURNAL (Menggunakan dynamic width wrapper berbasis html)
        lblTitle = new JLabel("<html><div style='width:300px;'>Memuat Judul...</div></html>");
        lblTitle.setFont(new Font("Poppins", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 41, 59));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(lblTitle);

        contentPanel.add(Box.createVerticalStrut(6));

        // TANGGAL
        lblDate = new JLabel("Memuat Tanggal...");
        lblDate.setFont(new Font("Poppins", Font.PLAIN, 12));
        lblDate.setForeground(new Color(156, 163, 175));
        lblDate.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(lblDate);

        contentPanel.add(Box.createVerticalStrut(15));

        // ISI JURNAL
        txtContent = new JTextArea("Memuat konten jurnal...");
        txtContent.setFont(new Font("Poppins", Font.PLAIN, 14));
        txtContent.setForeground(new Color(75, 85, 99));
        txtContent.setLineWrap(true);
        txtContent.setWrapStyleWord(true);
        txtContent.setEditable(false);
        txtContent.setFocusable(false);
        txtContent.setOpaque(false);
        txtContent.setBorder(null);
        txtContent.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(txtContent);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    // Menggunakan fungsi format tanggal yang telah disinkronisasikan (+16 jam WITA)
    public void setJournalData(String judul, String tanggalMentah, String isi, String imagePath) {
        this.imagePath = imagePath;
        lblTitle.setText("<html><div style='width:300px;'>" + judul + "</div></html>");

        // Memformat waktu sesuai standard page mood yang Anda minta
        lblDate.setText(formatDate(tanggalMentah));
        txtContent.setText(isi);

        headerArea.repaint();
        revalidate();
        repaint();
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    /**
     * Menggabungkan path relatif dari backend (mis. "/uploads/journals/xxx.jpg")
     * dengan BASE_URL agar bisa diakses sebagai URL gambar yang valid.
     * Jika path sudah berupa URL absolut atau path file lokal yang valid, dikembalikan apa adanya.
     */
    private String resolveImageUrl(String rawPath) {
        if (rawPath == null || rawPath.trim().isEmpty()) {
            return rawPath;
        }
        String trimmed = rawPath.trim();

        // Sudah URL lengkap
        if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            return trimmed;
        }

        // Path file lokal yang benar-benar ada (misal hasil pilih file lokal sebelumnya)
        if (new File(trimmed).exists()) {
            return trimmed;
        }

        // Path relatif dari server (format umum: "/uploads/journals/xxx.jpg")
        if (trimmed.startsWith("/")) {
            return BASE_URL + trimmed;
        }

        // Fallback terakhir: gabungkan dengan separator slash
        return BASE_URL + "/" + trimmed;
    }

    // Fungsi Formatting Tanggal Internal (+16 Jam penyesuaian timezone Supabase)
    private static String formatDate(String isoDate) {
        if (isoDate == null || isoDate.isEmpty()) {
            return "";
        }
        try {
            String clean = isoDate.trim();
            java.time.LocalDate date = java.time.LocalDate.parse(clean.substring(0, 10));
            int indexT = clean.contains("T") ? clean.indexOf("T") : clean.indexOf(" ");
            String jamStr = " 23:00";

            if (indexT != -1 && clean.length() > indexT + 5) {
                String waktuMentah = clean.substring(indexT + 1);
                String[] parts = waktuMentah.split(":");
                if (parts.length >= 2) {
                    int jamAngka = Integer.parseInt(parts[0]);
                    int jamLokal = (jamAngka + 16) % 24;
                    if (jamAngka + 16 >= 24) {
                        date = date.plusDays(1);
                    }
                    jamStr = String.format(" %02d:%s", jamLokal, parts[1]);
                }
            }

            String[] bulan = {
                "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                "Juli", "Agustus", "September", "Oktober", "November", "Desember"
            };
            return date.getDayOfMonth() + " " + bulan[date.getMonthValue() - 1] + " " + date.getYear() + jamStr;
        } catch (Exception e) {
            return isoDate;
        }
    }

    private static class RoundedPanel extends JPanel {
        private final int cornerRadius;
        private final Color backgroundColor;
        private final boolean isRounded;

        public RoundedPanel(int radius, Color bgColor, boolean rounded) {
            this.cornerRadius = radius;
            this.backgroundColor = bgColor;
            this.isRounded = rounded;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(backgroundColor != null ? backgroundColor : getBackground());
            if (isRounded) {
                graphics.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            } else {
                graphics.fillRect(0, 0, getWidth(), getHeight());
            }
            graphics.dispose();
        }
    }
}