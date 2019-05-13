/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Christian
 */
public enum DB_StatementType {
    
    INSERT_ADDRESS("INSERT INTO Address(Street, HouseNr, ZipCode, City, Region, Country) VALUES(?,?,?,?,?,?);");
    
    String sqlString;

    private DB_StatementType(String sqlString) {
        this.sqlString = sqlString;
    }

    public String getSqlString() {
        return sqlString;
    }        
}
