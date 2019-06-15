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

    /**
    Returns "the" instance of the DB_Access class
    @return 
     */
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

    /**
    Inserts the address, passed as Parameter into the current connected DB
    (credentials can be found in DB_Config)
    @param address
    @throws SQLException 
     */
    public void insertAddress(Address address) throws SQLException
    {
        PreparedStatement insertAddress = stmtPool.getPreparedStatement(DB_StatementType.INSERT_ADDRESS);
        Statement stat = stmtPool.getStatement();
        insertAddress.setString(1, address.getStreet());
        insertAddress.setInt(2, address.getHouseNr());

        int zipCode = -1;
        int region = -1;
        String country = "";

        // check whether ZipCode is already in the database, otherwise insert it (for the FK)
        ResultSet rs = stat.executeQuery("SELECT ZipCode FROM ZipCode WHERE ZipCode=" + address.getZipCode() + ";");
        while (rs.next())
        {
            zipCode = rs.getInt("ZipCode");
            break;
        }
        if (zipCode == -1)
        {
            PreparedStatement prepStat = stmtPool.getPreparedStatement(DB_StatementType.INSERT_ZIPCODE);

            prepStat.setInt(1, address.getZipCode());
            prepStat.setString(2, address.getCity());
            prepStat.execute();
            zipCode = address.getZipCode();

            stmtPool.releaseStatement(stat);
        }
        insertAddress.setInt(3, zipCode);

        // check whether Region is already  in the database, otherwise insert it (for the FK)
        // if address doesn't have a region, set it as undefined
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
            PreparedStatement prepStat = stmtPool.getPreparedStatement(DB_StatementType.INSERT_REGION);
            prepStat.setString(1, address.getRegion());
            prepStat.execute();
            
            rs = stat.executeQuery("SELECT * FROM Region WHERE RegionName LIKE '" + address.getRegion() + "';");
            while (rs.next())
            {
                region = rs.getInt("RegionID");
                break;
            }
            stmtPool.releaseStatement(prepStat);
        }
        insertAddress.setInt(4, region);

        // get Country FK from Country Table (for FK)
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

        //insert the Address into the AddressTable
        insertAddress.execute();

        stmtPool.releaseStatement(stat);
        stmtPool.releaseStatement(insertAddress);
    }

    /**
    FOR DEVELOPMENT PURPOSES: get the schema of all tables in the DB
    @return
    @throws SQLException 
     */
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

    /*
    public static void main(String[] args)
    {
        DB_Access dba = DB_Access.getInstance();
        try
        {
            dba.getAllTables();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
    }*/
}
