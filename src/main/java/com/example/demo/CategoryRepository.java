package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.Locale;

public interface CategoryRepository extends CrudRepository<Categories,Long> {
    Categories findByName(String name);
}
