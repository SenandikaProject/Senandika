package senandika.Model;

public class JournalData {

    private int id;
    private String judul;
    private String isi;
    private String tanggal;
    private int streak;

    public JournalData() {
    }

    public JournalData(
            int id,
            String judul,
            String isi,
            String tanggal,
            int streak
    ) {

        this.id = id;
        this.judul = judul;
        this.isi = isi;
        this.tanggal = tanggal;
        this.streak = streak;
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
}

