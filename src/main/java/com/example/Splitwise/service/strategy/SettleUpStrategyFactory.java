package com.example.Splitwise.service.strategy;

import org.springframework.stereotype.Service;

@Service
public class SettleUpStrategyFactory {
    //TODO : add an enum for different strategies, and then update this method accordingly
    public static SettleUpStrategy getSettleUpStrategy(){
        return new HeapBasedSettleUpStrategy();
    }
}
