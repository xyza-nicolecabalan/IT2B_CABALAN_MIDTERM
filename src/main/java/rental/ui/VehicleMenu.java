/**
*
*/
package rental.ui;

import rental.dao.VehicleDao;
import com.rental.model.Vehicle;

import java.util.List;
import java.util.Scanner;

public class VehicleMenu {
    private final VehicleDao dao = new VehicleDao();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("==================================================");
            System.out.println("            CAR RENTAL FLEET TRACKER");
            System.out.println("==================================================");
            System.out.println("[1] Add New Vehicle");
            System.out.println("[2] View Fleet Vehicles");
            System.out.println("[3] Exit System");
            System.out.println("==================================================");
            System.out.print("Enter choice: ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            }
            scanner.nextLine(); // Clear newline buffer

            System.out.println();

            switch (choice) {
                case 1 -> addVehiclePrompt();
                case 2 -> viewFleet();
                case 3 -> {
                    System.out.println("Exiting system... Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    private void addVehiclePrompt() {
        System.out.println("--- ADD NEW VEHICLE ---");
        System.out.print("Enter Model Name: ");
        String modelName = scanner.nextLine();

        System.out.print("Enter License Plate Number: ");
        String plateNo = scanner.nextLine();

        System.out.print("Enter Daily Rate: ");
        double dailyRate = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter Status (AVAILABLE / RENTED): ");
        String status = scanner.nextLine().toUpperCase();

        Vehicle vehicle = new Vehicle(0, modelName, plateNo, dailyRate, status);
        dao.addVehicle(vehicle);

        System.out.println("\nSUCCESS: Vehicle registered to fleet!\n");
    }

    private void viewFleet() {
        List<Vehicle> vehicles = dao.getAllVehicles();

        System.out.println("================================================================================");
        System.out.println("                            RENTAL FLEET VEHICLES");
        System.out.println("================================================================================");
        System.out.println("+----+-----------------------+------------+------------+---------------+");
        System.out.printf("| %-2s | %-21s | %-10s | %-10s | %-13s |\n", "ID", "MODEL NAME", "PLATE NO", "DAILY RATE", "STATUS");
        System.out.println("+----+-----------------------+------------+------------+---------------+");

        for (Vehicle v : vehicles) {
            System.out.printf("| %-2d | %-21s | %-10s | %-10.2f | %-13s |\n",
                    v.id(), v.modelName(), v.plateNo(), v.dailyRate(), v.status());
        }

        System.out.println("+----+-----------------------+------------+------------+---------------+");
        System.out.println("Total Records Displayed: " + vehicles.size());
        System.out.println();
    }
}