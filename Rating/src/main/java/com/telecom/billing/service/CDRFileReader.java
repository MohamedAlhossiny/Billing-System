package com.telecom.billing.service;

import com.telecom.billing.model.CDR;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CDRFileReader {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String DELIMITER = "|";
    
    public List<CDR> readCDRsFromFolder(String folderPath) {
        List<CDR> cdrs = new ArrayList<>();
        Path path = Paths.get(folderPath);
        
        try {
            Files.walk(path)
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".cdr"))
                .forEach(file -> {
                    try {
                        cdrs.addAll(readCDRFile(file.toFile()));
                    } catch (IOException e) {
                        log.error("Error reading CDR file: " + file, e);
                    }
                });
        } catch (IOException e) {
            log.error("Error walking through CDR folder: " + folderPath, e);
        }
        
        return cdrs;
    }
    
    private List<CDR> readCDRFile(File file) throws IOException {
        List<CDR> cdrs = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    CDR cdr = parseCDRLine(line);
                    if (cdr != null) {
                        cdrs.add(cdr);
                    }
                } catch (Exception e) {
                    log.error("Error parsing CDR line: " + line, e);
                }
            }
        }
        
        return cdrs;
    }
    
    private CDR parseCDRLine(String line) {
        // Expected format: profileId|dialA|dialB|serviceType|durationVolume|startTime
        line = line.trim();  // Trim any leading/trailing whitespace
        String[] parts = line.split("\\" + DELIMITER);  // Escape the pipe character for regex
        if (parts.length != 6) {
            log.warn("Invalid CDR line format: " + line);
            return null;
        }
        
        try {
            CDR cdr = new CDR();
            cdr.setProfileId(Long.parseLong(parts[0]));
            cdr.setDialA(parts[1]);
            cdr.setDialB(parts[2]);
            cdr.setServiceType(CDR.ServiceType.valueOf(parts[3].toLowerCase()));
            cdr.setDurationVolume(Long.parseLong(parts[4]));
            cdr.setStartTime(LocalDateTime.parse(parts[5], DATE_FORMATTER));
            return cdr;
        } catch (Exception e) {
            log.error("Error parsing CDR line: " + line, e);
            return null;
        }
    }
} 