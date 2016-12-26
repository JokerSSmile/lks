package net.volgatech.lks.Adapter;

import net.volgatech.lks.Pojo.Schedule;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ultim on 26.12.2016.
 */

public class ScheduleAdapter {
    private ArrayList<HashMap<String, String>> scheduleList;

    public ScheduleAdapter(){
        scheduleList = new ArrayList<>();
    }

    public ArrayList<HashMap<String, String>> GetScheduleList(Schedule schedule){
        scheduleList.clear();
        for (int i = 0; i < schedule.ScheduleItem.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("time", schedule.ScheduleItem[i].time);
            hashMap.put("title", schedule.ScheduleItem[i].title);
            hashMap.put("teacher", schedule.ScheduleItem[i].teacher);
            hashMap.put("location", schedule.ScheduleItem[i].location);
            if (schedule.ScheduleItem[i].color == 1){
                hashMap.put("color", "red");
            }
            if (schedule.ScheduleItem[i].color == 2){
                hashMap.put("color", "blue");
            }
            if (schedule.ScheduleItem[i].color == 0){
                hashMap.put("color", "NO");
            }
            scheduleList.add(hashMap);
        }

        return scheduleList;
    }

}
