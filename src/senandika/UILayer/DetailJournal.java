
package senandika.UILayer;

import Components.Journal_Component.DetailJournalCard;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;
import senandika.Model.JournalData;
import senandika.ServiceLayer.JournalService;

public class DetailJournal extends javax.swing.JFrame {
    private int journalId;
    private JournalService journalService;
    private DetailJournalCard detailJournalCard;

    public DetailJournal(int journalId) {
        setUndecorated(true); 

        this.journalId = journalId;
        this.journalService = new JournalService();
        
        initComponents();
        setBackground(new Color(0, 0, 0, 0)); // Membuat background frame transparan penuh
        setLocationRelativeTo(null);
        initUI();
        this.pack();
        this.setSize(394, 720);
        fetchDetailData();
    }
    
    private void initUI() {
        // 1. Konfigurasi JScrollPane agar bersih dan tidak bocor secara horizontal
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setBorder(null);
        jScrollPane1.getViewport().setBackground(new Color(246, 255, 248));

        // 2. Setup Container Panel utama
        content.setBackground(new Color(246, 255, 248));
        content.setLayout(new GridBagLayout());
        content.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH; 

        // 3. Pasang Custom Card Panel
        detailJournalCard = new DetailJournalCard();
        
        // Pasangkan Event Listener tombol kembali agar menutup Frame Utama saat ini
        detailJournalCard.getBtnBack().addActionListener(e -> dispose());
        
        content.add(detailJournalCard, gbc);
        
        detailJournalCard.getBtnBack().addActionListener(e -> {
            Journal journal = new Journal();
                journal.setVisible(true);
            dispose();
        });
        // 4. Sinkronisasikan Rendering Engine Swing
        content.revalidate();
        content.repaint();
        this.pack();
        this.setSize(394, 720); // Kunci dimensi frame agar konsisten seperti mockup rekomendasi
    }
    
    private void fetchDetailData() {
        try {
            JournalData journal = journalService.getJournalById(journalId);
            
            if (journal != null) {
                String formattedDate = (journal.getCreatedAt() != null) ? journal.getCreatedAt() : journal.getTanggal();
                
                // Ambil string path gambar dari model backend-mu (misal: journal.getImagePath() / journal.getAttachment())
                // Kirimkan null atau "" jika data gambar kosong untuk memicu fallback state.
                String pathGambar = journal.getImagePath(); 

                // Menyuntikkan data mentah terupdate ke UI Card Komponen
                detailJournalCard.setJournalData(
                    journal.getJudul(),
                    formattedDate,
                    journal.getIsi(),
                    pathGambar
                );
                
                this.revalidate();
                this.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Data jurnal tidak ditemukan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil rincian data: " + e.getMessage(), "Koneksi Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        content = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        content.setBackground(new java.awt.Color(246, 255, 248));
        content.setPreferredSize(new java.awt.Dimension(816, 546));
        content.setLayout(new java.awt.GridBagLayout());
        content.add(jScrollPane1, new java.awt.GridBagConstraints());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
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
            java.util.logging.Logger.getLogger(DetailJournal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetailJournal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetailJournal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetailJournal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DetailJournal(1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
