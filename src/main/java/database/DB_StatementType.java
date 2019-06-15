package database;

/**
 *
 * @author Christian
 */
public enum DB_StatementType
{
    // Prepared statement to insert into the tables
    INSERT_ADDRESS("INSERT INTO Address(Street, HouseNr, ZipCode, Region, Country) VALUES(?,?,?,?,?);"),
    INSERT_ZIPCODE("INSERT INTO ZipCode VALUES(?,?);"),
    INSERT_REGION("INSERT INTO Region(RegionName) VALUES(?);");

    String sqlString;

    private DB_StatementType(String sqlString)
    {
        this.sqlString = sqlString;
    }

    public String getSqlString()
    {
        return sqlString;
    }
}
