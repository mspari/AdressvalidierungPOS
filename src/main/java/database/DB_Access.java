/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import data.Address;
import java.sql.PreparedStatement;
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

    public void insertAddress(Address address) throws SQLException {
        PreparedStatement insertAddress = stmtPool.getPreparedStatement(DB_StatementType.INSERT_ADDRESS);
        Statement stat = stmtPool.getStatement();
        insertAddress.setString(1, address.getStreet());
        insertAddress.setInt(2, address.getHouseNr());
        int zipCode = 0;
        int RegionID = 0;
        ResultSet rs = stat.executeQuery("SELECT * FROM ZipCode;");
        while (rs.next()) {
            zipCode = rs.getInt("ZipCode");
        }
        if (zipCode == 0) {
            zipCode = address.getZipCode();
        } else {
            stat.executeQuery("INSERT INTO ZipCode VALUES(" + address.getZipCode() + "," + address.getCity() + ");");
        }
        insertAddress.setInt(3, zipCode);
        

    }

    public String getAllTables() throws SQLException {
        String out = "";

        Statement stat = stmtPool.getStatement();
        String sqlString = "select table_name from information_schema.tables";
        ResultSet rs = stat.executeQuery(sqlString);
        while (rs.next()) {
            String table = rs.getString("table_name");
            out += table + "\n";
        }
        System.out.println(out);
        stmtPool.releaseStatement(stat);
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
