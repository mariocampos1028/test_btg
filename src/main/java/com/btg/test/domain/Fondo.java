package com.btg.test.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "fondo")
public class Fondo {

    @Id
    private int id;

    private String nombre;

    private int monto_minimo;

    private String categoria;

}
