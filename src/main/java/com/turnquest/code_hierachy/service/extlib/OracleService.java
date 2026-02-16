package com.turnquest.code_hierachy.service.extlib;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

@Service
@Slf4j
public class OracleService {

    private final DataSource oracleDataSource;
    private  JdbcTemplate jdbcTemplate;

    public OracleService(DataSource oracleDataSource) {
        this.oracleDataSource = oracleDataSource;
    }

    @PostConstruct
    public void setup(){
        this.jdbcTemplate = new JdbcTemplate(oracleDataSource);
    }

    public String getPlsqlCode(String objectName, String objectType) {
        String sql = "SELECT TEXT FROM ALL_SOURCE WHERE TYPE = ? AND NAME = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, objectType.toUpperCase(), objectName.toUpperCase());
        } catch (Exception e) {
            log.error("Exception occurred", e);
            return null;
        }
    }
}