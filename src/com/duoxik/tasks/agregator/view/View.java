package com.duoxik.tasks.agregator.view;

import com.duoxik.tasks.agregator.Controller;
import com.duoxik.tasks.agregator.vo.Vacancy;

import java.util.List;

public interface View {
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
