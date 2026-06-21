package Components.Journal_Component;

import Components.RoundedButton;
import java.awt.*;
import javax.swing.*;
import senandika.FontManager;
import senandika.UILayer.CreateJournal;

public class JournalEmptyState extends JPanel {
    
    // Tambahkan parameter JFrame agar bisa dinavigasikan atau di-dispose
    public JournalEmptyState(JFrame parentFrame){
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10); 

        JLabel image = new JLabel();
        try {
            java.net.URL imgURL = getClass().getResource("/Asset/aset_utama/Maskot3.png");
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                image.setIcon(new ImageIcon(scaledImage));
            } else {
                System.out.println("Gagal memuat gambar: Path tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        gbc.gridy = 0;
        add(image, gbc);
        
        JLabel text = new JLabel("<html><center>Kamu belum membuat<br>jurnal apapun...</center></html>");
        text.setFont(FontManager.getPoppins(20f)); 
        text.setForeground(new Color(137, 126, 255));
        text.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        add(text, gbc);
        
        RoundedButton btnTambah = new RoundedButton();
        btnTambah.setText("+ Tambah Jurnal");
        btnTambah.setBackground(new Color(137, 126, 255));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFont(FontManager.getPoppins(14f));

        // Implementasi klik tombol tambah
        btnTambah.addActionListener(e -> {
            new CreateJournal().setVisible(true);
            if(parentFrame != null) {
                parentFrame.dispose();
            }
        });

        gbc.gridy = 2;
        add(btnTambah, gbc);
    }
}