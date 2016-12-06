package net.volgatech.lks.Pojo;

/**
 * Created by Ultim on 06.12.2016.
 */

public class Student {

    public String Fio;
    public int StudentNumber;
    public String AcademicState;
    public String GroupName;
    public Oop Oop;
    public EduGroup eduGroup;

    public class Oop {
        public String Faculty;
        public String Kafedra;
        public String FullStdName;
        public String FullSpzName;
    }

    public class EduGroup {
        public String Course;
    }

    public String toStringStudentNumber() {
        return "" + StudentNumber;
    }

}


