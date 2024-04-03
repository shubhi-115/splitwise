package com.example.Splitwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Splitwise.model.Expense;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
}
