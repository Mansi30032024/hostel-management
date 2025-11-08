package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Student;

public class StudentDAO {

    public static boolean save(Student s) {
        String sql = "INSERT INTO students(name, rollno, course, year, phone, room_no) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getRollno());
            ps.setString(3, s.getCourse());
            ps.setString(4, s.getYear());
            ps.setString(5, s.getPhone());
            ps.setString(6, s.getRoom());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Save Error: " + e.getMessage());
            return false;
        }
    }

    public static boolean update(Student s) {
        String sql = "UPDATE students SET name=?, rollno=?, course=?, year=?, phone=?, room_no=? WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getRollno());
            ps.setString(3, s.getCourse());
            ps.setString(4, s.getYear());
            ps.setString(5, s.getPhone());
            ps.setString(6, s.getRoom());
            ps.setInt(7, s.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Update Error: " + e.getMessage());
            return false;
        }
    }

    public static boolean delete(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Delete Error: " + e.getMessage());
            return false;
        }
    }

    public static List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection con = DBConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("rollno"),
                        rs.getString("course"),
                        rs.getString("year"),
                        rs.getString("phone"),
                        rs.getString("room_no")
                ));
            }
        } catch (Exception e) {
            System.out.println("Fetch Error: " + e.getMessage());
        }
        return list;
    }
}
