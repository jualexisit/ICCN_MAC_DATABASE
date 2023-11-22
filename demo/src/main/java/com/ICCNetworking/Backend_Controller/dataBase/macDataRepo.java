package com.ICCNetworking.Backend_Controller.dataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class macDataRepo {
    
    private final NamedParameterJdbcTemplate nTemplate;
    private final ObjectMapper mapper;

    @Autowired
    public macDataRepo(NamedParameterJdbcTemplate nTemplate, ObjectMapper mapper)
    {
        this.nTemplate = nTemplate;
        this.mapper = mapper;
    }

    public ObjectMapper getMapper() 
    {
        return mapper;
    }

    public NamedParameterJdbcTemplate getnTemplate() 
    {
        return nTemplate;
    }

}
