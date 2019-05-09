/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Christian
 */
public class DB_ConnectionPool implements DB_Config {

    //implement ConnectionPool as Singleton
    private static DB_ConnectionPool theInstance;
    private final LinkedList<Connection> connectionPool = new LinkedList<>();
    private final int MAX_SIZE = 100;
    private int numConns = 0;

    public static DB_ConnectionPool getInstance() {
        if (theInstance == null) {
            theInstance = new DB_ConnectionPool();
        }
        return theInstance;
    }

    private DB_ConnectionPool() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (numConns == MAX_SIZE) {
                throw new RuntimeException("Connection limit reached - try again later");
            }
            Connection connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
            numConns++;
            return connection;
        }
        return connectionPool.poll();
    }

    public void releaseConnection(Connection connection) {
        if (!connectionPool.isEmpty()) {
            connectionPool.offer(connection);
        }
    }
}
