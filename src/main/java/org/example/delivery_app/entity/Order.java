package org.example.delivery_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.delivery_app.enums.Status;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date dateTime;
    private Status status;

    @ManyToOne
    private Location location;

    @ManyToOne
    private User operator;

    @ManyToOne
    private TelegramUser tgUser;




}
