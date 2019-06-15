/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;

/**
 *
 * @author Christian
 */
public class DB_Connect
{

    /*
        Kept for further Development/testing purposes
     */
    public static void main(String args[])
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/FGfhOziZCa", "FGfhOziZCa", "LWMdDF6CPv");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables;");
            while (rs.next())
            {
                System.out.println(rs.getString("table_name"));
            }
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
