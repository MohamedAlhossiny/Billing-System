package com.telecom.billing.service;

import com.telecom.billing.config.DatabaseConfig;
import com.telecom.billing.dao.CDRDAO;
import com.telecom.billing.dao.RatedCDRDAO;
import com.telecom.billing.dao.impl.CDRDAOImpl;
import com.telecom.billing.dao.impl.RatedCDRDAOImpl;
import com.telecom.billing.model.CDR;
import com.telecom.billing.model.RatedCDR;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RatingService {
    
    private final CDRDAO cdrDAO;
    private final RatedCDRDAO ratedCDRDAO;
    
    public RatingService() {
        this.cdrDAO = new CDRDAOImpl();
        this.ratedCDRDAO = new RatedCDRDAOImpl();
    }
    
    public List<RatedCDR> rateCDRs(List<CDR> cdrs) {
        List<RatedCDR> ratedCDRs = new ArrayList<>();
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            for (CDR cdr : cdrs) {
                RatedCDR ratedCDR = ratedCDRDAO.getRatingRule(cdr, conn);
                if (ratedCDR != null) {
                    ratedCDRs.add(ratedCDR);
                    ratedCDRDAO.saveRatedCDR(ratedCDR, conn);
                }
            }
        } catch (SQLException e) {
            log.error("Error during rating process", e);
        }
        
        return ratedCDRs;
    }
} 