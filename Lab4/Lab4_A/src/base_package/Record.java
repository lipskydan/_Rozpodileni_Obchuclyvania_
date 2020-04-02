package base_package;

public class Record {
    private String name;
    private String number;

    public Record(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "[ Name: " + name + ", Phone: " + number + " ]" + "\n";
    }
}
