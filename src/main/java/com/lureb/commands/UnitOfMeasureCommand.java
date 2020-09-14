package com.lureb.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UnitOfMeasureCommand {
    private String id;
    private String uom;
}
