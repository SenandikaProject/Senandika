package Components.Mood_Component;

import Components.RoundedPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import senandika.Model.Mood;
import senandika.FontManager;

/**
 * Mood History Card
 * Menampilkan riwayat mood pengguna.
 */
public class MoodHistoryCard extends RoundedPanel {

    public MoodHistoryCard(Mood mood) {

        initComponents(mood);
    }

    private void initComponents(Mood mood) {

        setLayout(
            new BorderLayout(
                12,
                0
            )
        );

        setOpaque(true);
        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(
                        235,
                        235,
                        235
                    ),
                    1,
                    true
                ),
                BorderFactory.createEmptyBorder(
                    14,
                    16,
                    14,
                    16
                )
            )
        );

        setMaximumSize(
            new Dimension(
                Integer.MAX_VALUE,
                100
            )
        );

        setAlignmentX(
            LEFT_ALIGNMENT
        );

        JLabel emojiLabel =
                new JLabel(
                    mood.getMoodEmoji()
                );

        emojiLabel.setFont(
            FontManager.getPoppins(28f)
        );

        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
            new BoxLayout(
                textPanel,
                BoxLayout.Y_AXIS
            )
        );

        JLabel moodLabel =
                new JLabel(
                    mood.getMoodLabel()
                );

        moodLabel.setFont(
            FontManager.getPoppins(14f)
                .deriveFont(Font.BOLD)
        );

        moodLabel.setForeground(
            new Color(
                30,
                41,
                59
            )
        );

        moodLabel.setAlignmentX(
            LEFT_ALIGNMENT
        );

        JLabel dateLabel =
                new JLabel(
                    formatDate(
                        mood.getTanggal()
                    )
                );

        dateLabel.setFont(
            FontManager.getPoppins(12f)
        );

        dateLabel.setForeground(
            new Color(
                100,
                116,
                139
            )
        );

        dateLabel.setAlignmentX(
            LEFT_ALIGNMENT
        );

        dateLabel.setBorder(
            BorderFactory.createEmptyBorder(
                2,
                0,
                0,
                0
            )
        );

        textPanel.add(
            moodLabel
        );

        textPanel.add(
            dateLabel
        );

        if (
            mood.getCatatan() != null
            &&
            !mood.getCatatan()
                .trim()
                .isEmpty()
        ) {

            JLabel noteLabel =
                    new JLabel(
                        "<html><div style='width:220px;'>“"
                        + mood.getCatatan()
                        + "”</div></html>"
                    );

            noteLabel.setFont(
                FontManager.getPoppins(11f)
            );

            noteLabel.setForeground(
                new Color(
                    100,
                    116,
                    139
                )
            );

            noteLabel.setAlignmentX(
                LEFT_ALIGNMENT
            );

            noteLabel.setBorder(
                BorderFactory.createEmptyBorder(
                    4,
                    0,
                    0,
                    0
                )
            );

            textPanel.add(
                noteLabel
            );
        }

        add(
            emojiLabel,
            BorderLayout.WEST
        );

        add(
            textPanel,
            BorderLayout.CENTER
        );
    }

    private static String formatDate(
            String isoDate
    ) {

        if (
            isoDate == null
            ||
            isoDate.isEmpty()
        ) {

            return "";
        }

        try {

            java.time.LocalDate date =
                    java.time.LocalDate.parse(
                        isoDate.substring(
                            0,
                            10
                        )
                    );

            String[] bulan = {
                "Januari",
                "Februari",
                "Maret",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Agustus",
                "September",
                "Oktober",
                "November",
                "Desember"
            };

            return
                date.getDayOfMonth()
                + " "
                + bulan[
                    date.getMonthValue() - 1
                ]
                + " "
                + date.getYear();

        } catch (Exception e) {

            return isoDate;
        }
    }
}