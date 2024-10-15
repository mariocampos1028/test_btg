package com.btg.test.service.impl;

import com.btg.test.domain.*;
import com.btg.test.repository.ClienteRepository;
import com.btg.test.repository.FondoRepository;
import com.btg.test.repository.IncripcionFondoClienteRepository;
import com.btg.test.repository.TransaccionRepository;
import com.btg.test.service.BtgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BtgServiceImpl implements BtgService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FondoRepository fondoRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private IncripcionFondoClienteRepository incripcionFondoClienteRepository;

    @Override
    public List<Cliente> getClienteList() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente addCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente removeCliente(Cliente cliente) {
        Optional<Cliente> clientdeleted = clienteRepository.findById(cliente.getId());
        if(clientdeleted.isPresent()) {
            clienteRepository.delete(cliente);
            return clientdeleted.get();
        }else{
            return new Cliente();
        }
    }

    @Override
    public Cliente updateCliente(Cliente cliente) {
        Cliente clienteUpdated = clienteRepository.findById(cliente.getId()).get();
        clienteUpdated.setNombre(cliente.getNombre());
        clienteUpdated.setEmail(cliente.getEmail());
        clienteUpdated.setSaldo(cliente.getSaldo());
        clienteUpdated.setTelefono(cliente.getTelefono());
        clienteRepository.save(clienteUpdated);
        return clienteUpdated;
    }

    @Override
    public List<Fondo> getFondosExistentes() {
        return fondoRepository.findAll();
    }

    @Override
    public Fondo addFondo(Fondo fondo) {
        return fondoRepository.save(fondo);
    }

    @Override
    public Fondo removeFondo(Fondo fondo) {
        Optional<Fondo> fondoFind = fondoRepository.findById(fondo.getId());
        if(fondoFind.isPresent()) {
            fondoRepository.delete(fondo);
            return fondoFind.get();
        }else{
            return new Fondo();
        }
    }

    @Override
    public List<Transaccion> getTransacciones() {
        return transaccionRepository.findAll();
    }

    @Override
    public GenericResponse crearTransaccion(Transaccion transaccion) {
        GenericResponse gr = new GenericResponse();
        String valid = isValidTransaccion(transaccion.getId_fondo(),transaccion.getId_cliente(), transaccion.getTipo());
        if(!valid.equals("OK")){
            gr.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            gr.setMessage(valid);
            return gr;
        }
        if(transaccion.getTipo().equals("Apertura")){
            //** Primero descontar saldo de cliente
            //buscamos el cliente por id:
            Optional<Cliente> clienteFind = clienteRepository.findById(transaccion.getId_cliente());
            if(clienteFind.isPresent()){
                Cliente cliente = clienteFind.get();
                //buscamos el monto minimo del fondo
                Optional<Fondo> fondoFind = fondoRepository.findById(transaccion.getId_fondo());
                if(fondoFind.isPresent()){
                    Fondo fondo = fondoFind.get();
                    if(transaccion.getMonto() < fondo.getMonto_minimo()){
                        gr.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
                        gr.setMessage("Monto de apertura no es suficiente, monto minimo: "+fondo.getMonto_minimo());
                    }else if(transaccion.getMonto() > cliente.getSaldo()){
                        gr.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
                        gr.setMessage("No tiene saldo disponible para vincularse al fondo "+transaccion.getFondo());
                    }else{
                        //actualizamos saldo
                        cliente.setSaldo(cliente.getSaldo()-transaccion.getMonto());
                        clienteRepository.save(cliente);
                        //asignamos incripcion al fondo
                        IncripcionesFondoCliente ifc = new IncripcionesFondoCliente();
                        ifc.setId_fondo(transaccion.getId_fondo());
                        ifc.setId_cliente(transaccion.getId_cliente());
                        incripcionFondoClienteRepository.save(ifc);
                        //creamos la transaccion
                        gr.setStatus(HttpStatus.OK.value());
                        gr.setMessage("Apertura realizada con éxito");
                        gr.setData(transaccionRepository.save(transaccion));
                    }
                }else {
                    gr.setStatus(HttpStatus.NOT_FOUND.value());
                    gr.setMessage("fondo no encontrado");
                }
            }else{
                gr.setStatus(HttpStatus.NOT_FOUND.value());
                gr.setMessage("cliente no encontrado");
            }
        }else{
            Optional<Cliente> clienteFind = clienteRepository.findById(transaccion.getId_cliente());
            if(clienteFind.isPresent()){
                Cliente cliente = clienteFind.get();
                //buscamos el monto minimo del fondo
                Optional<Fondo> fondoFind = fondoRepository.findById(transaccion.getId_fondo());
                if(fondoFind.isPresent()){
                    cliente.setSaldo(cliente.getSaldo()+transaccion.getMonto());
                    clienteRepository.save(cliente);
                    //asignamos incripcion al fondo
                    IncripcionesFondoCliente ifc = new IncripcionesFondoCliente();
                    ifc.setId_fondo(transaccion.getId_fondo());
                    ifc.setId_cliente(transaccion.getId_cliente());
                    incripcionFondoClienteRepository.delete(ifc);
                    //creamos la transaccion
                    gr.setStatus(HttpStatus.OK.value());
                    gr.setMessage("Cancelacion realizada con éxito");
                    gr.setData(transaccionRepository.save(transaccion));
                }else {
                    gr.setStatus(HttpStatus.NOT_FOUND.value());
                    gr.setMessage("fondo no encontrado");
                }
            }else{
                gr.setStatus(HttpStatus.NOT_FOUND.value());
                gr.setMessage("cliente no encontrado");
            }

        }

        //retorno del generic response
        return gr;
    }

    private String isValidTransaccion(int id_fondo, Long id_cliente, String tipo) {
        Optional<IncripcionesFondoCliente> ifc = incripcionFondoClienteRepository.findByClienteAndFondo(id_fondo,id_cliente);
        if(tipo.equals("Apertura")){
            if(ifc.isPresent()){
                return "El cliente ya se encuentra aperturado en el fondo";
            }else{
                return "OK";
            }
        }else{
            if(ifc.isPresent()){
                return "OK";
            }else{
                return "El cliente no se encuentra aperturado en el fondo";
            }
        }
    }


}
