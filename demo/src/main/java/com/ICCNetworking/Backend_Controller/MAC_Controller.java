package com.ICCNetworking.Backend_Controller;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ICCNetworking.Backend_Controller.dataBase.macDataRepo;
import com.ICCNetworking.Backend_Controller.resp_center.*;
import com.ICCNetworking.Backend_Controller.sql_utility.*;


@RestController
public final class MAC_Controller {

    // dataRepo is how we will access and manage MySQL's data.
    private final macDataRepo dataRepo;

    // Contains modulated helper functions.
    private static sql_helper helper;

    // This is the ptr to the current MAC. It is properly saved in a separate
    // file so that restarting/moving the program does not accidentally 
    // move the MAC counter.
    // The current MAC the ptr points to is unused
    private static BigInteger ptr;

    // This initializes the MAC_Controller. It's values are
    // instantiated using @Autowired and the values are then passed
    // into the MAC_Controller. It contains the dataRepo, the sql manager;
    // the sql_helper, the supporting functions to keep the program modulated;
    // and it sets the ptr value.
    @Autowired
    public MAC_Controller(macDataRepo dataRepo, sql_helper helper) throws Exception
    {
        this.dataRepo = dataRepo;
        MAC_Controller.helper = helper;
        setMACPtr(getMACPtr());
    }

    @PutMapping("/mac/insert")
    public ResponseEntity<macResp> insertMAC() throws Exception
    {
        macResp respItem = new macResp().setMac(1);
        respItem.setStatus(200);

        System.out.println("procced");
    

        BigInteger mac_to_decimal = new BigInteger("f8f7d3000004",16);
        
        System.out.println("This is the mac_to_decimal" + mac_to_decimal);
        
        try
        {
            insertMACSQL(mac_to_decimal, "ICCN_Justin", Timestamp.from(Instant.now()));
       
        }
        catch (Exception e)
        {
            throw e;
        }
        
        return ResponseEntity.status(respItem.getStatus()).body(respItem);
    }


    // insert a group of macs
    @PutMapping("/mac/insert_block")
    public ResponseEntity<macResp> insertMACBlock(
        @RequestParam Optional<Integer> mac_block,
        @RequestParam Optional<String> vendor
    ) throws FileNotFoundException, Exception
    {

        // first, do value checks before doing computations
        if (mac_block.isEmpty())
        {
            throw new Exception("Number of Macs not specificed");
        }
        if (vendor.isEmpty())
        {
            throw new Exception("Vendor of Macs not specificed");
        }

        // check if the number of macs requested is legal
        BigInteger currentPtr = MAC_Controller.getMACPtr();
        if (!helper.checkRange(
             BigInteger.valueOf(mac_block.get()).add(currentPtr)))
        {
            throw new Exception("ICCN does not have enough MACs for you." +
            "Please Contact the parent company.");
        }

        macResp respItem = new macResp().setMac(1);
        respItem.setStatus(200);

        Timestamp timestamp = Timestamp.from(Instant.now());

        
        // create a loop that adds every MAC to the database individually

        for (Integer i = 0; i < mac_block.get() ; i++)
        {
            System.out.println(BigInteger.valueOf(i.intValue()));
            System.out.println(BigInteger.valueOf(i.intValue()).add(currentPtr));
            
            /*insertMACSQL(
                MAC_Controller.getMACPtr().add(BigInteger.valueOf(i.intValue())),
                vendor.get(), 
                timestamp);*/
            
        }

        // Finally, set the static ptr to its new value
        setMACPtr(BigInteger.valueOf(mac_block.get()).add(currentPtr));

        return ResponseEntity.status(respItem.getStatus()).body(respItem);
    }

    @PutMapping("/mac/test")
    public void test() throws IOException
    {
        System.out.println(helper.checkRange(new BigInteger("0",10)));
        System.out.println(helper.checkRange(new BigInteger("ffffffffffffff",16)));
        System.out.println(helper.checkRange(new BigInteger("273743280603143",10)));
        
    }

    @PutMapping("/mac/test/range")
    public void testRange() throws IOException
    {
        System.out.println(helper.checkRange(new BigInteger("0",10)));
        System.out.println(helper.checkRange(new BigInteger("ffffffffffffff",16)));
        System.out.println(helper.checkRange(new BigInteger("273743280603143",10)));
        
    }

    // inserts a MAC into the database with a vendor name and Timestamp.
    public void insertMACSQL(BigInteger mac, String vendor, Timestamp timestamp) throws Exception
    {
        String sql =
            "INSERT INTO mac_db.macs (mac_id, vendor, time_removed)" +
            "VALUES (:mac, :vendor, :timestamp);";

        MapSqlParameterSource source = new MapSqlParameterSource()
                                            .addValue("mac", mac)
                                            .addValue("vendor", vendor)
                                            .addValue("timestamp", timestamp);

        try
        {
            System.out.println("MAC Values Inserted: " + dataRepo.getnTemplate().update(sql, source));
        }
        catch (Exception e)
        {
            throw new Exception("MAC Value already exists");
        }

    }

    // setMACPtr sets the current MAC MAC_Controller.ptr to the value ptr_temp
    // This function is protected by helper.checkRange(). It confirms it is
    // within range and throws and exception if otherwise.
    private static void setMACPtr(BigInteger ptr_temp) throws Exception
    {
        if (helper.checkRange(ptr_temp))
        {
            helper.setSQLMACPtr(ptr_temp);
            MAC_Controller.ptr = ptr_temp;
        }
        else
        {
            throw new Exception("Value is not within the valid range from f8:f7:d3:00:00:00 to f8:f7:d3:ff:ff:ff");
        }
    }

    // getMACPtr gets where the current MAC Ptr is set to. Careful,
    // There are no inherent checks or verifications on the value that is pulled
    // Thus, double-check the file sql_mac_ptr.txt in sql_utility, but do
    // not alter it.
    public static BigInteger getMACPtr() throws FileNotFoundException
    {
        return helper.checkSQLMACPtr();
    }

}
