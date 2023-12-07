package com.ICCNetworking.Backend_Controller.resp_center;
import org.springframework.web.bind.annotation.RequestParam;

public class mac_blockData {
    
    private Integer mac_block;
    private String vendor;

    public Integer getMac_block() {
        return mac_block;
    }
    public void setMac_block(Integer mac_block) {
        this.mac_block = mac_block;
    }
    public String getVendor() {
        return vendor;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }



}
