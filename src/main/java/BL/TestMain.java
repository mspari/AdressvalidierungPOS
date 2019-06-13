package BL;

import data.Address;
import database.DB_Access;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marinaspari
 */
public class TestMain
{
    public static void main(String[] args)
    {
        try
        {
            DB_Access.getInstance().insertAddress(new Address("test", 123, "test", "AB"));
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
