package com.inventorySystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_admin")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String position;

    @Column(nullable=false)
    private String username;

    @Column(nullable=false)
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
