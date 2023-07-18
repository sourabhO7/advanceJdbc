package blobDataOperation;

import java.io.*;
import java.sql.*;

public class RetrieveBlobOperation {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/glologisticsdb";
        String username = "root";
        String password = "test";

        Connection connection = null;
        PreparedStatement pstmt = null;
        FileOutputStream fos = null;

        try{
            connection = DriverManager.getConnection(url, username, password);
            pstmt = connection.prepareStatement("SELECT * FROM supplier_table");

            ResultSet resultSet = pstmt.executeQuery();

            fos = new FileOutputStream("/home/sourabh/Documents/updated_invoice_db.jpg");

            if(resultSet.next()){
                int supId = resultSet.getInt(1);
                String supName = resultSet.getString(2);
                InputStream supInvoice = resultSet.getBinaryStream(3);

                byte [] buffer = new byte[2048];

                while (supInvoice.read(buffer)>0){
                    fos.write(buffer);
                }
                fos.flush();

                System.out.println("pdf file is created successfully after read operation from db");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // Close any open resources in the finally block
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (fos != null) {
                    fos.close();
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
