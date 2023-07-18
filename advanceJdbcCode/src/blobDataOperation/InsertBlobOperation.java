package blobDataOperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertBlobOperation {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/glologisticsdb";
        String username = "root";
        String password = "test";

        Connection connection = null;
        PreparedStatement pstmt = null;
        FileInputStream fis = null;

        int supId = 1;
        String supName = "Alexander";
        File invoiceFile = new File("/home/sourabh/Documents/sample_invoice.jpg");

        try{
            connection = DriverManager.getConnection(url, username, password);
            fis = new FileInputStream(invoiceFile);
            pstmt = connection.prepareStatement("INSERT INTO supplier_table (sup_id, sup_name, sup_invoice) VALUES (?, ?, ?)");

            pstmt.setInt(1, supId);
            pstmt.setString(2, supName);
            pstmt.setBinaryStream(3, fis);

            pstmt.executeUpdate();
            System.out.println("Data inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            // Close any open resources in the finally block
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (fis != null) {
                    fis.close();
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