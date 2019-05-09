/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Christian
 */
public class DB_Access {

    private static DB_Access theInstance;

    public static DB_Access getInstance() {
        if (theInstance == null) {
            theInstance = new DB_Access();
        }
        return theInstance;
    }

    private DB_Access() {
    }
    private DB_StatementPool stmtPool = DB_StatementPool.getInstance();

    public String getAllTables() throws SQLException {
        String out = "";

        Statement statement = stmtPool.getStatement();
        String sqlString = "select table_name from information_schema.tables";
        ResultSet rs = statement.executeQuery(sqlString);
        while (rs.next()) {
            String table = rs.getString("table_name");
            out += table + "\n";
        }
        System.out.println(out);
        stmtPool.releaseStatement(statement);
        return out;        
    }

    public static void main(String[] args) {
        DB_Access dba = DB_Access.getInstance();
        try {
            String out = dba.getAllTables();
            System.out.println(out);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
