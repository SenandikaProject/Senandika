package Components.Mood_Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JPanel;
import senandika.FontManager;
import senandika.Model.Mood;
import senandika.Model.MoodScale;

/**
 * Custom Mood Distribution Chart
 * Menampilkan distribusi frekuensi mood pengguna.
 */
public class MoodDistributionChart extends JPanel {

    private int[] counts = new int[5];

    public MoodDistributionChart() {
        setOpaque(false);
        setPreferredSize(new Dimension(320, 200));
    }

    public void setMoods(List<Mood> moods) {
        counts = new int[5];

        if (moods != null) {
            for (Mood mood : moods) {
                int level = mood.getTingkatMood();
                if (level >= 1 && level <= 5) {
                    counts[level - 1]++;
                }
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        int width = getWidth();
        int height = getHeight();

        int padding = 20;
        int bottomLabelHeight = 40;

        int chartTop = padding;
        int chartBottom = height - bottomLabelHeight;
        int chartHeight = chartBottom - chartTop;

        int maxCount = 1;
        for (int count : counts) {
            maxCount = Math.max(maxCount, count);
        }

        int barCount = 5;
        int gap = 18;
        int totalGap = gap * (barCount + 1);

        int barWidth = Math.max(
            24,
            (width - totalGap) / barCount
        );

        FontMetrics countMetrics = g2.getFontMetrics(
            FontManager.getPoppins(13f).deriveFont(java.awt.Font.BOLD)
        );

        int x = gap;

        for (int i = 0; i < barCount; i++) {
            int level = i + 1;
            int count = counts[i];

            int barHeight = (int) (
                        ((double) count / maxCount)
                        * (chartHeight - 30)
                    );

            if (count > 0 && barHeight < 6) {
                barHeight = 6;
            }

            int barY = chartBottom - barHeight;
            Color barColor;

            if (level == 4 || level == 5) {
                barColor = new Color(137, 126, 255); // Warna Ungu Utama
            } else {
                barColor = new Color(177, 167, 255); // Warna Ungu Sekunder
            }

            g2.setColor(count == 0 ? new Color(237, 234, 251) : barColor);

            g2.fillRoundRect(
                x,
                barY,
                barWidth,
                Math.max(barHeight, 4),
                10,
                10
            );

            // 1. Menggambar angka frekuensi di atas batang diagram
            if (count > 0) {
                g2.setFont(FontManager.getPoppins(13f).deriveFont(java.awt.Font.BOLD));
                g2.setColor(new Color(30, 41, 59));

                String countText = String.valueOf(count);
                int textWidth = countMetrics.stringWidth(countText);

                g2.drawString(
                    countText,
                    x + ((barWidth - textWidth) / 2),
                    barY - 6
                );
            }

            // 2. Menggambar teks nama emosi pengganti emoji Unicode silang
            g2.setFont(FontManager.getPoppins(11f));
            g2.setColor(new Color(100, 116, 139)); // Warna teks abu-abu (Slate)

            String labelText = "";
            switch (level) {
                case 1: labelText = "Sedih"; break;
                case 2: labelText = "Murung"; break;
                case 3: labelText = "Marah"; break;
                case 4: labelText = "Senang"; break;
                case 5: labelText = "Ceria"; break;
                default: labelText = "Marah";
            }

            FontMetrics labelMetrics = g2.getFontMetrics();
            int labelWidth = labelMetrics.stringWidth(labelText);

            g2.drawString(
                labelText,
                x + ((barWidth - labelWidth) / 2),
                chartBottom + 22
            );

            x += barWidth + gap;
        }

        // Garis batas sumbu horizontal X grafik
        g2.setColor(new Color(226, 232, 240));
        g2.drawLine(
            padding - 10,
            chartBottom,
            width - padding + 10,
            chartBottom
        );

        g2.dispose();
    }
}