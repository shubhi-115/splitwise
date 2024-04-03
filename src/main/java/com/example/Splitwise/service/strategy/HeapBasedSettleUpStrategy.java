package com.example.Splitwise.service.strategy;

import com.example.Splitwise.dto.TransactionDTO;
import com.example.Splitwise.model.Expense;
import com.example.Splitwise.model.User;
import com.example.Splitwise.model.UserExpense;
import com.example.Splitwise.model.UserExpenseType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Service
public class HeapBasedSettleUpStrategy implements SettleUpStrategy{
    public List<TransactionDTO> settleUp(List<Expense> expenses){
        HashMap<User, Double> outStandingAmountMap = new HashMap<>();
        List<TransactionDTO> transactions = new ArrayList<>();
        //calculate the final outstanding amount for each user
        for(Expense expense: expenses){
            for(UserExpense userExpense: expense.getUserExpenses()){
                User user = userExpense.getUser();
                double currentOutStandingAmount = outStandingAmountMap.getOrDefault(user, 0.00);
                outStandingAmountMap.put(user, getUpdatedOutStandingAmount(currentOutStandingAmount, userExpense));
            }
        }
        // MaxHeap -> contains all the users with positive balance
        PriorityQueue<Map.Entry<User, Double>> maxHeap = new PriorityQueue<Map.Entry<User, Double>>(
                (a,b) -> Double.compare(b.getValue(), a.getValue())
        );
        // MinHeap -> contains all the users with negative balance
        PriorityQueue<Map.Entry<User, Double>> minHeap = new PriorityQueue<Map.Entry<User, Double>>(
                (a,b) -> Double.compare(a.getValue(), b.getValue())
        );

        //populate the heaps using the values from the map
        for(Map.Entry<User, Double> entry: outStandingAmountMap.entrySet()){
            if(entry.getValue() < 0)
                minHeap.add(entry);
            else if(entry.getValue() > 0)
                maxHeap.add(entry);
            else
                System.out.println(entry.getKey().getName() + "'s is already settled up");
        }
        //calculate the transactions until the heaps become empty
        while(!minHeap.isEmpty()){
            //removing out min from minHead and max from maxHeap
            Map.Entry<User,Double> maxHasToPay = minHeap.poll();
            Map.Entry<User,Double> maxWillGetPaid = maxHeap.poll();
            TransactionDTO transactionDTO = new TransactionDTO(
                    maxHasToPay.getKey().getName(),
                    maxWillGetPaid.getKey().getName(),
                    Math.min(Math.abs(maxHasToPay.getValue()), Math.abs(maxWillGetPaid.getValue()) ));
            transactions.add(transactionDTO);
            double newBalance = maxHasToPay.getValue() + maxWillGetPaid.getValue();
            if(newBalance == 0){
                //means both are equal and settled the balances
                System.out.println("Settle for: "+ maxHasToPay.getKey().getName() + ", and " + maxWillGetPaid.getKey().getName());
            }else if(newBalance < 0) { // means person who had to pay was greater in value, so goes back t0 min Heap with new updated balance
                maxHasToPay.setValue(newBalance);
                minHeap.add(maxHasToPay);
            } else if(newBalance > 0){ // means person who will get paid was greater in value, so goes back t0 max Heap with new updated balance
                maxWillGetPaid.setValue(newBalance);
                maxHeap.add(maxWillGetPaid);
            }
        }
        return transactions;
    }

    private double getUpdatedOutStandingAmount(double currentOutStandingAmount, UserExpense userExpense){
        if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)){
            currentOutStandingAmount = currentOutStandingAmount + userExpense.getAmount();
        } else {
            currentOutStandingAmount = currentOutStandingAmount - userExpense.getAmount();
        }
        return currentOutStandingAmount;
    }
}
