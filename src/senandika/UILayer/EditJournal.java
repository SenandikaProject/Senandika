package senandika.UILayer;

import Components.Journal_Component.EditJournalCard;
import Components.Journal_Component.JournalCard;
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
    private JournalService service;
    private EditJournalCard editJournalCard;
    private JournalCard mainJournalPage; // Menyimpan referensi halaman utama jurnal

    public EditJournal(int journalId, JournalCard mainJournalPage) {
        this.service = new JournalService();
        this.mainJournalPage = mainJournalPage;
        
        initComponents();
        setLocationRelativeTo(null);
        initUI();
        
        // Muat data lama ke form & aktifkan listener tombol
        loadJournalDataToForm(journalId);
        setupActions();
    }
    
    private void initUI() {
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setBorder(null); 

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

        editJournalCard = new EditJournalCard(this);
        content.add(editJournalCard, gbc);

        content.revalidate();
        content.repaint();
        this.pack();
    }
    
    private void loadJournalDataToForm(int id) {
        try {
            JournalData data = service.getJournalById(id); 
            if (data != null) {
                editJournalCard.setJournalData(data);
            } else {
                JOptionPane.showMessageDialog(this, "Data jurnal tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + e.getMessage());
        }
    }
    
    private void setupActions() {
        // Tombol Batal -> Kembali ke halaman jurnal utama tanpa mengubah apapun
        editJournalCard.getBtnCancel().addActionListener(e -> {
            if (mainJournalPage != null) {
                mainJournalPage.loadData(); // Memastikan UI utama segar kembali
            }
            dispose();
        });
        
        // Tombol Simpan Perubahan -> Eksekusi update database
        editJournalCard.getBtnSave().addActionListener(e -> {
            String judulBaru = editJournalCard.getTxtJudul().getText().trim();
            String isiBaru = editJournalCard.getTxtIsi().getText().trim();
            File fileFotoBaru = editJournalCard.getSelectedFotoFile();
            String fotoLamaUrl = editJournalCard.getOldFotoUrl();
            int idJurnal = editJournalCard.getJournalId();

            // Validasi input kosong
            if (judulBaru.isEmpty() || isiBaru.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Judul dan Isi jurnal tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Tentukan path foto yang akan disimpan ke database
                String finalFotoPath = fotoLamaUrl; 
                
                // Jika user mengunggah foto baru, proses file tersebut
                if (fileFotoBaru != null) {
                    finalFotoPath = fileFotoBaru.getAbsolutePath(); 
                    // Catatan: Jika ServiceLayer Anda memproses upload/copy file otomatis, 
                    // Anda bisa melemparkan objek 'fileFotoBaru' langsung ke method service.
                }

                // Panggil Service Layer untuk melakukan query UPDATE ke database
                boolean success = service.updateJournal(idJurnal, judulBaru, isiBaru, finalFotoPath);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Jurnal berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    
                    // REFRESH OTOMATIS: Panggil loadData() halaman utama agar data terbaru langsung tampil
                    if (mainJournalPage != null) {
                        mainJournalPage.loadData();
                    }
                    
                    dispose(); // Tutup form edit
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal memperbarui jurnal di database.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan sistem: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
    });
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
                new EditJournal(1,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
