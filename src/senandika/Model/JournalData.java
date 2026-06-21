package senandika.Model;

public class JournalData {

    private int id;
    private String judul;
    private String isi;
    private String tanggal;
    private int streak;
    private String imagePath;
    private String createdAt;

    public JournalData() {
    }

    public JournalData(
            int id,
            String judul,
            String isi,
            String tanggal,
            int streak,
            String imagePath,
            String createdAt
    ) {

        this.id = id;
        this.judul = judul;
        this.isi = isi;
        this.tanggal = tanggal;
        this.streak = streak;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getStreak() {
        return streak;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

