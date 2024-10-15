package com.btg.test.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document(collection = "cliente")
public class Cliente {

    @Id
    private Long id;

    private String nombre;

    private int saldo;

    private String email;

    private String telefono;

}
