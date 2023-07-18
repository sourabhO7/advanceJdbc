package clobDataOperation;

import java.io.*;
import java.sql.*;

public class UpdateClobOperation {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/glologisticsdb";
        String username = "root";
        String password = "test";

        Connection connection = null;
        PreparedStatement pstmt = null;
        BufferedReader reader = null;

        int supId = 2;
        String supName = "John";
        File textFile = new File("/home/sourabh/Documents/supplier.xml");

        try {
            connection = DriverManager.getConnection(url, username, password);
            reader = new BufferedReader(new FileReader(textFile));
            pstmt = connection.prepareStatement("UPDATE supplier_table SET sup_invoice = ? WHERE sup_id = ?");

            pstmt.setCharacterStream(1, reader);
            pstmt.setInt(2, supId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("CLOB data updated successfully.");
            } else {
                System.out.println("No records found for the given supplier ID.");
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
