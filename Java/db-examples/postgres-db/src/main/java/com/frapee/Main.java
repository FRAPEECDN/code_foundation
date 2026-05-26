package com.frapee;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.yaml.snakeyaml.Yaml;

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

    private static Map<String, Object> loadYamlConfig(String resourceName) {
        try (InputStream stream = Main.class.getResourceAsStream(resourceName)) {
            if (stream == null) {
                throw new IllegalStateException("Missing resource: " + resourceName);
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> yaml = (Map<String, Object>) new Yaml().load(stream);
            return yaml;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load YAML config", e);
        }
    }

    private static String extractEncryptedValue(String value) {
        if (value == null) {
            return null;
        }
        if (value.startsWith("ENC(") && value.endsWith(")")) {
            return value.substring(4, value.length() - 1);
        }
        return value;
    }

    private static String decrypt(String encryptedValue, String base64Iv, String secret) {
        try {
            byte[] iv = Base64.getDecoder().decode(base64Iv);
            byte[] keyBytes = MessageDigest.getInstance("SHA-256").digest(secret.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Unable to decrypt database credentials", e);
        }
    }

    public static void main(String[] args) {
        Map<String, Object> yamlConfig = loadYamlConfig("/db-config.yml");
        @SuppressWarnings("unchecked")
        Map<String, String> dbConfig = (Map<String, String>) yamlConfig.get("db");

        String url = dbConfig.get("url");
        String secret = System.getenv("DB_CONFIG_SECRET");
        if (secret == null || secret.isBlank()) {
            System.err.println("WARNING: DB_CONFIG_SECRET environment variable is not set. Using example fallback key.");
            secret = "DB_CONFIG_SECRET_EXAMPLE_ChangeMe";
        }

        String username = decrypt(extractEncryptedValue(dbConfig.get("username")), dbConfig.get("iv"), secret);
        String password = decrypt(extractEncryptedValue(dbConfig.get("password")), dbConfig.get("iv"), secret);

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
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