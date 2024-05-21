package com.houseclay.zebra.service.impl;

import com.houseclay.zebra.dto.ExpenseDTO;
import com.houseclay.zebra.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Override
    public String addExpense(ExpenseDTO expenseDTO,String loggedInUser) {
        return null;
    }

    @Override
    public String approveExpense(UUID uuid) {
        return null;
    }

    @Override
    public String disapproveExpense(UUID uuid) {
        return null;
    }

    @Override
    public String editExpense(ExpenseDTO expenseDTO) {
        return null;
    }

    @Override
    public void deleteExpense(UUID uuid) {

    }
}
