package com.telecom.billing;

import com.telecom.billing.model.CDR;
import com.telecom.billing.model.RatedCDR;
import com.telecom.billing.service.CDRFileReader;
import com.telecom.billing.service.DatabaseService;
import com.telecom.billing.service.RatingService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class RatingEngine {
    private final RatingService ratingService;
    private final DatabaseService databaseService;
    private final CDRFileReader cdrFileReader;

    public RatingEngine() {
        this.ratingService = new RatingService();
        this.databaseService = new DatabaseService();
        this.cdrFileReader = new CDRFileReader();
    }

    public void processCDRs() {
        try (Connection conn = com.telecom.billing.config.DatabaseConfig.getConnection()) {
            // Read CDRs from files
            List<CDR> cdrs = cdrFileReader.readCDRsFromFolder("CDRs");
            log.info("Read {} CDRs from files", cdrs.size());

            // Save new CDRs to database
            int newCDRsCount = 0;
            for (CDR cdr : cdrs) {
                if (!databaseService.cdrExists(cdr, conn)) { // Check if CDR already exists
                    databaseService.saveCDR(cdr);
                    newCDRsCount++;
                }
            }
            log.info("Saved {} new CDRs to database", newCDRsCount);

            // Get unrated CDRs from database
            List<CDR> unratedCDRs = databaseService.getUnratedCDRs();
            log.info("Retrieved {} unrated CDRs from database", unratedCDRs.size());

            // Rate the CDRs (saving is done within RatingService)
            ratingService.rateCDRs(unratedCDRs);
            log.info("Rating process completed.");

        } catch (Exception e) {
            log.error("Error processing CDRs", e);
        }
    }

    public static void main(String[] args) {
        RatingEngine engine = new RatingEngine();
        engine.processCDRs();
    }
} 