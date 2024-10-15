package com.btg.test.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "incripcionesFondoCliente")
public class IncripcionesFondoCliente {

    @Id
    private int id_fondo;

    private Long id_cliente;
}
