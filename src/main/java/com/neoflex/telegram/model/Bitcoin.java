package com.neoflex.telegram.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "bitcoins")
@Entity
public class Bitcoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "usd")
    private Double usd;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
}
