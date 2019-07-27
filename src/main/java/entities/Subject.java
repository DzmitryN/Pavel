package entities;


public class Subject {

    Integer Id;
    String subject;

    public Subject(Integer id, String subject) {
        Id = id;
        this.subject = subject;
    }

    public Subject(){}

    public Subject(String subject) {
        this.subject = subject;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
