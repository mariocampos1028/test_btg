package com.btg.test.controller;

import com.btg.test.domain.Cliente;
import com.btg.test.domain.Fondo;
import com.btg.test.domain.GenericResponse;
import com.btg.test.domain.Transaccion;
import com.btg.test.service.BtgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class BtgController {


    @Autowired
    private BtgService btgService;

    //--------------------APIS CLIENTES-----------------------------------

    @GetMapping("/obtenerClientes")
    public List<Cliente> obtenerClientes(){
        return btgService.getClienteList();
    }


    @PostMapping("/crearCliente")
    public Cliente crearCliente(@RequestBody  Cliente cliente){
        return btgService.addCliente(cliente);
    }


    @PutMapping("/actualizarCliente")
    public Cliente actualizarCliente(@RequestBody Cliente cliente){
        return btgService.updateCliente(cliente);
    }


    @DeleteMapping("/borrarCliente")
    public Cliente borrarCliente(@RequestBody Cliente cliente){
        return btgService.removeCliente(cliente);
    }


    //--------------------APIS FONDO---------------------------


    @GetMapping("/obtenerFondosExistentes")
    public List<Fondo> obtenerFondosExistentes(){
        return btgService.getFondosExistentes();
    }


    @PostMapping("/crearFondo")
    public Fondo crearFondo(@RequestBody Fondo fondo){
        return btgService.addFondo(fondo);
    }


    @DeleteMapping("/borrarFondo")
    public Fondo borrarFondo(@RequestBody Fondo fondo){
        return btgService.removeFondo(fondo);
    }

    //--------------------APIS TRANSACCIONES---------------------------


    @GetMapping("/obtenerTransacciones")
    public List<Transaccion> obtenerTransacciones(){
        return btgService.getTransacciones();
    }


    @PostMapping("/crearTransaccion")
    public GenericResponse crearTransaccion(@RequestBody Transaccion transaccion){
        return btgService.crearTransaccion(transaccion);
    }








}
