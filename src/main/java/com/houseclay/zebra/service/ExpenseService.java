package com.houseclay.zebra.service;

import com.houseclay.zebra.dto.ExpenseDTO;
import com.houseclay.zebra.model.expense.Expense;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface ExpenseService {
    public String addExpense(ExpenseDTO expenseDTO, String loggedInUser);
    public List<Expense> fetchAll(String loggedInUser);
    public String approveExpense(UUID uuid);
    public String disapproveExpense(UUID uuid);
    public String editExpense(ExpenseDTO expenseDTO);
    public void deleteExpense(UUID uuid);
}
