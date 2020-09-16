package com.lureb.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CategoryCommand {
    private String id;
    private String description;
    private List<String> recipeIds = new ArrayList<>();
}
