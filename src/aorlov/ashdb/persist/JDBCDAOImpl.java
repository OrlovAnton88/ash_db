package aorlov.ashdb.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCDAOImpl implements JDBCDAO {

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(JDBCDAO.DRIVER);
            connection = DriverManager.getConnection(JDBCDAO.URL + "?" + JDBCDAO.ENCODING, JDBCDAO.USER, JDBCDAO.PASSWORD);
        } catch (SQLException ex) {
            throw new SQLException("Error in connection",ex);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Unable to find Driver", ex);
        }
        return connection;
    }
}
