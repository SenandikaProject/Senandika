package senandika.Model;

/**
 * Model representing a single mood entry.
 * Mirrors rows returned by /api/moods, /api/moods/latest
 */
public class Mood {

    private int id;
    private String tanggal;       // ISO date string, e.g. "2026-06-20"
    private int tingkatMood;      // 1..5
    private String catatan;
    private String createdAt;

    public Mood() {
    }

    public Mood(int id, String tanggal, int tingkatMood, String catatan) {
        this.id = id;
        this.tanggal = tanggal;
        this.tingkatMood = tingkatMood;
        this.catatan = catatan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getTingkatMood() {
        return tingkatMood;
    }

    public void setTingkatMood(int tingkatMood) {
        this.tingkatMood = tingkatMood;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    /**
     * Returns a human readable label for the mood level (1..5).
     */
    public String getMoodLabel() {
        return MoodScale.labelFor(tingkatMood);
    }

    /**
     * Returns the emoji associated with the mood level (1..5).
     */
    public String getMoodEmoji() {
        return MoodScale.emojiFor(tingkatMood);
    }

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
