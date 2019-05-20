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
public class readCsv {

    public List<Address> readCsv(File file) throws FileNotFoundException, IOException {
        List<Address> adressen = new LinkedList<>();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String s;
        while ((s = br.readLine()) != null) {
            String[] parts = s.split(";");
            Address addr = new Address(parts[0], Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]), parts[3], parts[4], parts[5]);
            adressen.add(addr);
        }
        return adressen;
    }
}
