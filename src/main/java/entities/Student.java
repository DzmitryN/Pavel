package entities;


public class Student {

    private Integer Id;
    private String firstName;
    private String secondName;

    public Student studentInitializing(String firstName, String secondName){
        Student student = new Student();
        student.setFirstName(firstName);
        student.setSecondName(secondName);
        return student;
    }
    public Student studentInitializing(Integer id , String firstName, String secondName){
        Student student = new Student();
        student.setId(id);
        student.setFirstName(firstName);
        student.setSecondName(secondName);
        return student;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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



}
