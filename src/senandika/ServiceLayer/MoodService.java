/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senandika.ServiceLayer;

// MoodService.java
import java.util.ArrayList;
import java.util.List;
import senandika.UILayer.Mood;

public class MoodService {
    private List<Mood> moods = new ArrayList<>();

    public boolean addMood(Mood mood) {
        moods.add(mood);
        return true;
    }

    public List<Mood> getMoodHistory() {
        return moods;
    }

    public double getAverageMood() {
        if (moods.isEmpty()) {
            return 0;
        }

        int total = 0;

        for (Mood mood : moods) {
            total += mood.getTingkatMood();
        }

        return (double) total / moods.size();
    }
}
