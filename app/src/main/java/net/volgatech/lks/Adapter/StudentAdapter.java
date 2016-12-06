package net.volgatech.lks.Adapter;

import net.volgatech.lks.Pojo.Student;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ultim on 06.12.2016.
 */

public class StudentAdapter {
    private ArrayList<HashMap<String, String>> infoList;
    public StudentAdapter() {
        infoList = new ArrayList<>();
    }

    private HashMap<String, String> addElement(String header, String value) {
        HashMap<String, String> infoElement = new HashMap<>();
        infoElement.put("header", header);
        infoElement.put("value", value);
        return infoElement;
    }

    public ArrayList<HashMap<String, String>> Convert(Student student) {
        infoList.add(addElement("ФИО", student.Fio));
        infoList.add(addElement("Номер зачетной книжки", student.toStringStudentNumber()));
        infoList.add(addElement("Факультет (институт)", student.Oop.FullSpzName));
        infoList.add(addElement("Направление (специальность)", student.Fio));
        infoList.add(addElement("Профиль", student.Oop.FullStdName));
        infoList.add(addElement("Группа", student.Oop.Kafedra));
        infoList.add(addElement("Академический статус,", student.AcademicState));
        return infoList;
    }
}