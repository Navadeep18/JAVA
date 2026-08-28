package window;

import DBConnection.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeDAO {

    // =========================================================
    // ADD EMPLOYEE
    // =========================================================

    public void addEmployee(
            int id,
            String name,
            String department,
            String designation,
            String date,
            double salary,
            String email,
            String phone,
            String status) throws SQLException {

        String sql = "INSERT INTO employee "
                + "(emp_id, emp_name, department, designation, "
                + "date_of_joining, salary, email, phone, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps =
                     DBConnection.getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, department);
            ps.setString(4, designation);
            ps.setDate(5, java.sql.Date.valueOf(date));
            ps.setDouble(6, salary);
            ps.setString(7, email);
            ps.setString(8, phone);
            ps.setString(9, status);

            ps.executeUpdate();
        }
    }

    // =========================================================
    // UPDATE EMPLOYEE
    // =========================================================

    public void updateEmployee(
            int id,
            String name,
            String department,
            String designation,
            String date,
            double salary,
            String email,
            String phone,
            String status) throws SQLException {

        String sql = "UPDATE employee SET "
                + "emp_name=?, department=?, designation=?, "
                + "date_of_joining=?, salary=?, email=?, "
                + "phone=?, status=? "
                + "WHERE emp_id=?";

        Connection con = DBConnection.getConnection();

        try {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setString(2, department);
                ps.setString(3, designation);
                ps.setDate(4, java.sql.Date.valueOf(date));
                ps.setDouble(5, salary);
                ps.setString(6, email);
                ps.setString(7, phone);
                ps.setString(8, status);
                ps.setInt(9, id);

                ps.executeUpdate();

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    // =========================================================
    // DELETE EMPLOYEE
    // =========================================================

    public void deleteEmployee(int id) throws SQLException {

        String sql = "DELETE FROM employee WHERE emp_id=?";

        Connection con = DBConnection.getConnection();

        try {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                ps.executeUpdate();

                con.commit();

            } catch (SQLException e) {

                con.rollback();
                throw e;

            } finally {

                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    // =========================================================
    // LOAD ALL EMPLOYEES
    // =========================================================

    public void loadEmployees(DefaultTableModel model)
            throws SQLException {

        String sql = "SELECT * FROM employee ORDER BY emp_id";

        model.setRowCount(0);

        try (Statement st =
                     DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                model.addRow(new Object[]{
                    rs.getInt("emp_id"),
                    rs.getString("emp_name"),
                    rs.getString("department"),
                    rs.getString("designation"),
                    rs.getDate("date_of_joining"),
                    rs.getDouble("salary"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("status")
                });
            }
        }
    }

    // =========================================================
    // SEARCH EMPLOYEE
    // =========================================================

    public void searchEmployees(
            DefaultTableModel model,
            String field,
            String value) throws SQLException {

        model.setRowCount(0);

        String sql;

        if (field.equals("ID")) {

            sql = "SELECT * FROM employee "
                    + "WHERE CAST(emp_id AS CHAR) LIKE ? "
                    + "ORDER BY emp_id";

        } else if (field.equals("Name")) {

            sql = "SELECT * FROM employee "
                    + "WHERE emp_name LIKE ? "
                    + "ORDER BY emp_id";

        } else {

            sql = "SELECT * FROM employee "
                    + "WHERE department LIKE ? "
                    + "ORDER BY emp_id";
        }

        try (PreparedStatement ps =
                     DBConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, "%" + value + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    model.addRow(new Object[]{
                        rs.getInt("emp_id"),
                        rs.getString("emp_name"),
                        rs.getString("department"),
                        rs.getString("designation"),
                        rs.getDate("date_of_joining"),
                        rs.getDouble("salary"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("status")
                    });
                }
            }
        }
    }
}