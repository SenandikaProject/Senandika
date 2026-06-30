/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Home_Component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import senandika.FontManager;
import senandika.Model.Recommendation;
import java.awt.Point;
import java.awt.Insets;

public class RecommendationDetailDialog extends JDialog {

    private final Recommendation recommendation;
    private JPanel footerArea;
    private CardLayout footerLayout;
    private JPanel footerCards;
    private JLabel timerLabel;
    private Timer countdownTimer;
    private int remainingSeconds;

    public RecommendationDetailDialog(Frame parent, Recommendation recommendation) {
        super(parent, true);
        this.recommendation = recommendation;
        setUndecorated(true);
        initUI();
    }

    private void initUI() {
        setSize(394, 720);
        Frame owner = (Frame) getOwner();
        if (owner != null) {
            Point ownerLocation = owner.getLocationOnScreen();
            Insets ownerInsets = owner.getInsets(); // tinggi title bar + border asli OS
            int targetX = ownerLocation.x + (owner.getWidth() - getWidth()) / 2;
            int targetY = ownerLocation.y + ownerInsets.top; // mulai TEPAT di bawah title bar
            setLocation(targetX, targetY);
        } else {
            setLocationRelativeTo(null);
        }
        setBackground(new Color(0, 0, 0, 0));
        getContentPane().setBackground(new Color(0, 0, 0, 0));

        JPanel mainPanel = new RoundedPanel(0, Color.WHITE, false);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(null);

        // --- HEADER ---
        JPanel headerArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int w = getWidth();
                int h = getHeight();

                // [FIX CROP] Kunci clip area agar gambar TIDAK PERNAH keluar dari batas panel ini,
                // apapun ukuran/rasio gambar aslinya.
                g2.setClip(new RoundRectangle2D.Float(0, 0, w, h, 0, 0));

                boolean drawn = false;
                try {
                    String path = recommendation.getThumbnailUrl();
                    if (path != null && !path.trim().isEmpty()) {
                        java.net.URL imgURL = getClass().getClassLoader().getResource(path);
                        if (imgURL == null) {
                            imgURL = getClass().getResource("/" + path);
                        }

                        if (imgURL != null) {
                            Image image = new ImageIcon(imgURL).getImage();
                            int imgW = image.getWidth(this);
                            int imgH = image.getHeight(this);

                            if (imgW > 0 && imgH > 0) {
                                // [FIX CROP] Object-fit: cover — gambar selalu MEMENUHI container,
                                // tidak gepeng, kelebihannya di-crop (bukan di-fit/stretch).
                                double containerRatio = (double) w / h;
                                double imageRatio = (double) imgW / imgH;

                                int renderW, renderH, x, y;
                                if (imageRatio > containerRatio) {
                                    // Gambar lebih lebar dari container -> samakan tinggi, crop kiri-kanan
                                    renderH = h;
                                    renderW = (int) Math.ceil(h * imageRatio);
                                    x = (w - renderW) / 2;
                                    y = 0;
                                } else {
                                    // Gambar lebih tinggi/sama dari container -> samakan lebar, crop atas-bawah
                                    renderW = w;
                                    renderH = (int) Math.ceil(w / imageRatio);
                                    x = 0;
                                    y = (h - renderH) / 2;
                                }
                                g2.drawImage(image, x, y, renderW, renderH, this);
                                drawn = true;
                            }
                        }
                    }
                } catch (Exception e) {
                    drawn = false;
                }

                if (!drawn) {
                    drawFallback(g2, w, h);
                }
                g2.dispose();
            }

            private void drawFallback(Graphics2D g2, int w, int h) {
                GradientPaint gradient = new GradientPaint(0, 0, new Color(137, 126, 255), w, h, new Color(110, 98, 230));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, w, h);
            }
        };
        headerArea.setLayout(null);
        headerArea.setPreferredSize(new Dimension(394, 250));
        headerArea.setOpaque(false);

        JButton btnBack = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, 45, 45);
                g2.setColor(new Color(137, 126, 255));
                g2.setFont(new Font("SansSerif", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                String backSym = "‹";
                g2.drawString(backSym, (45 - fm.stringWidth(backSym)) / 2, (45 - fm.getHeight()) / 2 + fm.getAscent() - 2);
                g2.dispose();
            }
        };
        btnBack.setBounds(20, 20, 45, 45);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> {
            stopTimerIfRunning();
            dispose();
        });
        headerArea.add(btnBack);
        mainPanel.add(headerArea, BorderLayout.NORTH);

        // --- CONTENT AREA ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 24, 25, 24));

        JLabel titleLabel = new JLabel("<html><div style='width:320px;'>" + recommendation.getTitle() + "</div></html>");
        titleLabel.setFont(FontManager.getPoppins(24f).deriveFont(Font.BOLD));
        titleLabel.setForeground(new Color(30, 41, 59));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setMaximumSize(new Dimension(346, Integer.MAX_VALUE));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        
        JPanel durationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        durationPanel.setOpaque(false);
        durationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 1. Label untuk menampung Ikon Gambar dari Asset
        JLabel durationIconLabel = new JLabel();
        try {
            // Silakan sesuaikan path nama file ikon jam milikmu di sini
            String iconPath = "Asset/aset-utama/clock.png"; 
            java.net.URL imgURL = getClass().getClassLoader().getResource(iconPath);
            if (imgURL == null) {
                imgURL = getClass().getResource("/" + iconPath);
            }
            
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                // Skala ukuran ikon menjadi 18x18 agar pas dan proporsional dengan ukuran teks font 13f
                Image scaledImg = originalIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
                durationIconLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                durationIconLabel.setText("⏱ "); // Fallback jika file gambar tidak ditemukan
                durationIconLabel.setFont(FontManager.getPoppins(13f));
            }
        } catch (Exception e) {
            durationIconLabel.setText("⏱ ");
        }
        durationPanel.add(durationIconLabel);

        // 2. Label untuk menampung Teks Menit Durasi
        JLabel durationTextLabel = new JLabel(formatEstimatedDuration(recommendation.getDurationSeconds()));
        durationTextLabel.setFont(FontManager.getPoppins(13f).deriveFont(Font.BOLD));
        durationTextLabel.setForeground(new Color(137, 126, 255));
        durationPanel.add(durationTextLabel);

        // Masukkan kontainer gabungan ke dalam contentPanel utama
        contentPanel.add(durationPanel);
        contentPanel.add(Box.createVerticalStrut(15));

        JTextArea descArea = new JTextArea(recommendation.getDescription());
        descArea.setFont(FontManager.getPoppins(14f));
        descArea.setForeground(new Color(100, 116, 139));
        descArea.setBackground(Color.WHITE);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setFocusable(false);
        descArea.setOpaque(false);
        descArea.setBorder(null);
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        descArea.setMaximumSize(new Dimension(346, Integer.MAX_VALUE));
        contentPanel.add(descArea);
        contentPanel.add(Box.createVerticalStrut(25));

        JPanel stepsCard = new RoundedPanel(16, new Color(250, 251, 255), true);
        stepsCard.setLayout(new BoxLayout(stepsCard, BoxLayout.Y_AXIS));
        stepsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 238, 250), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        stepsCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        stepsCard.setMaximumSize(new Dimension(346, Integer.MAX_VALUE));

        JLabel labelHeader = new JLabel("Langkah aktivitas");
        labelHeader.setFont(FontManager.getPoppins(16f).deriveFont(Font.BOLD));
        labelHeader.setForeground(new Color(30, 41, 59));
        labelHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelHeader.setMaximumSize(new Dimension(306, Integer.MAX_VALUE));
        stepsCard.add(labelHeader);
        stepsCard.add(Box.createVerticalStrut(15));

        String[] steps = recommendation.getSteps();
        if (steps != null && steps.length > 0) {
            for (int i = 0; i < steps.length; i++) {
                JTextArea stepArea = new JTextArea((i + 1) + ". " + steps[i]);
                stepArea.setFont(FontManager.getPoppins(14f));
                stepArea.setForeground(new Color(51, 65, 85));
                stepArea.setBackground(new Color(250, 251, 255));
                stepArea.setLineWrap(true);
                stepArea.setWrapStyleWord(true);
                stepArea.setEditable(false);
                stepArea.setFocusable(false);
                stepArea.setOpaque(false);
                stepArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
                stepArea.setAlignmentX(Component.LEFT_ALIGNMENT);
                stepArea.setMaximumSize(new Dimension(306, Integer.MAX_VALUE));
                stepsCard.add(stepArea);
            }
        } else {
            JLabel emptySteps = new JLabel("Memuat langkah aktivitas...");
            emptySteps.setFont(FontManager.getPoppins(13f));
            emptySteps.setForeground(new Color(100, 116, 139));
            emptySteps.setAlignmentX(Component.LEFT_ALIGNMENT);
            stepsCard.add(emptySteps);
        }
        contentPanel.add(stepsCard);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- FOOTER
        footerLayout = new CardLayout();
        footerCards = new JPanel(footerLayout);
        footerCards.setBackground(Color.WHITE);
        footerCards.setBorder(BorderFactory.createEmptyBorder(12, 24, 20, 24));

        // STATE 1: Tombol "Mulai Aktivitas"
        JPanel startState = new JPanel();
        startState.setLayout(new BoxLayout(startState, BoxLayout.Y_AXIS));
        startState.setBackground(Color.WHITE);
        JButton btnMulai = createFooterButton("Mulai Aktivitas", new Color(137, 126, 255), Color.WHITE);
        btnMulai.addActionListener(e -> startTimer());
        btnMulai.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMulai.setMaximumSize(new Dimension(346, 48)); // [FIX] BoxLayout menghormati maximumSize, beda dengan BorderLayout
        btnMulai.setPreferredSize(new Dimension(346, 48));
        startState.add(btnMulai);

        // STATE 2: Tampilan timer countdown + tombol Stop
        JPanel runningState = new JPanel();
        runningState.setLayout(new BoxLayout(runningState, BoxLayout.Y_AXIS));
        runningState.setBackground(Color.WHITE);

        timerLabel = new JLabel("00:00", SwingConstants.CENTER);
        timerLabel.setFont(FontManager.getPoppins(28f).deriveFont(Font.BOLD));
        timerLabel.setForeground(new Color(137, 126, 255));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        runningState.add(timerLabel);
        runningState.add(Box.createVerticalStrut(12));

        JButton btnStop = createFooterButton("Stop", new Color(241, 245, 249), new Color(30, 41, 59));
        btnStop.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStop.setMaximumSize(new Dimension(346, 45));
        btnStop.addActionListener(e -> stopActivityManually());
        runningState.add(btnStop);

        footerCards.add(startState, "start");
        footerCards.add(runningState, "running");
        footerLayout.show(footerCards, "start");

        mainPanel.add(footerCards, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    // [TIMER] Memulai countdown sesuai durationSeconds dari model Recommendation
    private void startTimer() {
        remainingSeconds = recommendation.getDurationSeconds();
        if (remainingSeconds <= 0) {
            remainingSeconds = 60; // fallback default 1 menit jika durasi belum diisi
        }
        updateTimerLabel();
        footerLayout.show(footerCards, "running");

        countdownTimer = new Timer(1000, e -> {
            remainingSeconds--;
            updateTimerLabel();
            if (remainingSeconds <= 0) {
                countdownTimer.stop();
                JOptionPane.showMessageDialog(this,
                        "Selamat, kamu telah menyelesaikan aktivitas ini!",
                        "Aktivitas Selesai",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        countdownTimer.start();
    }

    // [TIMER] Update tampilan label format MM:SS
    private void updateTimerLabel() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
    
    private String formatEstimatedDuration(int totalSeconds) {
        if (totalSeconds <= 0) {
            return "Estimasi waktu tidak tersedia";
        }
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        if (minutes == 0) {
            return "Kurang lebih " + seconds + " detik";
        } else if (seconds == 0) {
            return "Kurang lebih " + minutes + " menit";
        } else {
            return "Kurang lebih " + minutes + " menit " + seconds + " detik";
        }
    }

    // [TIMER] User menekan tombol Stop di tengah jalan -> tetap dianggap selesai sesuai permintaan
    private void stopActivityManually() {
        stopTimerIfRunning();
        JOptionPane.showMessageDialog(this,
                "Selamat, kamu telah menyelesaikan aktivitas ini!",
                "Aktivitas Selesai",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    // [TIMER] Hentikan timer dengan aman (dipanggil juga saat tombol back ditekan)
    private void stopTimerIfRunning() {
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
    }

    private JButton createFooterButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(FontManager.getPoppins(14f).deriveFont(Font.BOLD));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }
}