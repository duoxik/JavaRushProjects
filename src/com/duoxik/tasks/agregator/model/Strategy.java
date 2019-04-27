package com.duoxik.tasks.agregator.model;

import com.duoxik.tasks.agregator.vo.Vacancy;

import java.util.List;

public interface Strategy {
    List<Vacancy> getVacancies(String searchString);
}
