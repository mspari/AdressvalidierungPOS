/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import data.Address;

/**
 *
 * @author Christoph Klampfer
 */
public class InputData
{

    /** 
    Method which takes a csv File with Addresses, one per line.
    Reads the Addresses and returns a List of them
    -> if the address doesn't match the desired format, it'll be ignored
    
    @param file
    @return 
    @throws FileNotFoundException
    @throws IOException 
     */
    public static List<Address> readCsv(File file) throws FileNotFoundException, IOException
    {
        List<Address> adressen = new LinkedList<>();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String s;
        while ((s = br.readLine()) != null)
        {
            String[] parts = s.split(";");
            if (parts.length == 4)
            {
                try
                {
                    Address addr = new Address(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]);
                    adressen.add(addr);
                }
                catch (NumberFormatException ex)
                {
                    //skip address
                }
            }
            else
            {
                //skip address
            }
        }
        return adressen;
    }
}
