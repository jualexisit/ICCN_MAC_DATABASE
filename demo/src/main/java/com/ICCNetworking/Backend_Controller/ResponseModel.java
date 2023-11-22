package com.ICCNetworking.Backend_Controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel  {

    
    private int status;

    public int getStatus() {
        return status;
    }

    public ResponseModel setStatus(int status) {
        this.status = status;
        return this;
    }

    
}
