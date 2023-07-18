package blobDataOperation;

import java.io.*;
import java.sql.*;

public class UpdateBlobOperation {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/glologisticsdb";
        String username = "root";
        String password = "test";

        Connection connection = null;
        PreparedStatement pstmt = null;
        FileInputStream fis = null;

        int supId = 1;
        File newInvoice = new File("/home/sourabh/Documents/new_invoice.jpg");

        try {
            connection = DriverManager.getConnection(url, username, password);
            fis = new FileInputStream(newInvoice);
            pstmt = connection.prepareStatement("UPDATE supplier_table SET sup_invoice = ? WHERE sup_id = ?");

            pstmt.setBinaryStream(1, fis);
            pstmt.setInt(2, supId);

            int rowsAffected = pstmt.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("New invoice update in the table");
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
