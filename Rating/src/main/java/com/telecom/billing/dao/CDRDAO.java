package com.telecom.billing.dao;

import com.telecom.billing.model.CDR;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CDRDAO {
    void saveCDR(CDR cdr, Connection conn) throws SQLException;
    List<CDR> getUnratedCDRs(Connection conn) throws SQLException;
    boolean cdrExists(CDR cdr, Connection conn) throws SQLException;
} 