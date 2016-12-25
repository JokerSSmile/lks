package net.volgatech.lks.Adapter;

import net.volgatech.lks.Pojo.Achievement;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ultim on 25.12.2016.
 */

public class AchievementsAdapter {
    private ArrayList<HashMap<String, String>> achievementsList;

    public AchievementsAdapter() {
        achievementsList = new ArrayList<>();
    }

    public ArrayList<HashMap<String, String>> GetAchievementsList(Achievement achievement){
        achievementsList.clear();
        for (int i = 0; i < achievement.Achievements.Study.length; i++){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("criterion", achievement.Achievements.Study[i].criterion);
            hashMap.put("subcriterion", achievement.Achievements.Study[i].subcriterion);
            hashMap.put("mark", achievement.Achievements.Study[i].mark);
            hashMap.put("date", achievement.Achievements.Study[i].date);
            achievementsList.add(hashMap);
        }
        return achievementsList;
    }

    public ArrayList<HashMap<String, String>> GetSumMark(Achievement achievement){
        ArrayList<HashMap<String, String>> sumLust = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sum", achievement.Achievements.SumMark);
        sumLust.add(hashMap);
        return sumLust;
    }
}
