package entities;


public class Mark {

    Integer ID;
    Integer Mark;

    public Mark() {}

    public Mark(Integer mark) {
        Mark = mark;
    }

    public Mark(Integer ID, Integer mark) {
        this.ID = ID;
        Mark = mark;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getMark() {
        return Mark;
    }

    public void setMark(Integer mark) {
        Mark = mark;
    }


}
