package com.telecom.billing.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CDR {
    private Long cdrId;
    private Long profileId;
    private String dialA;
    private String dialB;
    private ServiceType serviceType;
    private Long durationVolume;
    private LocalDateTime startTime;

    public enum ServiceType {
        voice,
        sms,
        data
    }
} 