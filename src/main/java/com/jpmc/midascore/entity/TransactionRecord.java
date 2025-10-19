package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransactionRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserRecord recipient;

    @Column(nullable = false)
    private float amount;

    private LocalDateTime timestamp;
    protected TransactionRecord() {}

    public TransactionRecord(UserRecord sender,UserRecord recipient,float amount,LocalDateTime timestamp){
        this.sender=sender;
        this.recipient=recipient;
        this.amount=amount;
        this.timestamp=timestamp;
    }

    public Long getId() {return id;}
    public UserRecord getSender() {return sender;}
    public void setSender(UserRecord sender){this.sender=sender;}
    public UserRecord getRecipient() {return recipient;}
    public void setRecipient(UserRecord recipient) {this.recipient=recipient;}
    public float getAmount(){return amount;}
    public void setAmount(float amount){this.amount=amount;}
    public LocalDateTime getTimestamp() {return timestamp;}
    public void setTimestamp(LocalDateTime timestamp) {this.timestamp=timestamp;}
}
