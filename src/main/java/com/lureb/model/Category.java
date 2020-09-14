package com.lureb.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
public class Category {

    private String id;
    private String description;

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="id")
    @JsonIgnore
    private Set<Recipe> recipes = new HashSet<>();

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
