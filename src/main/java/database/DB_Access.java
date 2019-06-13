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
public class DB_Access
{

    private static DB_Access theInstance;

    public static DB_Access getInstance()
    {
        if (theInstance == null)
        {
            theInstance = new DB_Access();
        }
        return theInstance;
    }

    private DB_Access()
    {
    }
    private DB_StatementPool stmtPool = DB_StatementPool.getInstance();

    public void insertAddress(Address address) throws SQLException
    {
        PreparedStatement insertAddress = stmtPool.getPreparedStatement(DB_StatementType.INSERT_ADDRESS);
        Statement stat = stmtPool.getStatement();
        insertAddress.setString(1, address.getStreet());
        insertAddress.setInt(2, address.getHouseNr());
        int zipCode = 0;
        int region = 0;
        String country = "";
        ResultSet rs = stat.executeQuery("SELECT ZipCode FROM ZipCode WHERE ZipCode LIKE '" + address.getZipCode() + "';");
        while (rs.next())
        {
            zipCode = rs.getInt("ZipCode");
        }
        if (zipCode == 0)
        {
            zipCode = address.getZipCode();
        }
        else
        {
            stat.executeQuery("INSERT INTO ZipCode VALUES(" + address.getZipCode() + "," + address.getCity() + ");");
        }
        insertAddress.setInt(3, zipCode);
        insertAddress.setString(4, address.getCity());
        rs = stat.executeQuery("SELECT * FROM Region WHERE RegionName LIKE '" + address.getRegion() + "';");

        while (rs.next())
        {
            region = rs.getInt("RegionID");
        }
        if (region == 0)
        {
            region = 1;
        }
        else
        {
            stat.executeQuery("INSERT INTO Region(RegionID,RegionName) VALUES(" + region + ";" + address.getRegion() + ");");
        }

        insertAddress.setInt(5, region);
        rs = stat.executeQuery("SELECT * FROM Country WHERE CountryLong LIKE '" + address.getCountry() + "';");
        while (rs.next())
        {
            country = rs.getString("CoutryLong");
        }
        if (country.isEmpty())
        {
            country = address.getCountry();
        }
        else
        {
            stat.executeQuery("INSERT INTO Coutry VALUES(" + address.getCountry() + ");");
        }
        insertAddress.setString(6, country);
        insertAddress.execute();
    }

    public String getAllTables() throws SQLException
    {
        String out = "";

        Statement stat = stmtPool.getStatement();
        String sqlString = "select table_name from information_schema.tables";
        ResultSet rs = stat.executeQuery(sqlString);
        while (rs.next())
        {
            String table = rs.getString("table_name");
            out += table + "\n";
        }
        System.out.println(out);
        stmtPool.releaseStatement(stat);
        return out;
    }

    public static void main(String[] args)
    {
        DB_Access dba = DB_Access.getInstance();
        try
        {
            String out = dba.getAllTables();
            System.out.println(out);
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
    }
}
