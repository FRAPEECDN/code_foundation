package com.frapee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Main {

    private static void displayResults(Connection conn) throws SQLException {

        String sql = "SELECT * from people;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("FirstName");
                    String surname = resultSet.getString("LastName");
                    int age = resultSet.getInt("age");
                    System.out.printf("| ID: %d | Name: %s | Surname: %s | Age: %d|%n", id, name, surname, age);
                }                
            }
        }
    }

    private static void createTable(Connection conn) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS people ("
                + "id SERIAL PRIMARY KEY, "
                + "FirstName VARCHAR(100) NOT NULL, "
                + "LastName VARCHAR(100) NOT NULL, "
                + "age INT NOT NULL "
                + ")";
        try (PreparedStatement preparedStatement = conn.prepareStatement(createTableSQL)) {
            preparedStatement.execute();
            System.out.println("Table 'people' created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }    

    private static boolean executeInsert(Connection conn, Person person) throws SQLException {
        String sql = "INSERT INTO people(FirstName, LastName, age) values (?, ?, ?);";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, person.fistName());
            preparedStatement.setString(2, person.lastName());
            preparedStatement.setInt(3, person.age());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    private static boolean executeUpdate(Connection conn, Person person) throws SQLException {
        String sql = "UPDATE people set age = ? WHERE FirstName = ? AND LastName = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, person.age());
            preparedStatement.setString(2, person.fistName());
            preparedStatement.setString(3, person.lastName());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    private static boolean executeDelete(Connection conn, Person person) throws SQLException {
        String sql = "DELETE FROM people WHERE FirstName = ? AND LastName = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, person.fistName());
            preparedStatement.setString(2, person.lastName());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    public static void main(String[] args) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/test_two");
        config.setUsername("postgres");
        config.setPassword("G1j1m@");
        config.setMaximumPoolSize(10); // Set the max pool size

        List<Person> listPeople = new ArrayList<>();
        listPeople.add(new Person("Bob", "Somebody", 30));
        listPeople.add(new Person("Alice", "Nobody", 28));
        listPeople.add(new Person("John", "Doe", 25));

        try (HikariDataSource dataSource = new HikariDataSource(config)) {
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("Connected.");

                createTable(conn);
                displayResults(conn);

                for (Person person : listPeople) {
                    executeInsert(conn, person);
                }
                displayResults(conn);

                Person update = new Person("Alice", "Nobody", 30);
                executeUpdate(conn, update);
                displayResults(conn);

                Person delete = new Person("John", "Doe", 0);
                executeDelete(conn, delete);
                displayResults(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
}