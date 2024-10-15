package com.btg.test.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse {

    private int status;

    private String message;

    private Object data;
}
