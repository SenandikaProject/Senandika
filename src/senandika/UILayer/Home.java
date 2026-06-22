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
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
        
        content.add(moodSummaryCard, new AbsoluteConstraints(24, currentY, 344, 150));
        renderMoodSummaryLoading();
        currentY += 165;

        // ---- 3. DAILY INSIGHT CARD ----
        DailyInsightCard dailyInsightCard = new DailyInsightCard();
        content.add(dailyInsightCard, new AbsoluteConstraints(24, currentY, 344, 110));
        currentY += 125;

        // ---- 4. RECOMMENDATION SECTION ----
        JLabel recTitle = new JLabel("Rekomendasi Untukmu");
        recTitle.setFont(FontManager.getPoppins(15f).deriveFont(Font.BOLD));
        recTitle.setForeground(new Color(30, 41, 59));
        content.add(recTitle, new AbsoluteConstraints(28, currentY, 340, 25));
        currentY += 30;

        recommendationsContent = new JPanel();
        recommendationsContent.setOpaque(false);
        recommendationsContent.setLayout(new MigLayout("insets 0, gap 12", "[grow,fill][grow,fill]"));
        content.add(recommendationsContent, new AbsoluteConstraints(24, currentY, 344, 220));
        renderRecommendationsLoading();
        currentY += 235;

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
        gridActions.add(buildQuickActionCard("📚", "Buat Jurnal", () -> {
            Journal jurnal = new Journal();
            jurnal.setVisible(true);
            dispose();
        }));
        gridActions.add(buildQuickActionCard("😊", "Isi Mood", () -> {
            Mood mood = new Mood();
            mood.setVisible(true);
            dispose();
        }), "wrap");
        gridActions.add(buildQuickActionCard("🌱", "Aktivitas", () -> {
            // Callback menu aktivitas/rekomendasi
        }));
        gridActions.add(buildQuickActionCard("📊", "Statistik", () -> {
            // Callback menu statistik data
        }));
        
        content.add(gridActions, new AbsoluteConstraints(24, currentY, 344, 200));
        currentY += 220;

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

    private RoundedPanel buildQuickActionCard(String icon, String label, Runnable onClick) {
        RoundedPanel card = new RoundedPanel(14, Color.WHITE, true);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(14, 10, 14, 10)
        ));
        card.setPreferredSize(new Dimension(150, 85));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(FontManager.getPoppins(22f));
        iconLabel.setAlignmentX(0.5f);

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
        String emoji = "😐";
        String label = "Biasa Aja";
        
        // Memetakan manual pengganti MoodScale
        if (tingkat == 1) { emoji = "😢"; label = "Sangat Sedih"; }
        else if (tingkat == 2) { emoji = "🙁"; label = "Sedih"; }
        else if (tingkat == 3) { emoji = "😐"; label = "Biasa Aja"; }
        else if (tingkat == 4) { emoji = "🙂"; label = "Senang"; }
        else if (tingkat == 5) { emoji = "😁"; label = "Sangat Senang"; }

        JLabel moodLine = new JLabel(emoji + "  " + label);
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

    // -----------------------------------------------------------------
    // Async Data Load Callbacks
    // -----------------------------------------------------------------
    private void loadAllData() {
    // 1. Ambil Data Profil Pengguna (Sinkronus - String JSON)
    try {
        String profileJson = profileService.getProfile();
        // Cek jika response tidak kosong dan tidak error
        if (profileJson != null && !profileJson.trim().isEmpty() && !profileJson.startsWith("HTTP")) {
            com.google.gson.JsonObject json = com.google.gson.JsonParser.parseString(profileJson).getAsJsonObject();
            
            // Ambil nama dari fullName, jika kosong gunakan username
            String displayName = "";
            if (json.has("fullName") && !json.get("fullName").isJsonNull()) {
                displayName = json.get("fullName").getAsString();
            }
            if (displayName.isEmpty() && json.has("username") && !json.get("username").isJsonNull()) {
                displayName = json.get("username").getAsString();
            }
            
            if (!displayName.isEmpty()) {
                greeting.setText("<html>Halo " + displayName + ",<br>senang melihatmu hari ini ^_^</html>");
            }
        }
    } catch (Exception e) {
        System.out.println("Gagal memuat profil: " + e.getMessage());
    }

    // 2. Ambil Data Mood Terakhir (Menggunakan data lokal List MoodService)
    try {
        List<Mood> moodHistory = moodService.getMoodHistory();
        if (moodHistory != null && !moodHistory.isEmpty()) {
            Mood latestMood = moodHistory.get(moodHistory.size() - 1);
            renderMoodSummaryLocal(latestMood);
        } else {
            renderMoodSummaryLocal(null);
        }
    } catch (Exception e) {
        renderMoodSummaryError();
    }

    // 3. Ambil Rekomendasi Aktivitas (Menggunakan List dari RecomendationService)
    try {
        // Catatan: sesuaikan penulisan nama kelas jika typo (RecomendationService dengan satu 'c')
        List<senandika.UILayer.ActivitySuggestion> suggestions = recommendationService.getRecommendation();
        renderRecommendationsLocal(suggestions);
    } catch (Exception e) {
        renderRecommendationsError();
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
        navbar_panel.add(navbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
