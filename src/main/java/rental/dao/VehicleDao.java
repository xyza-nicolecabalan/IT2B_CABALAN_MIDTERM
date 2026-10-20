package rental.dao;

import com.rental.model.Vehicle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {

    public VehicleDao() {
        createTableIfNotExists();
    }

    // 1. Database Initialization / Table Creation
    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS vehicles (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                model_name TEXT NOT NULL,
                plate_no TEXT NOT NULL,
                daily_rate REAL NOT NULL,
                status TEXT NOT NULL
            );
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error initializing database table: " + e.getMessage());
        }
    }

    // 2. Add Record
    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (model_name, plate_no, daily_rate, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.modelName());
            stmt.setString(2, vehicle.plateNo());
            stmt.setDouble(3, vehicle.dailyRate());
            stmt.setString(4, vehicle.status());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting vehicle record: " + e.getMessage());
        }
    }

    // 3. View/Fetch Records
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT id, model_name, plate_no, daily_rate, status FROM vehicles";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Vehicle(
                        rs.getInt("id"),
                        rs.getString("model_name"),
                        rs.getString("plate_no"),
                        rs.getDouble("daily_rate"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching vehicle records: " + e.getMessage());
        }

        return list;
    }
}