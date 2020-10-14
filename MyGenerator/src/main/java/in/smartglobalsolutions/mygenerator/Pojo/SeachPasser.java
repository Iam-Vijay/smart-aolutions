package in.smartglobalsolutions.mygenerator.Pojo;

public class SeachPasser {
String name;
int id;
String original_pos;
String address;
    public SeachPasser(String name, int id,String original_pos) {
        this.name = name;
        this.original_pos=original_pos;
        this.id=id;
    }


    public SeachPasser() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOriginal_pos() {
        return original_pos;
    }

    public void setOriginal_pos(String original_pos) {
        this.original_pos = original_pos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
