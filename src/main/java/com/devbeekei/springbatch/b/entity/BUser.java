package com.devbeekei.springbatch.b.entity;

import lombok.*;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;

@Builder
@Getter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BUser {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

}
