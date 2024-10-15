package com.btg.test.service;

import com.btg.test.domain.Cliente;
import com.btg.test.domain.Fondo;
import com.btg.test.domain.GenericResponse;
import com.btg.test.domain.Transaccion;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BtgService {

    public List<Cliente> getClienteList();

    public Cliente addCliente(Cliente cliente);

    public Cliente removeCliente(Cliente cliente);

    public Cliente updateCliente(Cliente cliente);

    public List<Fondo> getFondosExistentes();

    public Fondo addFondo(Fondo fondo);

    public Fondo removeFondo(Fondo fondo);

    public List<Transaccion> getTransacciones();

    public GenericResponse crearTransaccion(Transaccion transaccion);
}
