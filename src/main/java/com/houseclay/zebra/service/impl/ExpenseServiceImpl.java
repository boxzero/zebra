package com.houseclay.zebra.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.houseclay.zebra.dto.ExpenseDTO;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.model.expense.Expense;
import com.houseclay.zebra.repository.ExpenseRepository;
import com.houseclay.zebra.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;
    @Override
    public String addExpense(ExpenseDTO expenseDTO,String loggedInUser) {
        Expense expense = Expense.builder().expenseType(expenseDTO.getExpenseType())
                        .expenseMadeBy(expenseDTO.getExpenseMadeBy())
                .amount(expenseDTO.getAmount()).date(expenseDTO.getDate()).notes(expenseDTO.getNotes())
                .baseTimeStamp(BaseTimeStamp.builder().created_on(new Date()).created_by(loggedInUser).build()).build();

        try{
            expenseRepository.save(expense);
            return "Expense Data saved successfully";
        }
        catch(Exception e){
            return "Error in Saving Data";
        }

    }

    @Override
    public List<Expense> fetchAll(String loggedInUser) {
        ArrayList<Expense> expenseArrayList = new ArrayList<>();
        try{
            expenseArrayList = (ArrayList<Expense>) expenseRepository.findAll();
        }catch (Exception ex) {
            log.error("Error in fetchAll Expense Service Impl ");
        }

        return expenseArrayList;
    }

    @Override
    public String approveExpense(UUID uuid,String loggedInUser) {
        return null;
    }

    @Override
    public String reimburseExpense(UUID uuid,String loggedInUser) {
        return null;
    }

    @Override
    public String editExpense(ExpenseDTO expenseDTO,String loggedInUser, UUID expenseId) {
        try{
            Optional<Expense> expense = expenseRepository.findById(expenseId);
            if(expense.isPresent()){
                //update the record
                BaseTimeStamp baseTimeStamp = BaseTimeStamp.builder().
                        created_by(expense.get().getBaseTimeStamp().getCreated_by())
                        .created_on(expense.get().getBaseTimeStamp().getCreated_on())
                        .changed_on(new Date())
                        .changed_by(loggedInUser).build();

                Expense updatedExpense = Expense.builder()
                        .baseTimeStamp(baseTimeStamp)
                        .expenseMadeBy(expenseDTO.getExpenseMadeBy())
                        .expenseType(expenseDTO.getExpenseType()).
                        date(expenseDTO.getDate())
                        .amount(expenseDTO.getAmount())
                        .notes(expenseDTO.getNotes()).build();

                expenseRepository.save(updatedExpense);
                return "Expense updated successfully!";
            }
        }catch (Exception e) {

        }
        return "Expense cannot be updated: Record missing/Data Issue";
    }

    @Override
    public Expense viewExpense(UUID expenseId) {
        Expense expenseDetails = new Expense();
        try{
            Optional<Expense> expense = expenseRepository.findById(expenseId);
            if(expense.isPresent()){
                expenseDetails = expense.get();
            }
        }catch(Exception e) {
            log.error("Exeption in viewExpense Implementation method");
        }
        return expenseDetails;
    }

    @Override
    public void deleteExpense(UUID uuid,String loggedInUser) {

    }
}
