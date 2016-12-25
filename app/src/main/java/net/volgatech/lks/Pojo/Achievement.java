package net.volgatech.lks.Pojo;

/**
 * Created by Ultim on 25.12.2016.
 */

public class Achievement {
    public Achievements Achievements;

    public class Achievements{

        public StudyHeader StudyHeader;
        public Study Study[];
        public String SumMark;

        public class StudyHeader{
            public String criterion;
            public String subcriterion;
            public String mark;
            public String date;
        }

        public class Study{
            public String criterion;
            public String subcriterion;
            public String mark;
            public String date;
        }
    }
}
