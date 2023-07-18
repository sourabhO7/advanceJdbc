package blobDataOperation;

import java.sql.*;

public class DeleteBlobOperation {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/glologisticsdb";
        String username = "root";
        String password = "test";

        Connection connection = null;
        PreparedStatement pstmt = null;

        int supId = 1;

        try {
            connection = DriverManager.getConnection(url, username, password);
            pstmt = connection.prepareStatement("DELETE FROM supplier_table WHERE sup_id = ?");

            pstmt.setInt(1, supId);

            int rowsAffected = pstmt.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Record deleted successfully");
            }else {
                System.out.println("Deletion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close any open resources in the finally block
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
