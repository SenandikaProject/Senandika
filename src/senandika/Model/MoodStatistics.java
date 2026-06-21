package senandika.Model;

/**
 * Model representing aggregate mood statistics.
 * Mirrors response from GET /api/moods/statistics
 */
public class MoodStatistics {

    private double averageMood;
    private int highestMood;
    private int lowestMood;

    public MoodStatistics() {
    }

    public MoodStatistics(double averageMood, int highestMood, int lowestMood) {
        this.averageMood = averageMood;
        this.highestMood = highestMood;
        this.lowestMood = lowestMood;
    }

    public double getAverageMood() {
        return averageMood;
    }

    public void setAverageMood(double averageMood) {
        this.averageMood = averageMood;
    }

    public int getHighestMood() {
        return highestMood;
    }

    public void setHighestMood(int highestMood) {
        this.highestMood = highestMood;
    }

    public int getLowestMood() {
        return lowestMood;
    }

    public void setLowestMood(int lowestMood) {
        this.lowestMood = lowestMood;
    }
}
