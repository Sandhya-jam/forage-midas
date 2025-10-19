package com.jpmc.midascore.foundation;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionListener {
    
    @KafkaListener(topics = "${general.kafka-topic}",groupId = "midas-core-group")
    public void listen(Transaction transaction){
        System.out.println("Recieved transaction: "+transaction);
    }
}
