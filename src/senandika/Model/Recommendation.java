package senandika.Model;

/**
 * Model representing a wellness activity recommendation.
 * Mirrors response from GET /api/recommendations and /api/recommendations/:id
 */
public class Recommendation {

    private int id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String[] steps;
    private String durationLabel; // e.g. "5 menit" - optional extra shown on the card

    public Recommendation() {
    }

    public Recommendation(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Recommendation(int id, String title, String description, String thumbnailUrl,
                           String[] steps, String durationLabel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.steps = steps;
        this.durationLabel = durationLabel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public String getDurationLabel() {
        return durationLabel;
    }

    public void setDurationLabel(String durationLabel) {
        this.durationLabel = durationLabel;
    }
}
