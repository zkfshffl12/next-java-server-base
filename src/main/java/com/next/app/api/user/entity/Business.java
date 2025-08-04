package com.next.app.api.user.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "business_tamibang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessNumber;

    private String companyName;

    private String representativeName;

    private String contactEmail;

    private String contactPhone;

    private String address;

    private LocalDate registeredAt;
}
