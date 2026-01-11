import java.util.Scanner;
import java.io.File;

public class PatientManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // File created in current working directory
        String filePath = System.getProperty("user.dir") + File.separator + "patients.txt";
        File f = new File(filePath);
        System.out.println("patients.txt will be created at: " + f.getAbsolutePath());

        PatientOperations manager = new PatientOperations(filePath);
        int choice;

        do {
            System.out.println("\n--- Patient Management System ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Display Patients");
            System.out.println("3. Sort Patients by ID");
            System.out.println("4. Search Patient by ID");
            System.out.println("5. Delete/Discharge Patient by ID");
            System.out.println("6. Update Patient Status");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    manager.addPatient(new Patient(id, name, age));
                    System.out.println("Patient added successfully.");
                    break;

                case 2:
                    manager.displayPatients();
                    break;

                case 3:
                    manager.sortPatients();
                    System.out.println("Patients sorted by ID.");
                    break;

                case 4:
                    System.out.print("Enter ID to search: ");
                    int searchId = sc.nextInt();
                    sc.nextLine();
                    int index = manager.searchPatient(searchId, 0, manager.getCount() - 1);
                    if (index != -1)
                        System.out.println("Found: " + manager.getPatient(index).getName() +
                                " | Status: " + manager.getPatient(index).getStatus());
                    else
                        System.out.println("Patient not found.");
                    break;

                case 5:
                    System.out.print("Enter ID to delete/discharge: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();
                    if (manager.deletePatient(deleteId))
                        System.out.println("Patient deleted/discharged successfully.");
                    else
                        System.out.println("Patient not found.");
                    break;

                case 6:
                    System.out.print("Enter ID to update status: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new status (Admitted/Recovered): ");
                    String newStatus = sc.nextLine();
                    if (manager.updateStatus(updateId, newStatus))
                        System.out.println("Status updated successfully.");
                    else
                        System.out.println("Patient not found.");
                    break;

                case 7:
                    System.out.println("Exiting Patient Management System...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 7);

        sc.close();
    }
}
