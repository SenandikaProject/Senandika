package senandika.Model;

/**
 * Central place that maps a mood level (1..5) to its emoji and label.
 * Used by Mood, MoodPanel, MoodButton, MoodHistoryCard, etc. so the
 * mapping only has to be defined once.
 */
public final class MoodScale {

    private MoodScale() {
        // utility class
    }

    public static String emojiFor(int level) {
        switch (level) {
            case 1: return "\uD83D\uDE2D"; // 😭
            case 2: return "\uD83D\uDE41"; // 🙁
            case 3: return "\uD83D\uDE10"; // 😐
            case 4: return "\uD83D\uDE42"; // 🙂
            case 5: return "\uD83D\uDE01"; // 😁
            default: return "\u2754";       // ❔
        }
    }

    public static String labelFor(int level) {
        switch (level) {
            case 1: return "Sangat Sedih";
            case 2: return "Sedih";
            case 3: return "Biasa";
            case 4: return "Senang";
            case 5: return "Sangat Bahagia";
            default: return "Tidak diketahui";
        }
    }
}
