package net.volgatech.lks.Adapter;

import net.volgatech.lks.Pojo.Exam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ultim on 06.12.2016.
 */

public class ExamAdapter {
    private ArrayList<HashMap<String, String>> examList;
    public ExamAdapter() {
        examList = new ArrayList<>();
    }

    public ArrayList<HashMap<String, String>> getCurrentSemester(Exam exams){
        for (int i = 0;  i < exams.currentSemester.ExamList.length; i++){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", exams.currentSemester.ExamList[i].title);
            hashMap.put("date: ", exams.currentSemester.ExamList[i].date);
            hashMap.put("location", exams.currentSemester.ExamList[i].location);
            hashMap.put("teacher", exams.currentSemester.ExamList[i].teacher);
            examList.add(hashMap);
        }
        return examList;
    }
}