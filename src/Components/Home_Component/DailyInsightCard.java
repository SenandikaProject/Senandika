package Components.Home_Component;

import Components.RoundedPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import senandika.FontManager;

public class DailyInsightCard extends RoundedPanel {

    private static final String[] QUOTES = {
        "Setiap langkah kecil yang kamu ambil hari ini tetap merupakan sebuah kemajuan.",
        "Tidak apa-apa untuk beristirahat. Kamu tidak harus selalu kuat setiap saat.",
        "Perasaanmu valid, apapun itu. Beri dirimu ruang untuk merasakannya.",
        "Kemajuan tidak selalu terlihat. Kadang ia tumbuh diam-diam di dalam dirimu.",
        "Hari ini cukup melakukan yang kamu bisa. Itu sudah lebih dari cukup.",
        "Bernapaslah. Kamu sudah melalui hari-hari sulit sebelumnya, dan kamu bisa lagi."
    };

    private JLabel quoteLabel;

    public DailyInsightCard() {
        this(randomQuote());
    }

    public DailyInsightCard(String quote) {
        initComponents(quote);
    }

    private void initComponents(String quote) {

        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(235, 235, 235),
                    1,
                    true
                ),
                BorderFactory.createEmptyBorder(
                    18,
                    20,
                    18,
                    20
                )
            )
        );

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        JLabel iconLabel = new JLabel("✨");
        iconLabel.setFont(
            FontManager.getPoppins(20f)
        );

        iconLabel.setBorder(
            BorderFactory.createEmptyBorder(
                0,
                0,
                10,
                0
            )
        );

        quoteLabel = new JLabel(
            "<html><div style='width:260px;'>"
            + quote +
            "</div></html>"
        );

        quoteLabel.setVerticalAlignment(
            SwingConstants.TOP
        );

        quoteLabel.setFont(
            FontManager.getPoppins(13f)
                .deriveFont(Font.PLAIN)
        );

        quoteLabel.setForeground(
            new Color(71, 85, 105)
        );

        contentPanel.add(
            iconLabel,
            BorderLayout.NORTH
        );

        contentPanel.add(
            quoteLabel,
            BorderLayout.CENTER
        );

        add(
            contentPanel,
            BorderLayout.CENTER
        );

        setPreferredSize(
            new Dimension(
                320,
                120
            )
        );
    }

    public void setQuote(String quote) {

        quoteLabel.setText(
            "<html><div style='width:260px;'>"
            + quote +
            "</div></html>"
        );
    }

    public static String randomQuote() {

        Random random = new Random();

        return QUOTES[
            random.nextInt(
                QUOTES.length
            )
        ];
    }
}