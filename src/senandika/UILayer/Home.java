package senandika.UILayer;

import Components.Home_Component.DailyInsightCard;
import Components.Home_Component.EmptyStatePanel;
import Components.Home_Component.RecommendationCard;
import Components.Home_Component.RecommendationDetailDialog;
import Components.RoundedButton;
import Components.RoundedPanel;
import Components.Home_Component.WellnessProgressCard;
import Components.MoodCard;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import senandika.Model.Mood;
import senandika.Model.Recommendation;
import net.miginfocom.swing.MigLayout;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import senandika.FontManager;
import senandika.ServiceLayer.JournalService;
import senandika.ServiceLayer.MoodService;
import senandika.ServiceLayer.ProfileService;
import senandika.ServiceLayer.RecomendationService;
import senandika.Model.Mood;

public class Home extends javax.swing.JFrame {

   // Instansiasi Service Layer untuk memuat data async
    private final ProfileService profileService = new ProfileService();
    private final MoodService moodService = new MoodService();
    private final RecomendationService recommendationService = new RecomendationService();
    private final JournalService journalService = new JournalService();

    // Deklarasi Komponen Dinamis
    private JLabel greeting;
    private JPanel moodSummaryContent;
    private RoundedPanel moodSummaryCard;
    private JPanel recommendationsContent;
    private WellnessProgressCard wellnessProgressCard;
    private Components.Home_Component.DailyInsightCard dailyInsightCard;

    /**
     * Creates new form Dashboard / Home
     */
    public Home() {
        initComponents();
        initUI();
        loadAllData();
    }
    
    private void initUI() {
        setTitle("Senandika");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up content panel utama dengan AbsoluteLayout bawaan NetBeans Netbeans
        content = new JPanel();
        content.setBackground(new Color(246, 255, 248));
        content.setOpaque(true);
        content.setLayout(new AbsoluteLayout());
        
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(content);
        jScrollPane1.getViewport().setBackground(new Color(246, 255, 248));
        jScrollPane1.setBorder(null);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);

        // Variabel tracker untuk mengontrol flow posisi vertikal komponen AbsoluteLayout
        int currentY = 30;

        // ---- 1. GREETING SECTION ----
        greeting = new JLabel("<html>Halo,<br>senang melihatmu hari ini ^_^</html>");
        greeting.setFont(FontManager.getPoppins(22f).deriveFont(Font.BOLD));
        greeting.setForeground(new Color(137, 126, 255)); // Ungu Utama
        content.add(greeting, new AbsoluteConstraints(24, currentY, 340, 80));
        currentY += 95;

        // ---- 2. MOOD SUMMARY SECTION ----
        moodSummaryCard = new RoundedPanel(16, Color.WHITE, true);
        moodSummaryCard.setLayout(new BorderLayout());
        moodSummaryCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)
        ));
        
        moodSummaryContent = new JPanel();
        moodSummaryContent.setOpaque(false);
        moodSummaryContent.setLayout(new BoxLayout(moodSummaryContent, BoxLayout.Y_AXIS));
        moodSummaryCard.add(moodSummaryContent, BorderLayout.CENTER);
        
        content.add(moodSummaryCard, new AbsoluteConstraints(24, currentY, 344, 175));
        renderMoodSummaryLoading();
        currentY += 190;

        // ---- 3. DAILY INSIGHT CARD ----
        dailyInsightCard = new DailyInsightCard();
        content.add(dailyInsightCard, new AbsoluteConstraints(24, currentY, 344, 110));
        currentY += 125;

        // ---- 4. RECOMMENDATION SECTION ----
        JLabel recTitle = new JLabel("Rekomendasi Untukmu");
        recTitle.setFont(FontManager.getPoppins(15f).deriveFont(Font.BOLD));
        recTitle.setForeground(new Color(30, 41, 59));
        content.add(recTitle, new AbsoluteConstraints(28, currentY, 340, 25));
        currentY += 30;
        // PANEL INTERNAL: Menggunakan BoxLayout X_AXIS agar deretan card otomatis berjejer ke samping
        JPanel horizontalScrollPanel = new JPanel();
        horizontalScrollPanel.setOpaque(false);
        horizontalScrollPanel.setLayout(new BoxLayout(horizontalScrollPanel, BoxLayout.X_AXIS)); // Mengunci layout horizontal

        // SCROLL PANE: Pembungkus panel internal agar list bisa di-scroll ke samping dengan halus
        JScrollPane horizontalScroll = new JScrollPane(horizontalScrollPanel);
        horizontalScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        horizontalScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        horizontalScroll.setBorder(null);
        horizontalScroll.setOpaque(false);
        horizontalScroll.getViewport().setOpaque(false);
        horizontalScroll.getHorizontalScrollBar().setUnitIncrement(14); // Mengatur kecepatan scroll mouse

        // Masukkan Scroll Pane utama ke layout Absolute kontainer Home
        content.add(horizontalScroll, new AbsoluteConstraints(24, currentY, 344, 250));
        
        // PENTING: Alihkan reference variabel global kamu ke panel internal baru ini
        recommendationsContent = horizontalScrollPanel; 
        
        currentY += 265;

        // ---- 5. WELLNESS PROGRESS SECTION ----
        wellnessProgressCard = new WellnessProgressCard();
        wellnessProgressCard.setLoading();
        content.add(wellnessProgressCard, new AbsoluteConstraints(24, currentY, 344, 96));
        currentY += 110;

        // ---- 6. QUICK ACTIONS SECTION ----
        JLabel quickTitle = new JLabel("Aksi Cepat");
        quickTitle.setFont(FontManager.getPoppins(15f).deriveFont(Font.BOLD));
        quickTitle.setForeground(new Color(30, 41, 59));
        content.add(quickTitle, new AbsoluteConstraints(28, currentY, 340, 25));
        currentY += 35;

        JPanel gridActions = new JPanel(new MigLayout("insets 0, gap 12", "[grow,fill][grow,fill]"));
        gridActions.setOpaque(false);
        
        // Menu 1: Buat Jurnal (Tetap)
        gridActions.add(buildQuickActionCard("Asset/aset-utama/createjournal.png", "Buat Jurnal", () -> {
            senandika.UILayer.Journal jurnal = new senandika.UILayer.Journal();
            jurnal.setVisible(true);
            dispose();
        }));
        
        // Menu 2: Isi Mood
        gridActions.add(buildQuickActionCard("Asset/aset-utama/icon_mood.png", "Isi Mood", () -> {
            senandika.UILayer.Mood moodWindow = new senandika.UILayer.Mood();
            moodWindow.setVisible(true);
            dispose();
        }), "wrap");

        // Menu 3: Acak Tantangan
        gridActions.add(buildQuickActionCard("Asset/aset-utama/dailyChallenge.png", "Acak Tantangan", () -> {
            String[] challenges = {
                "Minum air putih satu gelas penuh sekarang juga demi hidrasi otakmu.",
                "Rapikan meja belajar atau kasurmu dalam waktu 2 menit. Rasakan kesegarannya!",
                "Tarik napas dalam-dalam selama 4 detik, tahan 4 detik, embuskan 4 detik. Ulangi 3 kali.",
                "Jauhkan pandangan dari layar laptop/HP, tatap objek terjauh di luar jendela selama 20 detik.",
                "Kirimkan satu stiker lucu atau pesan teks singkat 'terima kasih' kepada teman dekatmu.",
                "Lakukan peregangan tangan dan putar pundakmu 5 kali untuk melepas otot kaku.",
                "Berdirilah dari kursi, jalan santai keliling ruanganmu selama 1 menit penuh.",
                "Tuliskan 1 hal paling sepele yang membuatmu tersenyum kecil hari ini.",
                "Pejamkan mata selama 1 menit penuh tanpa memikirkan tugas kuliah.",
                "Cuci mukamu dengan air dingin segar untuk mengembalikan fokus dan menurunkan stres.",
                "Dengarkan 1 lagu favoritmu tanpa melakukan aktivitas lain.",
                "Katakan pada dirimu sendiri di dalam hati: 'Aku sudah melakukan yang terbaik hari ini.'"
            };
            int randIndex = (int) (Math.random() * challenges.length);
            JOptionPane.showMessageDialog(this, challenges[randIndex], "Tantangan Mikro Hari Ini", JOptionPane.INFORMATION_MESSAGE);
        }));
        
        // Menu 4: Kutipan Hari Ini (AKSI POP-UP INSTAN)
        gridActions.add(buildQuickActionCard("Asset/aset-utama/dailyQuotes.png", "Kutipan Harian", () -> {
            String[] quotes = {
                "“Tidak apa-apa untuk merasa lelah. Langkah kecilmu hari ini tetaplah sebuah progres.”",
                "“Ambil napas dalam-dalam. Ingat, harimu tidak ditentukan oleh satu momen buruk saja.”",
                "“Kamu jauh lebih kuat, tangguh, dan berharga daripada yang kamu bayangkan hari ini.”",
                "“Sama seperti kode yang eror, hidup juga butuh *debugging* lewat istirahat yang cukup.”",
                "“Jangan membandingkan halaman ke-1 milikmu dengan halaman ke-20 milik orang lain.”",
                "“Perasaan sedih atau kesal hari ini adalah valid. Terima, peluk, lalu lepaskan pelan-pelan.”",
                "“Hari yang berat bukan berarti kehidupan yang berat. Besok ada kesempatan baru.”",
                "“Kamu sudah bertahan sejauh ini melewati hari-hari yang kamu pikir tak sanggup kamu lewati.”",
                "“Istirahat sejenak bukan berarti menyerah. Mesin paling canggih pun butuh dimatikan berkala.”",
                "“Fokuslah pada apa yang bisa kamu kendalikan hari ini. Sisanya, biarkan waktu yang menjawab.”",
                "“Dunia tidak menuntutmu untuk sempurna setiap hari. Menjadi dirimu yang tulus sudah cukup.”",
                "“Kurangi *overthinking*, naikkan *self-loving*. Kamu sedang berproses menjadi versi terbaikmu.”"
            };
            int randIndex = (int) (Math.random() * quotes.length);
            JOptionPane.showMessageDialog(this, quotes[randIndex], "Self Affirmation", JOptionPane.PLAIN_MESSAGE);
        }));
        
        content.add(gridActions, new AbsoluteConstraints(24, currentY, 344, 260));
        currentY += 280;

        // Atur dimensi scrollable secara dinamis berdasarkan kalkulasi akumulasi tinggi komponen di atas
        content.setPreferredSize(new Dimension(398, currentY + 20));
        content.revalidate();
        content.repaint();
    }

    // -----------------------------------------------------------------
    // Render State Management (Loading, Success, Error)
    // -----------------------------------------------------------------

    private void renderMoodSummaryLoading() {
        moodSummaryContent.removeAll();
        JLabel loading = new JLabel("Memuat mood terakhir...");
        loading.setFont(FontManager.getPoppins(12f));
        loading.setForeground(new Color(100, 116, 139));
        moodSummaryContent.add(loading);
        moodSummaryContent.revalidate();
        moodSummaryContent.repaint();
    }

    private void renderMoodSummary(Mood mood) {
        moodSummaryContent.removeAll();

        JLabel sectionTitle = new JLabel("Mood terakhir");
        sectionTitle.setFont(FontManager.getPoppins(11f));
        sectionTitle.setForeground(new Color(100, 116, 139));
        sectionTitle.setAlignmentX(0f);
        moodSummaryContent.add(sectionTitle);

        if (mood == null) {
            JLabel emptyLabel = new JLabel("Belum ada mood dicatat");
            emptyLabel.setFont(FontManager.getPoppins(13f).deriveFont(Font.BOLD));
            emptyLabel.setForeground(new Color(30, 41, 59));
            emptyLabel.setAlignmentX(0f);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 10, 0));
            moodSummaryContent.add(emptyLabel);
        } else {
            JLabel moodLine = new JLabel(mood.getMoodEmoji() + "  " + mood.getMoodLabel());
            moodLine.setFont(FontManager.getPoppins(15f).deriveFont(Font.BOLD));
            moodLine.setForeground(new Color(30, 41, 59));
            moodLine.setAlignmentX(0f);
            moodLine.setBorder(BorderFactory.createEmptyBorder(4, 0, 6, 0));
            moodSummaryContent.add(moodLine);

            if (mood.getCatatan() != null && !mood.getCatatan().trim().isEmpty()) {
                JLabel noteText = new JLabel("“" + mood.getCatatan() + "”");
                noteText.setFont(FontManager.getPoppins(12f));
                noteText.setForeground(new Color(51, 65, 85));
                noteText.setAlignmentX(0f);
                noteText.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
                moodSummaryContent.add(noteText);
            }
        }

        RoundedButton fillMoodButton = new RoundedButton("Isi Mood Hari Ini");
        fillMoodButton.setCornerRadius(10);
        fillMoodButton.setFont(FontManager.getPoppins(12f).deriveFont(Font.BOLD));
        fillMoodButton.setAlignmentX(0f);
        fillMoodButton.setPreferredSize(new Dimension(160, 36));
        fillMoodButton.setMaximumSize(new Dimension(160, 36));
        fillMoodButton.setBackground(new Color(137, 126, 255));
        fillMoodButton.setForeground(Color.WHITE);
        fillMoodButton.addActionListener(e -> {
            Mood moodWindow = new Mood();
            moodWindow.setVisible(true);
            dispose();
        });

        moodSummaryContent.add(Box.createVerticalStrut(2));
        moodSummaryContent.add(fillMoodButton);
        moodSummaryContent.revalidate();
        moodSummaryContent.repaint();
    }

    private void renderMoodSummaryError() {
        moodSummaryContent.removeAll();
        JLabel errorLabel = new JLabel("Gagal memuat mood terakhir");
        errorLabel.setFont(FontManager.getPoppins(12f));
        errorLabel.setForeground(new Color(100, 116, 139));
        errorLabel.setAlignmentX(0f);
        moodSummaryContent.add(errorLabel);
        moodSummaryContent.revalidate();
        moodSummaryContent.repaint();
    }

    private void renderRecommendationsLoading() {
        recommendationsContent.removeAll();
        JLabel loading = new JLabel("Memuat rekomendasi...");
        loading.setFont(FontManager.getPoppins(12f));
        loading.setForeground(new Color(100, 116, 139));
        recommendationsContent.add(loading, "span");
        recommendationsContent.revalidate();
        recommendationsContent.repaint();
    }

    private void renderRecommendations(List<Recommendation> recommendations) {
        recommendationsContent.removeAll();
        if (recommendations == null || recommendations.isEmpty()) {
            recommendationsContent.setLayout(new BorderLayout());
            recommendationsContent.add(new EmptyStatePanel("🌱", "Belum ada rekomendasi", "Coba lagi nanti"), BorderLayout.CENTER);
        } else {
            recommendationsContent.setLayout(new MigLayout("insets 0, gap 12", "[grow,fill][grow,fill]"));
            int i = 0;
            for (Recommendation rec : recommendations) {
                RecommendationCard card = new RecommendationCard(rec);
                card.setOnOpenDetail(() -> {
                    RecommendationDetailDialog dialog = new RecommendationDetailDialog(this, rec);
                    dialog.setVisible(true);
                });
                String constraint = (i % 2 == 1) ? "wrap" : "";
                recommendationsContent.add(card, constraint);
                if (++i >= 2) break; // Membatasi maks 2 card agar pas dalam layout
            }
        }
        recommendationsContent.revalidate();
        recommendationsContent.repaint();
    }

    private void renderRecommendationsError() {
        recommendationsContent.removeAll();
        recommendationsContent.setLayout(new BorderLayout());
        recommendationsContent.add(new EmptyStatePanel("⚠️", "Gagal memuat rekomendasi", "Periksa koneksi"), BorderLayout.CENTER);
        recommendationsContent.revalidate();
        recommendationsContent.repaint();
    }

    private RoundedPanel buildQuickActionCard(String iconPath, String label, Runnable onClick) {
        RoundedPanel card = new RoundedPanel(14, Color.WHITE, true);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(30, 10, 30, 10)
        ));
        card.setPreferredSize(new Dimension(150, 95));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        // --- RENDER ICON GAMBAR DARI ASSET ---
        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setAlignmentX(0.5f);

        try {
            java.net.URL imgURL = getClass().getClassLoader().getResource(iconPath);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                // Skala 24x24 atau 28x28 agar pas di tengah card aslimu tanpa bikin gepeng
                Image scaledImg = originalIcon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                iconLabel.setText("✨"); // Fallback jika path tidak ditemukan
                iconLabel.setFont(FontManager.getPoppins(22f));
            }
        } catch (Exception e) {
            iconLabel.setText("✨");
            iconLabel.setFont(FontManager.getPoppins(22f));
        }

        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);
        textLabel.setFont(FontManager.getPoppins(12f).deriveFont(Font.BOLD));
        textLabel.setForeground(new Color(30, 41, 59));
        textLabel.setAlignmentX(0.5f);
        textLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        inner.add(iconLabel);
        inner.add(textLabel);
        card.add(inner, BorderLayout.CENTER);
        
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                onClick.run();
            }
        });
        return card;
    }
    
    private void renderMoodSummaryLocal(Mood mood) {
        moodSummaryContent.removeAll();

        JLabel sectionTitle = new JLabel("Mood terakhir");
        sectionTitle.setFont(FontManager.getPoppins(11f));
        sectionTitle.setForeground(new Color(100, 116, 139));
        sectionTitle.setAlignmentX(0f);
        moodSummaryContent.add(sectionTitle);

        if (mood == null) {
            JLabel emptyLabel = new JLabel("Belum ada mood dicatat");
            emptyLabel.setFont(FontManager.getPoppins(13f).deriveFont(Font.BOLD));
            emptyLabel.setForeground(new Color(30, 41, 59));
            emptyLabel.setAlignmentX(0f);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 10, 0));
            moodSummaryContent.add(emptyLabel);
        } else {
            int tingkat = mood.getTingkatMood();
            String label = "Senang";
            
            if (tingkat == 1) label = "Sedih";
            else if (tingkat == 2) label = "Murung";
            else if (tingkat == 3) label = "Marah";
            else if (tingkat == 4) label = "Senang";
            else if (tingkat == 5) label = "Ceria";

            // 1. BUAT LABEL UNTUK MENAMPUNG ICON GAMBAR PURPLE PNG
            JLabel iconLabel = new JLabel();
            try {
                // Mengambil file asset mood kustom ungu secara dinamis (mood1-purple.png s.d mood5-purple.png)
                java.net.URL imgURL = getClass().getClassLoader().getResource("Asset/aset-utama/mood" + tingkat + "-purple.png");
                if (imgURL != null) {
                    iconLabel.setIcon(new javax.swing.ImageIcon(imgURL));
                } else {
                    iconLabel.setText("• "); // Fallback jika gambar tidak ditemukan
                }
            } catch (Exception e) {
                iconLabel.setText("");
            }

            // 2. KELOMPOKKAN ICON DAN TEKS MOOD AGAR BERJEJER HORIZONTAL
            JPanel moodLinePanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));
            moodLinePanel.setOpaque(false);
            moodLinePanel.setAlignmentX(0f);
            moodLinePanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 6, 0));

            JLabel moodTextLabel = new JLabel(label);
            moodTextLabel.setFont(FontManager.getPoppins(15f).deriveFont(Font.BOLD));
            moodTextLabel.setForeground(new Color(30, 41, 59));

            // Masukkan icon gambar dan teks nama mood ke dalam panel baris
            moodLinePanel.add(iconLabel);
            moodLinePanel.add(moodTextLabel);
            moodSummaryContent.add(moodLinePanel);
            
            if (mood.getCatatan() != null && !mood.getCatatan().trim().isEmpty()) {
                JLabel noteText = new JLabel("“" + mood.getCatatan() + "”");
                noteText.setFont(FontManager.getPoppins(12f));
                noteText.setForeground(new Color(51, 65, 85));
                noteText.setAlignmentX(0f);
                noteText.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
                moodSummaryContent.add(noteText);
            }
        }

        RoundedButton fillMoodButton = new RoundedButton("Isi Mood Hari Ini");
        fillMoodButton.setCornerRadius(10);
        fillMoodButton.setFont(FontManager.getPoppins(12f).deriveFont(Font.BOLD));
        fillMoodButton.setAlignmentX(0f);
        fillMoodButton.setPreferredSize(new Dimension(160, 36));
        fillMoodButton.setMaximumSize(new Dimension(160, 36));
        fillMoodButton.setBackground(new Color(137, 126, 255));
        fillMoodButton.setForeground(Color.WHITE);
        fillMoodButton.addActionListener(e -> {
            senandika.UILayer.Mood moodWindow = new senandika.UILayer.Mood();
            moodWindow.setVisible(true);
            dispose();
        });

        moodSummaryContent.add(Box.createVerticalStrut(2));
        moodSummaryContent.add(fillMoodButton);
        moodSummaryContent.revalidate();
        moodSummaryContent.repaint();
    }

/**
 * Mengadaptasi List ActivitySuggestion milik Anda agar tampil pas pada layout Home
 */
private void renderRecommendationsLocal(List<senandika.UILayer.ActivitySuggestion> suggestions) {
    recommendationsContent.removeAll();
    if (suggestions == null || suggestions.isEmpty()) {
        recommendationsContent.setLayout(new BorderLayout());
        // Menggunakan EmptyState default jika list data aktivitas masih kosong
        recommendationsContent.add(new EmptyStatePanel("🌱", "Belum ada rekomendasi", "Aktivitas akan muncul di sini"), BorderLayout.CENTER);
    } else {
        recommendationsContent.setLayout(new MigLayout("insets 0, gap 12", "[grow,fill][grow,fill]"));
        int i = 0;
        for (senandika.UILayer.ActivitySuggestion activity : suggestions) {
            // Membuat model penampung sementara agar RecommendationCard Claude tidak error saat dibaca
            senandika.Model.Recommendation bridgeModel = new senandika.Model.Recommendation();
            bridgeModel.setTitle(activity.getName()); // Mengambil properti nama/judul dari ActivitySuggestion Anda
            bridgeModel.setDescription("Rekomendasi aktivitas sehat untuk relaksasi pikiran Anda.");
            
            RecommendationCard card = new RecommendationCard(bridgeModel);
            card.setOnOpenDetail(() -> {
                RecommendationDetailDialog dialog = new RecommendationDetailDialog(this, bridgeModel);
                dialog.setVisible(true);
            });
            String constraint = (i % 2 == 1) ? "wrap" : "";
            recommendationsContent.add(card, constraint);
            if (++i >= 2) break; // Batasi maksimal 2 item agar seimbang secara visual
        }
    }
    recommendationsContent.revalidate();
    recommendationsContent.repaint();
}

private void generateDynamicSuggestions(int tingkatMood, JPanel container) {
    container.removeAll();
    
    // Matriks data kegiatan dinamis: {Judul, Deskripsi Singkat di Home, Instruksi Detail di Dialog, Path Gambar, LANGKAH AKTIVITAS}
    String[][] dataRekomendasi;
    
    switch (tingkatMood) {
        case 1: // Sedih
            dataRekomendasi = new String[][]{
                {
                    "Menulis Katarsis", 
                    "Tumpahkan emosi sedihmu dalam jurnal tanpa filter.", 
                    "Tulis apa pun yang membuat dadamu sesak selama beberapa menit tanpa berhenti untuk membaca ulang. Biarkan emosimu mengalir bebas.", 
                    "Asset/halaman/activitySuggestion/katarsis.jpg",
                    "Ambil posisi duduk yang tenang dan buka halaman jurnal baru di Senandika.;Tuliskan semua hal yang mengganjal di pikiranmu secara jujur tanpa perlu diedit atau dibaca ulang.;Biarkan air mata atau emosimu keluar mengalir bersama tulisanmu.;Setelah selesai, tarik napas dalam-dalam dan lepaskan beban tersebut perlahan."
                },
                {
                    "Mendengarkan Musik", 
                    "Dengarkan lagu instrumen penenang suasana hati.", 
                    "Gunakan headphone, atur volume ke tingkat nyaman, pejamkan mata, dan dengarkan melodi instrumen akustik perlahan untuk menurunkan ketegangan.", 
                    "Asset/halaman/activitySuggestion/musik_tenang.jpg",
                    "Gunakan headphone atau earphone demi fokus yang lebih baik.;Pilihlah daftar putar lagu instrumen atau melodi akustik yang menenangkan.;Pejamkan mata dan pusatkan perhatian penuh pada ketukan serta alun musiknya.;Bernapaslah dengan teratur mengikuti ritme instrumen hingga detak jantung terasa lebih stabil."
                },
                {
                    "Latihan Afirmasi", 
                    "Tatap cermin dan ucapkan kalimat penerimaan diri.", 
                    "Berdirilah di depan cermin, tatap matamu sendiri, tarik napas sedalam mungkin, dan ingatkan dirimu bahwa perasaanmu saat ini adalah valid.", 
                    "Asset/halaman/activitySuggestion/afirmasi.jpg",
                    "Berdiri atau duduk dengan tegak di depan cermin terdekat.;Tatap matamu sendiri dengan pandangan yang lembut dan penuh penerimaan.;Tarik napas sedalam mungkin dari hidung, lalu embuskan perlahan.;Ucapkan dengan lirih namun tegas: 'Aku menerima perasaanku hari ini, tidak apa-apa untuk merasa lelah. Besok akan lebih baik.';"
                }
            };
            break;

        case 2: // Murung
            dataRekomendasi = new String[][]{
                {
                    "Jalan Santai 10 Menit", 
                    "Hirup udara segar di luar ruangan tanpa gawai.", 
                    "Tinggalkan laptop dan ponselmu di kamar sejenak. Berjalanlah keluar rumah untuk merasakan udara luar dan menyegarkan pikiran.", 
                    "Asset/halaman/activitySuggestion/jalan_santai.jpg",
                    "Simpan ponsel dan tinggalkan laptopmu di atas meja kamar.;Pakai alas kaki yang nyaman lalu melangkahlah keluar ruangan atau pekarangan rumah.;Berjalanlah dengan ritme santai selama kurang lebih 10 menit.;Perhatikan objek di sekitarmu seperti daun, langit, atau angin yang menyentuh kulit."
                },
                {
                    "Mindful Tea Sipping", 
                    "Seduh teh hangat kesukaanmu secara sadar.", 
                    "Rasakan kehangatan cangkirnya di tanganmu, hirup aromanya, lalu sesap perlahan tanpa memikirkan tugas kuliah atau pekerjaan.", 
                    "Asset/halaman/activitySuggestion/minum_teh.jpg",
                    "Seduh secangkir teh hangat kesukaanmu (seperti chamomile atau melati).;Pegang cangkir dengan kedua tangan dan rasakan sensasi hangatnya mengalir ke telapak tangan.;Dekatkan cangkir ke wajah, lalu hirup aroma teh yang menenangkan dalam-dalam.;Sesap teh tersebut sedikit demi sedikit secara perlahan sambil menikmati rasanya tanpa distraksi."
                },
                {
                    "Terapi Hijau Visual", 
                    "Manjakan mata dengan memandang tanaman terdekat.", 
                    "Cari jendela atau keluar ruangan, tatap tanaman atau pohon hijau terdekat selama beberapa menit untuk menyegarkan saraf visualmu.", 
                    "Asset/halaman/activitySuggestion/terapi_hijau.jpg",
                    "Posisikan dirimu berdiri di dekat jendela atau area terbuka luar ruangan.;Cari objek tanaman atau pepohonan hijau yang berada di sekitar pandanganmu.;Tatap dedaunan hijau tersebut secara fokus selama 3 hingga 5 menit.;Biarkan saraf matamu beristirahat dari paparan cahaya biru layar laptop atau komputer."
                }
            };
            break;

        case 3: // Marah
            dataRekomendasi = new String[][]{
                {
                    "Peregangan Otot Leher", 
                    "Lenturkan otot pundak dan leher yang tegang.", 
                    "Tarik napas dalam-dalam untuk mengendurkan ketegangan emosional dan kaku pada saraf batin yang kaku akibat emosi.", 
                    "Asset/halaman/activitySuggestion/peregangan.jpg",
                    "Duduk tegak, rebahkan kepala ke pundak kanan perlahan, tahan 5 detik, lalu balas ke kiri.;Ulangi gerakan ini sebanyak 3 kali atau sampai ketegangan saraf batin mereda."
                },
                {
                    "Hitung Mundur Fokus", 
                    "Duduk rileks dan hitung mundur perlahan dari 100.", 
                    "Pejamkan mata dan berfokuslah secara murni pada deret angka yang kamu bayangkan di dalam pikiranmu tersebut.", 
                    "Asset/halaman/activitySuggestion/hitung_mundur.jpg",
                    "Posisikan tubuh duduk tegak bersandar.;Lakukan hitung mundur secara perlahan dan lantang di dalam hati.;Fokuskan pikiranmu murni pada deret angka tersebut.;Jika pikiranmu teralihkan, kembalilah fokus pada hitungan mundurnya."
                },
                {
                    "Sensasi Air Dingin", 
                    "Teguk segelas air es secara perlahan.", 
                    "Rasakan sensasi dingin yang mengalir menuruni tenggorokan secara fisik untuk meredakan amarah secara fisik.", 
                    "Asset/halaman/activitySuggestion/air_dingin.jpg",
                    "Teguk segelas air es secara perlahan.;Fokuskan pikiranmu pada rasa dingin tersebut.;Sesapi setiap tegukannya hingga rasa maramu perlahan memudar."
                }
            };
            break;

        case 4: // Senang
            dataRekomendasi = new String[][]{
                {
                    "Jurnal Syukur Kecil", 
                    "Catat 3 hal berharga yang terjadi hari ini.", 
                    "Buka menu jurnal Senandika, abadikan momen sederhana yang membuatmu merasa beruntung atau tersenyum hari ini.", 
                    "Asset/halaman/activitySuggestion/jurnal_syukur.jpg",
                    "Buka lembar catatan atau fitur jurnal baru di dalam aplikasi Senandika.;Pikirkan kembali kejadian-kejadian yang kamu alami sejak bangun tidur pagi tadi.;Tuliskan 3 hal sederhana yang paling kamu syukuri (misal: cuaca cerah, berpapasan dengan teman, makan enak).;Baca kembali tulisan tersebut sambil tersenyum untuk mengunci rasa bahagiamu."
                },
                {
                    "Apresiasi Sahabat", 
                    "Kirim pesan teks terima kasih singkat ke teman dekat.", 
                    "Sapa salah satu teman terbaikmu dan katakan terima kasih singkat karena dia telah menjadi teman bincang yang hebat.", 
                    "Asset/halaman/activitySuggestion/pesan_teman.jpg",
                    "Buka aplikasi perpesanan di ponsel pintarmu.;Pilih ruang obrolan salah satu sahabat atau teman terdekatmu.;Ketikkan pesan apresiasi tulus singkat (misal: 'Hey, makasih ya udah selalu seru diajak ngobrol selama ini!');Kirimkan pesan tersebut dan biarkan energi positifmu menular ke lingkaran pertemananmu."
                },
                {
                    "Hobi Ekspresif 15m", 
                    "Salurkan energi senangmu ke aktivitas hobi kreatif.", 
                    "Gunakan waktu sejenak untuk melakukan hal yang paling kamu sukai, seperti mendengarkan lagu rock favorit atau menggambar sketsa.", 
                    "Asset/halaman/activitySuggestion/hobi_senang.jpg",
                    "Siapkan peralatan atau media hobi favoritmu (buku komik, kertas sketsa, atau instrumen musik).;Atur alarm atau pengingat waktu selama 15 menit ke depan.;Lakukan aktivitas hobi tersebut dengan penuh suka cita tanpa memikirkan beban tugas kuliah.;Rasakan dorongan semangat kreatif yang mengalir di dalam dirimu."
                }
            };
            break;

        case 5: // Ceria
            dataRekomendasi = new String[][]{
                {
                    "Perencanaan Esok Seru", 
                    "Susun target menarik yang ingin dieksekusi besok.", 
                    "Manfaatkan momentum motivasi yang sedang melimpah ini untuk menulis daftar rencana seru atau tugas penting esok hari.", 
                    "Asset/halaman/activitySuggestion/rencana_esok.jpg",
                    "Ambil buku catatan kecil atau catatan digital di laptopmu.;Tuliskan 3 target utama paling menarik dan menantang yang ingin kamu taklukkan esok hari.;Breakdown target tersebut menjadi langkah kecil agar mudah dieksekusi.;Simpan daftarnya di tempat yang mudah terlihat saat kamu bangun tidur esok pagi."
                },
                {
                    "Olahraga Kardio Ringan", 
                    "Gunakan energi ceriamu untuk menggerakkan tubuh.", 
                    "Lakukan stretching dinamis, lompat tali, atau gerakan workout ringan selama 10 menit agar hormon endorfin tetap lancar.", 
                    "Asset/halaman/activitySuggestion/olahraga_ceria.jpg",
                    "Ganti pakaianmu dengan pakaian olahraga yang longgar dan nyaman.;Putar lagu dengan ketukan bersemangat (*upbeat*) untuk memicu energi tubuh.;Lakukan pemanasan dinamis dilanjutkan gerakan kardio ringan (seperti jumping jack atau skipping) selama 10 menit.;Rasakan kesegaran fisik yang melimpah setelah keringat keluar."
                },
                {
                    "Membaca Wawasan Baru", 
                    "Buka bab baru dari buku pengembangan diri favoritmu.", 
                    "Pikiranmu saat ini sedang berada dalam mode paling terbuka. Gunakan untuk menyerap halaman wawasan berharga dari buku bacaan.", 
                    "Asset/halaman/activitySuggestion/baca_buku.jpg",
                    "Ambil satu buku non-fiksi atau artikel pengembangan diri yang sedang ingin kamu pelajari.;Cari posisi duduk yang nyaman dengan pencahayaan ruangan yang cukup terang.;Bacalah 1 hingga 2 bab baru secara fokus dan saksama.;Catat kutipan atau poin berharga yang paling menginspirasimu ke dalam jurnal."
                }
            };
            break;

        default:
            return;
    }

    // Loop pembuatan card
    for (String[] recData : dataRekomendasi) {
        senandika.Model.Recommendation recModel = new senandika.Model.Recommendation();
        recModel.setTitle(recData[0]);
        // Teks deskripsi di Home dipotong agar card tidak memanjang ke bawah
        recModel.setDescription(recData[1]); 
        recModel.setThumbnailUrl(recData[3]);
        
        RecommendationCard card = new RecommendationCard(recModel);
        
        // Listener aksi ketika tombol "Mulai" atau area Card diklik
        card.setOnOpenDetail(() -> {
            // Kita timpa deskripsi model sementara menggunakan instruksi detail (recData[2]) agar muncul lengkap di dialog
            senandika.Model.Recommendation detailModel = new senandika.Model.Recommendation();
            detailModel.setTitle(recData[0]);
            detailModel.setDescription(recData[2]); 
            // Ambil langkah-langkah asli dari kolom matriks baru (Pemisah ;)
            detailModel.setSteps(recData[4].split(";")); 
            detailModel.setThumbnailUrl(recData[3]);
            
            // Panggil dialog detail aslimu
            RecommendationDetailDialog dialog = new RecommendationDetailDialog(this, detailModel);
            dialog.setVisible(true);
        });

        container.add(card);
        // Jarak 12px antar kartu (Padding Kanan otomatis)
        container.add(Box.createHorizontalStrut(12)); 
    }
}

    // -----------------------------------------------------------------
    // Async Data Load Callbacks
    // -----------------------------------------------------------------
    private void loadAllData() {
    // 1. Ambil Data Profil Pengguna (Sinkronus - String JSON)
    try {
        String profileJson = profileService.getProfile(); 

        if (profileJson != null && !profileJson.trim().isEmpty() && !profileJson.startsWith("HTTP")) {
            com.google.gson.JsonObject jsonNodeUtama = com.google.gson.JsonParser.parseString(profileJson).getAsJsonObject();
            
            if (jsonNodeUtama.has("data") && !jsonNodeUtama.get("data").isJsonNull()) {
                com.google.gson.JsonObject json = jsonNodeUtama.getAsJsonObject("data");
                
                String displayName = "";
                
                // Ambil full_name atau username dari dalam objek data
                if (json.has("full_name") && !json.get("full_name").isJsonNull()) {
                    displayName = json.get("full_name").getAsString();
                } else if (json.has("username") && !json.get("username").isJsonNull()) {
                    displayName = json.get("username").getAsString();
                }
                
                if (!displayName.isEmpty()) {
                    greeting.setText("<html>Halo, " + displayName + "<br>senang melihatmu hari ini ^_^</html>");
                }
            }
        }
    } catch (Exception e) {
        System.out.println("Gagal memuat profil: " + e.getMessage());
    }

    // 2. Ambil Data Mood Terakhir (Menggunakan data lokal List MoodService)
    try {
        List<Mood> moodHistory = moodService.getMoodHistory();
        
        if (moodHistory != null && !moodHistory.isEmpty()) {
            moodHistory.sort((m1, m2) -> Integer.compare(m2.getId(), m1.getId()));
            
            Mood latestMood = moodHistory.get(0); 
            
            renderMoodSummaryLocal(latestMood);
            dailyInsightCard.updateByMood(latestMood.getTingkatMood());
            
            // =====================================================================
            // PERBAIKAN UTAMA: Hubungkan pemicu pengisian card dinamis di sini!
            // =====================================================================
            generateDynamicSuggestions(latestMood.getTingkatMood(), recommendationsContent);
            
        } else {
            renderMoodSummaryLocal(null);
            dailyInsightCard.updateByMood(4);
            
            // Fallback jika database kosong (Tingkat mood 4 = Senang)
            generateDynamicSuggestions(4, recommendationsContent);
        }

        // Paksa UI kontainer untuk merender ulang isi card baru ke layar laptopmu
        recommendationsContent.revalidate();
        recommendationsContent.repaint();

    } catch (Exception e) {
        renderMoodSummaryError();
    }

    // 4. Ambil Streak Journal (Sinkronus - mengembalikan int)
    try {
        int streak = journalService.getStreak();
        wellnessProgressCard.setStreak(streak);
    } catch (Exception e) {
        System.out.println("Gagal memuat streak: " + e.getMessage());
        wellnessProgressCard.setUnavailable();
    }
}
 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        content = new javax.swing.JPanel();
        navbar_panel = new javax.swing.JPanel();
        mood_nav = new javax.swing.JLabel();
        jurnal_nav = new javax.swing.JLabel();
        profile_nav = new javax.swing.JLabel();
        navbar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setBorder(null);

        content.setBackground(new java.awt.Color(246, 255, 248));
        content.setPreferredSize(new java.awt.Dimension(398, 550));
        content.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(content);

        navbar_panel.setBackground(new java.awt.Color(246, 255, 248));
        navbar_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mood_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mood_navMouseClicked(evt);
            }
        });
        navbar_panel.add(mood_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 60, 50));

        jurnal_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jurnal_navMouseClicked(evt);
            }
        });
        navbar_panel.add(jurnal_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 60, 50));

        profile_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profile_navMouseClicked(evt);
            }
        });
        navbar_panel.add(profile_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 60, 60));

        navbar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/component/navbar/nav-home.png"))); // NOI18N
        navbar_panel.add(navbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -8, -1, 110));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(navbar_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navbar_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mood_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mood_navMouseClicked
        senandika.UILayer.Mood mood = new senandika.UILayer.Mood();
        mood.setVisible(true);
        dispose();
    }//GEN-LAST:event_mood_navMouseClicked

    private void jurnal_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jurnal_navMouseClicked
        Journal jurnal = new Journal();
        jurnal.setVisible(true);
        dispose();
    }//GEN-LAST:event_jurnal_navMouseClicked

    private void profile_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profile_navMouseClicked
        Profile profile = new Profile();
        profile.setVisible(true);
        dispose();
    }//GEN-LAST:event_profile_navMouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jurnal_nav;
    private javax.swing.JLabel mood_nav;
    private javax.swing.JLabel navbar;
    private javax.swing.JPanel navbar_panel;
    private javax.swing.JLabel profile_nav;
    // End of variables declaration//GEN-END:variables
}
