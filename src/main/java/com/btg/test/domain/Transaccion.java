package com.btg.test.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "transaccion")
public class Transaccion {

    @Id
    private String id;

    private String cliente;

    private Long id_cliente;

    private String tipo;

    private String fondo;

    private int id_fondo;

    private String fecha;

    private int monto;
}
