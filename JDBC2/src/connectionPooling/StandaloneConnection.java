package connectionPooling;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.sql.*;
import java.sql.*;

public class StandaloneConnection {
    public static void main(String[] args) {
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/glologisticsdb");
        dataSource.setUser("root");
        dataSource.setPassword("test");

        // Create DataSource object for connection pooling
        DataSource connectionPool = dataSource;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Get connection from the connection pool
            connection = connectionPool.getConnection();

            // Prepare and execute the SQL query
            String query = "SELECT * FROM supplier";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                String supId = resultSet.getString("supId");
                String supName = resultSet.getString("supName");
                String supType = resultSet.getString("supType");
                String supCity = resultSet.getString("supCity");
                String supEmail = resultSet.getString("supEmail");

                // Do something with the retrieved data
                System.out.println("Supplier ID: " + supId);
                System.out.println("Supplier Name: " + supName);
                System.out.println("Supplier Type: " + supType);
                System.out.println("Supplier City: " + supCity);
                System.out.println("Supplier Email: " + supEmail);
                System.out.println("-------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources in the reverse order of their creation
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
