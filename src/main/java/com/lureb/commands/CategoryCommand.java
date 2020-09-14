package com.lureb.commands;

import com.lureb.model.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class CategoryCommand {
    private String id;
    private String description;
    private Set<Recipe> recipes = new HashSet<>();
}
