package net.volgatech.lks.Pojo;

/**
 * Created by Ultim on 06.12.2016.
 */

public class Exam {
    public int countExam;
    public CurrentSemester currentSemester;
    public SemesterList SemesterList[];

    public class SemesterList {
        public ExamList ExamList[];
    }

    public class ExamList {
        public String title;
        public String date;
        public String location;
        public String teacher;
    }

    public class CurrentSemester {
        public ExamList ExamList[];

    }
}
