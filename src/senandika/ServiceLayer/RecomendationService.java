/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senandika.ServiceLayer;

import java.util.ArrayList;
import java.util.List;
import senandika.UILayer.ActivitySuggestion;

public class RecomendationService {
    private List<ActivitySuggestion> activities = new ArrayList<>();

    public List<ActivitySuggestion> getRecommendation() {
        return activities;
    }

    public void addActivity(ActivitySuggestion activity) {
        activities.add(activity);
    }
}
