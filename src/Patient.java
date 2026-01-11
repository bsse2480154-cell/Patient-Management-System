public class Patient {
    private int id;
    private String name;
    private int age;
    private String status; // Admitted or Recovered

    public Patient(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.status = "Admitted"; // default status
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Human-readable format for file
    public String toFileString() {
        return "Name of the patient: " + name + "\n" +
                "ID: " + id + "\n" +
                "Age: " + age + "\n" +
                "Status: " + status + "\n" +
                "------------------";
    }
}
