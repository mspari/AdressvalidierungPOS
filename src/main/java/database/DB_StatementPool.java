/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author Christian
 */
public class DB_StatementPool {

    private static DB_StatementPool theInstance;

    public static DB_StatementPool getInstance() {

        if (theInstance == null) {
            theInstance = new DB_StatementPool();
        }

        return theInstance;
    }

    public DB_StatementPool() {

    }

    private DB_ConnectionPool connectionPool = DB_ConnectionPool.getInstance();
    private Map<Connection, Queue<Statement>> statementMap = new HashMap();
   

    public synchronized Statement getStatement() throws SQLException {

        Connection connection = connectionPool.getConnection();
        Queue<Statement> stmtList = statementMap.get(connection);

        if (stmtList == null) {
            stmtList = new LinkedList<>();
            statementMap.put(connection, stmtList);
        }

        Statement statement = stmtList.poll();

        if (statement == null) {
            statement = connection.createStatement();
        }

        return statement;
    }

    public synchronized void releaseStatement(Statement statement) throws SQLException {
        Connection connection = statement.getConnection();

        if (!(statement instanceof PreparedStatement)) {
            Queue<Statement> statementPool = statementMap.get(connection);
            statementPool.offer(statement);
        }

        connectionPool.releaseConnection(connection);
    }
}
