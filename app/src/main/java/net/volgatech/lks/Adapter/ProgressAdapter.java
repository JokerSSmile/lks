package net.volgatech.lks.Adapter;


import net.volgatech.lks.Pojo.Progress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ultim on 13.12.2016.
 */

public class ProgressAdapter {

    private ArrayList<HashMap<String, String>> progressList;
    public ProgressAdapter()
    {
        progressList = new ArrayList<>();
    }
    public ArrayList<HashMap<String, String>> GetProgressArray(Progress progress){
        progressList.clear();
        //List<String> items = new ArrayList<>();
        for (int i = 0; i < progress.Subjects.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", progress.Subjects[i].SubjectName);
            hashMap.put("mark", progress.Subjects[i].MarkValue);

            if (progress.Subjects[i].ResultBall != null) {
                hashMap.put("ball", progress.Subjects[i].ResultBall);
            }else {
                hashMap.put("ball", "-");
            }
            String timestamp = progress.Subjects[i].MarkDate.replace("/Date(", "").replace(")/", "");
            Date createdOn = new Date(Long.parseLong(timestamp));
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
            String formattedDate = sdf.format(createdOn);
            hashMap.put("date", formattedDate);

            progressList.add(hashMap);
            /*
            items.add("Предмет");
            items.add(progress.Subjects[i].SubjectName);
            items.add("Оценка");
            items.add(progress.Subjects[i].MarkValue);
            items.add("Балл рейтинга");
            if (progress.Subjects[i].ResultBall != null){
                items.add(progress.Subjects[i].ResultBall);
            }else {
                items.add("-");
            }
            items.add("Дата получения");
            //String timestamp = progress.Subjects[i].MarkDate.replace("/Date(", "").replace(")/", "");
            //Date createdOn = new Date(Long.parseLong(timestamp));
           // SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
           // String formattedDate = sdf.format(createdOn);
            //items.add(formattedDate);
            */
        }
        return progressList;
    }
}
