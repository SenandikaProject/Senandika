package senandika.UILayer;

import Components.Journal_Component.EditJournalCard;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;
import senandika.Model.JournalData;
import senandika.ServiceLayer.JournalService;


public class EditJournal extends javax.swing.JFrame {
    
    private File selectedFile;
    private EditJournalCard journalCard;
    private int journalId;
    
    public EditJournal(int journalId) {
        this.journalId = journalId;
        initComponents();
        setLocationRelativeTo(null);
        initUI();
        loadJournal();
    }

    private EditJournal() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void initUI() {
        // 1. Bersihkan aturan konfigurasi ScrollPane pembungkus
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setBorder(null); // Menghilangkan border ganda bawaan swing agar bersih

        // 2. Atur panel internal (content) agar elastis mengikuti GridBagLayout
        content.setBackground(new Color(246, 255, 248));
        content.setLayout(new GridBagLayout());
        content.removeAll();

        // PENTING: JANGAN pakai setPreferredSize() yang mengunci tinggi komponen kaku!
        // Kita biarkan dimensi lebarnya pas 398, dan tingginya otomatis berdasar isi komponen (PreferredSize bawaan)

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH; // Menjaga konten selalu rapi mulai dari atas layar

        // 3. Pasang Card Komponen Modern Baru
        journalCard = new EditJournalCard(this);
        content.add(journalCard, gbc);
        
        journalCard.getBtnSave()
        .addActionListener(e -> {
            updateJournal();
        });

        journalCard.getBtnCancel()
        .addActionListener(e -> {
            Journal page =
                    new Journal();

            page.setVisible(true);

            dispose();
        });

        // 4. Sinkronisasikan ulang rendering engine Java Swing
        content.revalidate();
        content.repaint();
        this.pack();
    }
    
    private void loadJournal() {
        try {

            JournalService service =
                    new JournalService();

            JournalData data =
                    service.getJournalById(
                            journalId
                    );

            journalCard.setJournalData(
                    data
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
    
    private void updateJournal() {
        try {

            String judul =
                    journalCard
                            .getTxtJudul()
                            .getText();

            String isi =
                    journalCard
                            .getTxtIsi()
                            .getText();

            JournalService service =
                    new JournalService();

            service.updateJournal(
                    journalId,
                    judul,
                    isi
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Jurnal berhasil diperbarui"
            );

            new Journal().setVisible(true);

            dispose();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
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

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditJournal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
