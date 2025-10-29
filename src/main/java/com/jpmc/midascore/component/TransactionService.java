package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    @Autowired
    private RestTemplate restTemplate;

    public TransactionService(UserRepository userRepository,TransactionRecordRepository transactionRecordRepository){
        this.userRepository=userRepository;
        this.transactionRecordRepository=transactionRecordRepository;
    }

    @Transactional
    public void processTransaction(com.jpmc.midascore.foundation.Transaction transaction){
        UserRecord sender=userRepository.findById(transaction.getSenderId());
        UserRecord recipient=userRepository.findById(transaction.getRecipientId());

        if(sender==null || recipient==null){
            System.out.println("Transaction discarded: invalid sender or recipient");
            return;
        }

        if(sender.getBalance()<transaction.getAmount()){
            System.out.println("Transaction discarded: insufficient balance");
            return;
        }
        
        String incentiveUrl="http://localhost:8080/incentive";
        Incentive incentiveResponse=restTemplate.postForObject(incentiveUrl, transaction, Incentive.class);
        float incentiveAmount=(incentiveResponse!=null)?incentiveResponse.getAmount():0;
        //Update Balance
        sender.setBalance(sender.getBalance()-transaction.getAmount());
        recipient.setBalance(recipient.getBalance()+transaction.getAmount()+incentiveAmount);
        
        userRepository.save(sender);
        userRepository.save(recipient);

        //Record Transaction
        TransactionRecord record=new TransactionRecord(sender, recipient,transaction.getAmount(),LocalDateTime.now());
        transactionRecordRepository.save(record);
    }
}
