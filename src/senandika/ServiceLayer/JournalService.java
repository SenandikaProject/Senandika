/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senandika.ServiceLayer;

import java.util.ArrayList;
import java.util.List;
import senandika.UILayer.Journal;

/**
 *
 * @author SAHABAT-IT
 */
public class JournalService {
    private List<Journal> journals = new ArrayList<>();

    public boolean addJournal(Journal journal) {
        journals.add(journal);
        return true;
    }

    public int calculateStreak() {
        return journals.size();
    }

    public List<Journal> getUserJournal() {
        return journals;
    }
}
