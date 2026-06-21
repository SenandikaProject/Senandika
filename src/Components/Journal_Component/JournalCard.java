package Components.Journal_Component;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import senandika.Model.JournalData;
import senandika.ServiceLayer.JournalService;

public class JournalCard extends JPanel {

    private JPanel contentPanel;

    public JournalCard(JFrame parentFrame) {
        
        setLayout(new BorderLayout());
        contentPanel = new JPanel();
        contentPanel.setOpaque(false);

        contentPanel.setLayout(
            new BoxLayout(
                contentPanel,
                BoxLayout.Y_AXIS
            )
        );

        JScrollPane scroll =
                new JScrollPane(
                        contentPanel
                );

        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);

        add(scroll, BorderLayout.CENTER);

        
    }

    public void loadData() {

        try {

            JournalService service =
                    new JournalService();

            List<JournalData> journals =
                    service.getJournals();

            contentPanel.removeAll();

            if (journals.isEmpty()) {

                contentPanel.add(
                        new JournalEmptyState()
                );

            } else {

                int streak =
                        service.getStreak();

                JournalStreakCard streakCard =
                        new JournalStreakCard();

                streakCard.setStreak(
                        streak
                );

                contentPanel.add(
                        streakCard
                );

                contentPanel.add(
                        Box.createVerticalStrut(
                                20
                        )
                );

                JLabel title =
                        new JLabel(
                                "Daftar Jurnal"
                        );

                title.setFont(
                        new Font(
                                "Poppins",
                                Font.BOLD,
                                24
                        )
                );

                contentPanel.add(
                        title
                );

                contentPanel.add(
                        Box.createVerticalStrut(
                                15
                        )
                );

                for (
                        JournalData journal
                        : journals
                ) {

                    JournalItemCard card =
                            new JournalItemCard();

                    card.setData(
                            journal.getId(),
                            journal.getJudul(),
                            journal.getTanggal()
                    );

                    contentPanel.add(
                            card
                    );

                    contentPanel.add(
                            Box.createVerticalStrut(
                                    15
                            )
                    );
                }
            }

            revalidate();
            repaint();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}