import java.util.ArrayList;
import java.io.*;

public class PatientOperations extends PatientOperationsBase {
    private ArrayList<Patient> patients;
    private String filename;

    public PatientOperations(String filename) {
        this.filename = filename;
        patients = new ArrayList<>();
        File f = new File(filename);
        if (f.exists()) {
            System.out.println(filename + " exists. Data will be appended when adding new patients.");
        }
    }

    @Override
    public void sortPatients() {
        int n = patients.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (patients.get(j).getId() > patients.get(j + 1).getId()) {
                    Patient temp = patients.get(j);
                    patients.set(j, patients.get(j + 1));
                    patients.set(j + 1, temp);
                }
            }
        }
        saveToFile();
    }

    @Override
    public int searchPatient(int id, int low, int high) {
        if (low > high)
            return -1;
        int mid = (low + high) / 2;
        if (patients.get(mid).getId() == id)
            return mid;
        else if (id < patients.get(mid).getId())
            return searchPatient(id, low, mid - 1);
        else
            return searchPatient(id, mid + 1, high);
    }

    public void displayPatients() {
        System.out.println("\n--- List of Patients ---");
        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            System.out.println("ID: " + p.getId() + " | Name: " + p.getName() +
                    " | Age: " + p.getAge() + " | Status: " + p.getStatus());
        }
    }

    public void addPatient(Patient p) {
        patients.add(p);
        saveToFile();
    }

    public int getCount() {
        return patients.size();
    }

    public Patient getPatient(int index) {
        return patients.get(index);
    }

    public boolean deletePatient(int id) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId() == id) {
                patients.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean updateStatus(int id, String newStatus) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId() == id) {
                patients.get(i).setStatus(newStatus);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < patients.size(); i++) {
                bw.write(patients.get(i).toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
