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
        int zipCode = -1;
        int region = -1;
        String country = "";
        ResultSet rs = stat.executeQuery("SELECT ZipCode FROM ZipCode WHERE ZipCode=" + address.getZipCode() + ";");
        while (rs.next())
        {
            zipCode = rs.getInt("ZipCode");
            break;
        }
        if (zipCode == -1)
        {
            stat.execute("INSERT INTO ZipCode VALUES('" + address.getZipCode() + "','" + address.getCity() + "');");
            zipCode = address.getZipCode();
        }
        insertAddress.setInt(3, zipCode);

        if (address.getRegion() == null || address.getRegion().equals(""))
        {
            address.setRegion("Undefined");
        }
        rs = stat.executeQuery("SELECT * FROM Region WHERE RegionName LIKE '" + address.getRegion() + "';");
        while (rs.next())
        {
            region = rs.getInt("RegionID");
            break;
        }
        if (region == -1)
        {
            stat.execute("INSERT INTO Region(RegionName) VALUES('" + address.getRegion() + "');");
            rs = stat.executeQuery("SELECT * FROM Region WHERE RegionName LIKE '" + address.getRegion() + "';");
            while (rs.next())
            {
                region = rs.getInt("RegionID");
                break;
            }
        }
        insertAddress.setInt(4, region);

        if (address.getCountry() == null || address.getCountry().equals(""))
        {
            address.setCountry("XX");
        }
        rs = stat.executeQuery("SELECT * FROM Country WHERE CountryShort LIKE '" + address.getCountry() + "';");
        while (rs.next())
        {
            country = rs.getString("CountryShort");
        }

        insertAddress.setString(5, country);
        insertAddress.execute();
        
        stmtPool.releaseStatement(stat);
        stmtPool.releaseStatement(insertAddress);
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
            dba.insertAddress(new Address("krottendorf", 2345, "ads", "AT"));
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
    }
}
