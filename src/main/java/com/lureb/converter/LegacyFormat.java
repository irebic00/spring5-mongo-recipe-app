package com.lureb.converter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
class LegacyFormat {
    private long timestamp;
    private int machineIdentifier;
    private short processIdentifier;
    private int counter;
    @JsonIgnore
    private long time;
    private Date date;
    private long timeSecond;
}
