package net.volgatech.lks.Adapter;

import net.volgatech.lks.Pojo.Exam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ultim on 26.12.2016.
 */

public class ExamAdapter {
    private ArrayList<HashMap<String, String>> examList;
    public ExamAdapter() {
        examList = new ArrayList<>();
    }

    public ArrayList<HashMap<String, String>> GetCurrentSemester(Exam exam){
        examList.clear();

        for (int i = 0; i < exam.currentSemester.ExamList.length; i++){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", exam.currentSemester.ExamList[i].title);
            hashMap.put("date", exam.currentSemester.ExamList[i].date);
            hashMap.put("location", exam.currentSemester.ExamList[i].location);
            hashMap.put("teacher", exam.currentSemester.ExamList[i].teacher);
            examList.add(hashMap);
        }
        return examList;
    }

    public ArrayList<HashMap<String, String>> GetNumberSemester(Exam exam, int num){
        examList.clear();

        for (int i = 0; i < exam.SemesterList[num].ExamList.length; i++){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", exam.SemesterList[num].ExamList[i].title);
            hashMap.put("date", exam.SemesterList[num].ExamList[i].date);
            hashMap.put("location", exam.SemesterList[num].ExamList[i].location);
            hashMap.put("teacher", exam.SemesterList[num].ExamList[i].teacher);
            examList.add(hashMap);
        }
        return examList;
    }
}
