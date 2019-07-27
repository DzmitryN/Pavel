package entities;


public class MarksSheet {


    Integer ID;
    String firstName;
    String secondName;
    String subject;
    Integer mark;
    Integer Student_id;
    Integer subject_id;
    Integer mark_id;



    public MarksSheet() {
    }



    public MarksSheet(Integer ID, String firstName, String secondName, String subject, Integer mark) {
        this.ID = ID;
        this.firstName = firstName;
        this.secondName = secondName;
        this.subject = subject;
        this.mark = mark;
    }



    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getStudent_id() {
        return Student_id;
    }

    public void setStudent_id(Integer student_id) {
        Student_id = student_id;
    }

    public Integer getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        this.subject_id = subject_id;
    }

    public Integer getMark_id() {
        return mark_id;
    }

    public void setMark_id(Integer mark_id) {
        this.mark_id = mark_id;
    }






}
