package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionRecordListener {
    private final TransactionService transactionService;

    public TransactionRecordListener(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
    public void handleTransaction(Transaction transaction) {
        transactionService.processTransaction(transaction);
    }
}
