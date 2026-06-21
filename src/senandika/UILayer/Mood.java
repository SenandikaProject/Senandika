/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package senandika.UILayer;

import Components.Home_Component.EmptyStatePanel;
import Components.Mood_Component.MoodButton;
import Components.Mood_Component.MoodDistributionChart;
import Components.Mood_Component.MoodHistoryCard;
import Components.Mood_Component.MoodStatisticsCard;
import Components.RoundedButton;
import Components.RoundedPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import senandika.FontManager;
import senandika.ServiceLayer.MoodService;

/**
 *
 * @author SAHABAT-IT
 */
public class Mood extends javax.swing.JFrame {
    private String idMood;
    private LocalDate tanggal;
    private int tingkatMood;
    private String catatan;
    private String userId;

    // Instance Service Layer data lokal
    private final MoodService moodService = new MoodService();
    private final List<MoodButton> moodButtons = new ArrayList<>();
    private int selectedMoodLevel = 0;

    // Deklarasi Elemen Konten Kustom
    private JLabel selectedMoodText;
    private JTextArea notesArea;
    private RoundedButton submitButton;
    private JLabel submitStatusLabel;
    private MoodStatisticsCard avgCard;
    private MoodStatisticsCard highCard;
    private MoodStatisticsCard lowCard;
    private JPanel historyContent;
    private MoodDistributionChart distributionChart;

    public Mood(String idMood, LocalDate tanggal, int tingkatMood, String catatan, String userId) {
        this.idMood = idMood;
        this.tanggal = tanggal;
        this.tingkatMood = tingkatMood;
        this.catatan = catatan;
        this.userId = userId;
    }

    public void saveMood() {
        System.out.println("Mood berhasil disimpan!");
    }

    public int getTingkatMood() {
        return tingkatMood;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public String getIdMood() {
        return idMood;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getUserId() {
        return userId;
    }
    
    public Mood() {
        initComponents();
        initUI();
        loadAllMoodData();
    }

    private void initUI() {
        setTitle("Senandika - Mood Tracker");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menata ulang panel content bentukan NetBeans ke AbsoluteLayout dinamis
        content = new JPanel();
        content.setBackground(new Color(246, 255, 248));
        content.setOpaque(true);
        content.setLayout(new AbsoluteLayout());

        jScrollPane1.setViewportView(content);
        jScrollPane1.getViewport().setBackground(new Color(246, 255, 248));
        jScrollPane1.setBorder(null);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);

        int currentY = 30;

        // ---- 1. TITLE SECTION ----
        JLabel titleLabel = new JLabel("Bagaimana perasaanmu hari ini?");
        titleLabel.setFont(FontManager.getPoppins(20f).deriveFont(Font.BOLD));
        titleLabel.setForeground(new Color(30, 41, 59));
        content.add(titleLabel, new AbsoluteConstraints(24, currentY, 340, 30));
        
        JLabel subtitleLabel = new JLabel("Luangkan waktu sejenak untuk mengenali emosimu.");
        subtitleLabel.setFont(FontManager.getPoppins(12f));
        subtitleLabel.setForeground(new Color(100, 116, 139));
        content.add(subtitleLabel, new AbsoluteConstraints(24, currentY + 30, 340, 20));
        currentY += 65;

        // ---- 2. MOOD SELECTOR CARD ----
        RoundedPanel selectorCard = new RoundedPanel(16, Color.WHITE, true);
        selectorCard.setLayout(new BorderLayout());
        selectorCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(16, 12, 16, 12)
        ));

        JPanel buttonsRow = new JPanel(new MigLayout("insets 0, align center", "[]6[]6[]6[]6[]"));
        buttonsRow.setOpaque(false);

        int[] levels = {1, 2, 3, 4, 5};
        for (int i = 0; i < levels.length; i++) {
            MoodButton button = new MoodButton(levels[i], getEmojiFor(levels[i]));
            button.setOnSelect(this::selectMood);
            moodButtons.add(button);
            buttonsRow.add(button);
        }

        selectedMoodText = new JLabel("Silakan pilih emosimu di atas", SwingConstants.CENTER);
        selectedMoodText.setFont(FontManager.getPoppins(13f).deriveFont(Font.BOLD));
        selectedMoodText.setForeground(new Color(137, 126, 255));
        selectedMoodText.setBorder(new EmptyBorder(12, 0, 0, 0));

        selectorCard.add(buttonsRow, BorderLayout.CENTER);
        selectorCard.add(selectedMoodText, BorderLayout.SOUTH);
        content.add(selectorCard, new AbsoluteConstraints(24, currentY, 344, 130));
        currentY += 145;

        // ---- 3. MOOD NOTES CARD ----
        RoundedPanel notesCard = new RoundedPanel(16, Color.WHITE, true);
        notesCard.setLayout(new BorderLayout(0, 8));
        notesCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        JLabel notesPrompt = new JLabel("Apa yang membuatmu merasa seperti ini?");
        notesPrompt.setFont(FontManager.getPoppins(13f).deriveFont(Font.BOLD));
        notesPrompt.setForeground(new Color(71, 85, 105));

        notesArea = new JTextArea(3, 20);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setFont(FontManager.getPoppins(13f));
        notesArea.setBackground(new Color(250, 250, 250));
        notesArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        notesCard.add(notesPrompt, BorderLayout.NORTH);
        notesCard.add(notesArea, BorderLayout.CENTER);
        content.add(notesCard, new AbsoluteConstraints(24, currentY, 344, 120));
        currentY += 135;

        // ---- 4. SUBMIT BUTTON SECTION ----
        submitButton = new RoundedButton("Simpan Mood");
        submitButton.setCornerRadius(12);
        submitButton.setFont(FontManager.getPoppins(14f).deriveFont(Font.BOLD));
        submitButton.setBackground(new Color(137, 126, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setEnabled(false);
        submitButton.addActionListener(e -> executeSubmitMood());
        content.add(submitButton, new AbsoluteConstraints(24, currentY, 344, 44));

        submitStatusLabel = new JLabel(" ");
        submitStatusLabel.setFont(FontManager.getPoppins(12f));
        submitStatusLabel.setForeground(new Color(100, 116, 139));
        content.add(submitStatusLabel, new AbsoluteConstraints(26, currentY + 48, 340, 20));
        currentY += 75;

        // ---- 5. STATISTICS SECTION ----
        JLabel statsTitle = new JLabel("Statistik Mood");
        statsTitle.setFont(FontManager.getPoppins(15f).deriveFont(Font.BOLD));
        statsTitle.setForeground(new Color(30, 41, 59));
        content.add(statsTitle, new AbsoluteConstraints(28, currentY, 340, 25));
        currentY += 30;

        JPanel gridStats = new JPanel(new MigLayout("insets 0, gap 10", "[grow,fill][grow,fill][grow,fill]"));
        gridStats.setOpaque(false);
        avgCard = new MoodStatisticsCard("⭐", "Rata-rata");
        highCard = new MoodStatisticsCard("📈", "Tertinggi");
        lowCard = new MoodStatisticsCard("📉", "Terendah");
        gridStats.add(avgCard);
        gridStats.add(highCard);
        gridStats.add(lowCard);
        content.add(gridStats, new AbsoluteConstraints(24, currentY, 344, 80));
        currentY += 95;

        // ---- 6. HISTORY SECTION ----
        JLabel historyTitle = new JLabel("Riwayat Mood");
        historyTitle.setFont(FontManager.getPoppins(15f).deriveFont(Font.BOLD));
        historyTitle.setForeground(new Color(30, 41, 59));
        content.add(historyTitle, new AbsoluteConstraints(28, currentY, 340, 25));
        currentY += 30;

        historyContent = new JPanel();
        historyContent.setOpaque(false);
        historyContent.setLayout(new BoxLayout(historyContent, BoxLayout.Y_AXIS));
        content.add(historyContent, new AbsoluteConstraints(24, currentY, 344, -1)); // Mengembang sesuai data
        
        // Sengaja ditumpuk di koordinat aman di bawah list riwayat yang nanti dikalkulasi ulang
        int chartYAnchor = currentY + 120; 

        // ---- 7. DISTRIBUTION SECTION ----
        JLabel distTitle = new JLabel("Distribusi Mood");
        distTitle.setFont(FontManager.getPoppins(15f).deriveFont(Font.BOLD));
        distTitle.setForeground(new Color(30, 41, 59));
        content.add(distTitle, new AbsoluteConstraints(28, chartYAnchor, 340, 25));

        RoundedPanel chartCard = new RoundedPanel(16, Color.WHITE, true);
        chartCard.setLayout(new BorderLayout());
        chartCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        distributionChart = new MoodDistributionChart();
        chartCard.add(distributionChart, BorderLayout.CENTER);
        content.add(chartCard, new AbsoluteConstraints(24, chartYAnchor + 30, 344, 150));

        // Set tinggi awal frame kontainer scroll
        content.setPreferredSize(new Dimension(398, chartYAnchor + 210));
    }

    private void selectMood(int level) {
        selectedMoodLevel = level;
        for (MoodButton button : moodButtons) {
            button.setSelected(button.getLevel() == level);
        }
        selectedMoodText.setText("Hari ini kamu merasa " + getLabelFor(level));
        submitButton.setEnabled(selectedMoodLevel > 0);
    }

    private void executeSubmitMood() {
    if (selectedMoodLevel == 0) return;
    submitButton.setEnabled(false);
    submitStatusLabel.setText("Menyimpan...");

    try {
        String catatanInput = notesArea.getText().trim();
        String tanggalHariIni = LocalDate.now().toString(); // format "yyyy-MM-dd"
        int generateId = (int) (System.currentTimeMillis() / 1000L); // ID integer kustom aman

        // Instansiasi langsung ke Model asli Anda
        senandika.Model.Mood newEntry = new senandika.Model.Mood(
                generateId,
                tanggalHariIni,
                selectedMoodLevel,
                catatanInput
        );

        moodService.addMood(newEntry);
        
        submitStatusLabel.setForeground(new Color(137, 126, 255));
        submitStatusLabel.setText("Mood berhasil disimpan ✓");
        notesArea.setText("");
        
        loadAllMoodData();
    } catch (Exception ex) {
        submitStatusLabel.setForeground(new Color(239, 68, 68));
        submitStatusLabel.setText("Gagal menyimpan data.");
    } finally {
        submitButton.setEnabled(true);
    }
}

    private void loadAllMoodData() {
        // 1. Sinkronisasi Data Riwayat ke Komponen UI
try {
    historyContent.removeAll();
    List<senandika.Model.Mood> listData = moodService.getMoodHistory();
    
    if (listData == null || listData.isEmpty()) {
        historyContent.add(new EmptyStatePanel("📝", "Belum ada riwayat", "Catat mood pertamamu!"));
    } else {
        // Kita bungkus ke model internal Claude untuk mencocokkan component chart & timeline
        List<senandika.Model.Mood> bridgeList = new ArrayList<>();
        for (senandika.Model.Mood m : listData) {
            senandika.Model.Mood bridge = new senandika.Model.Mood();
            bridge.setTingkatMood(m.getTingkatMood());
            
            // Mengonversi data ke String secara aman menggunakan String.valueOf() atau .toString()
            bridge.setTanggal(m.getTanggal() != null ? m.getTanggal().toString() : ""); 
            bridge.setCatatan(m.getCatatan() != null ? m.getCatatan() : ""); 
            
            bridgeList.add(bridge);
        }

        int addedCount = 0;
        for (int i = bridgeList.size() - 1; i >= 0; i--) {
            if (addedCount > 0) historyContent.add(Box.createVerticalStrut(10));
            historyContent.add(new MoodHistoryCard(bridgeList.get(i)));
            if (++addedCount >= 3) break; // Ambil maksimal 3 histori teratas agar proporsional
        }
        
        // Distribusikan data chart
        distributionChart.setMoods(bridgeList);
    }
} catch (Exception e) {
    System.out.println("Gagal memuat histori: " + e.getMessage());
}

    // 2. Kalkulasi Nilai Rata-rata dari Method Sinkronus Anda
    try {
        double avg = moodService.getAverageMood();
        avgCard.setValue(String.format("%.1f", avg));
        
        List<senandika.Model.Mood> data = moodService.getMoodHistory();
        if (data != null && !data.isEmpty()) {
            int max = 1, min = 5;
            for (senandika.Model.Mood m : data) {
                if (m.getTingkatMood() > max) max = m.getTingkatMood();
                if (m.getTingkatMood() < min) min = m.getTingkatMood();
            }
            highCard.setValue(getEmojiFor(max));
            lowCard.setValue(getEmojiFor(min));
        } else {
            highCard.setValue("-");
            lowCard.setValue("-");
        }
    } catch (Exception e) {
        avgCard.setValue("-");
    }

        historyContent.revalidate();
        historyContent.repaint();
    }

    private static String getEmojiFor(int level) {
        switch (level) {
            case 1: return "😢";
            case 2: return "🙁";
            case 3: return "😐";
            case 4: return "🙂";
            case 5: return "😁";
            default: return "😐";
        }
    }

    private static String getLabelFor(int level) {
        switch (level) {
            case 1: return "Sangat Sedih";
            case 2: return "Sedih";
            case 3: return "Biasa Aja";
            case 4: return "Senang";
            case 5: return "Sangat Senang";
            default: return "Biasa Aja";
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        content = new javax.swing.JPanel();
        navbar_panel = new javax.swing.JPanel();
        profil_nav = new javax.swing.JLabel();
        jurnal_nav = new javax.swing.JLabel();
        home_nav = new javax.swing.JLabel();
        navbar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setBorder(null);

        content.setBackground(new java.awt.Color(246, 255, 248));
        content.setPreferredSize(new java.awt.Dimension(398, 550));
        content.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(content);

        navbar_panel.setBackground(new java.awt.Color(246, 255, 248));
        navbar_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        profil_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profil_navMouseClicked(evt);
            }
        });
        navbar_panel.add(profil_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 60, 60));

        jurnal_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jurnal_navMouseClicked(evt);
            }
        });
        navbar_panel.add(jurnal_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 60, 50));

        home_nav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_navMouseClicked(evt);
            }
        });
        navbar_panel.add(home_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 60, 50));

        navbar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/component/navbar/nav-mood.png"))); // NOI18N
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

    private void profil_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profil_navMouseClicked
        Profile profile = new Profile();
        profile.setVisible(true);
        dispose();
    }//GEN-LAST:event_profil_navMouseClicked

    private void jurnal_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jurnal_navMouseClicked
        Journal jurnal = new Journal();
        jurnal.setVisible(true);
        dispose();
    }//GEN-LAST:event_jurnal_navMouseClicked

    private void home_navMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_navMouseClicked
        Home home = new Home();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_home_navMouseClicked

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
            java.util.logging.Logger.getLogger(Mood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mood().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.JLabel home_nav;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jurnal_nav;
    private javax.swing.JLabel navbar;
    private javax.swing.JPanel navbar_panel;
    private javax.swing.JLabel profil_nav;
    // End of variables declaration//GEN-END:variables
}
