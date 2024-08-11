package com.houseclay.zebra.controller.expense;

import com.houseclay.zebra.controller.BaseController;
import com.houseclay.zebra.dto.ExpenseDTO;
import com.houseclay.zebra.model.expense.Expense;
import com.houseclay.zebra.service.ExpenseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/expense")
@Api(tags = "Expense APIs")
public class ExpenseController extends BaseController {


    @Autowired ExpenseService expenseService;

    @PostMapping("/add")
    public ResponseEntity<String> addExpense(@RequestBody ExpenseDTO expenseDTO, @RequestHeader("Authorization") String token) {
       return ResponseEntity.status(HttpStatus.OK).
               body(expenseService.addExpense(expenseDTO,findUsernameFromHeader(token)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Expense>> fetchExpenses(@RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(expenseService.fetchAll(findUsernameFromHeader(token)));
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editExpense(@RequestHeader("Authorization") String token, @RequestBody ExpenseDTO expenseDTO,@PathVariable("id") String uuid) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(expenseService.editExpense(expenseDTO,findUsernameFromHeader(token), UUID.fromString(uuid)));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Expense> viewExpense(@RequestHeader("Authorization") String token,@PathVariable("id") String uuid) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(expenseService.viewExpense(UUID.fromString(uuid)));
    }
}
