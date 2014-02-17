package aorlov.ashdb.persist;

import java.sql.Connection;
import java.sql.SQLException;


public interface JDBCDAO {

    public static final String URL = "jdbc:mysql://localhost:3306/ash";
    public static final String ENCODING = "useUnicode=true&characterEncoding=UTF-8";
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASSWORD= "mouse";

    public Connection getConnection() throws SQLException;
}
