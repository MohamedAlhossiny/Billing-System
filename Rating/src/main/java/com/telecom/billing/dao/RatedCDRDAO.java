package com.telecom.billing.dao;

import com.telecom.billing.model.CDR;
import com.telecom.billing.model.RatedCDR;
import java.sql.Connection;
import java.sql.SQLException;

public interface RatedCDRDAO {
    void saveRatedCDR(RatedCDR ratedCDR, Connection conn) throws SQLException;
    RatedCDR getRatingRule(CDR cdr, Connection conn) throws SQLException;
} 