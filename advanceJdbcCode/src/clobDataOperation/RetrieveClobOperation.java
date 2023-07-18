package clobDataOperation;

import java.io.*;
import java.sql.*;

public class RetrieveClobOperation {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/glologisticsdb";
        String username = "root";
        String password = "test";

        Connection connection = null;
        PreparedStatement pstmt = null;
        FileWriter fr = null;

        try{
            connection = DriverManager.getConnection(url, username, password);
            pstmt = connection.prepareStatement("SELECT * FROM supplier_table");
            fr = new FileWriter("/home/sourabh/Documents/sample_xml_output.xml");
            ResultSet resultSet = pstmt.executeQuery();

            if(resultSet.next()){
                int supId = resultSet.getInt(1);
                String supName = resultSet.getString(2);
                Reader invoiceRetrieved = resultSet.getCharacterStream(3);

                char [] buffer = new char[2048];

                while (invoiceRetrieved.read(buffer)>0){
                    fr.write(buffer);
                }
                fr.flush();

                System.out.println("text file is created successfully after read operation from db");
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
                if (fr != null) {
                    fr.close();
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
