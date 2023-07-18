package clobDataOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertClobOperation {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/glologisticsdb";
        String username = "root";
        String password = "test";

        Connection connection = null;
        PreparedStatement pstmt = null;
        BufferedReader reader = null;

        int supId = 2;
        String supName = "John";
        File textFile = new File("/home/sourabh/Documents/output.xml");

        try {
            connection = DriverManager.getConnection(url, username, password);
            reader = new BufferedReader(new FileReader(textFile));
            pstmt = connection.prepareStatement("INSERT INTO supplier_table (sup_id, sup_name, sup_invoice) VALUES (?, ?, ?)");

            pstmt.setInt(1, supId);
            pstmt.setString(2, supName);
            pstmt.setCharacterStream(3, reader);

            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("File inserted successfully");
            }else {
                System.out.println("Insertion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close any open resources in the finally block
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
