package com.ICCNetworking.Backend_Controller.sql_utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.ICCNetworking.Backend_Controller.dataBase.macDataRepo;

@Component
public class sql_helper {

    private final String pathStr = "D:\\ICCN Engineering\\ICCN_MACs_Engineering\\ICCN_MAC_DATABASE\\demo\\src\\main\\java\\com\\ICCNetworking\\Backend_Controller\\sql_utility\\sql_mac_ptr.txt";
    private final BigInteger startingMAC = new BigInteger("273743280603136", 10);
    private final BigInteger endingMAC = new BigInteger("273743297380351", 10);

    public BigInteger checkSQLMACPtr() throws FileNotFoundException
    {
        File file = new File(pathStr);
        Scanner stream = new Scanner(file);
        
        BigInteger mac = new BigInteger(stream.nextLine().strip(), 16);
        stream.close();

        return mac;
    }
    

    public void setSQLMACPtr(BigInteger ptr) throws IOException
    {
        File file = new File(pathStr);
        FileWriter stream = new FileWriter(file);
        
        stream.write(ptr.toString(16));

        stream.close();
    }

    public boolean checkRange(BigInteger mac)
    {
        if (mac.compareTo(startingMAC) == -1)
        {
            return false;
        }
        else if (mac.compareTo(endingMAC) == 1)
        {
            return false;
        }
        return true;
    }

}