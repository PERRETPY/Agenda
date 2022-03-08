package agenda.models;

public class Person implements ModelInterface {
    private int id;
    private String firstname;
    private String lastname;
    private String mail;

    public Person() {
    }

    public Person(int id, String firstname, String lastname, String mail) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
    }

    public Person(String firstname, String lastname, String mail) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String name) {
        this.firstname = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String toCSV() {
        return this.id + ";" + this.firstname + ";" + this.lastname + ";" + this.mail;
    }
}
