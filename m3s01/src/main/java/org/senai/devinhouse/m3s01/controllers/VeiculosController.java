package org.senai.devinhouse.m3s01.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.senai.devinhouse.m3s01.dtos.VeiculoRequest;
import org.senai.devinhouse.m3s01.dtos.VeiculoResponse;
import org.senai.devinhouse.m3s01.models.Veiculo;
import org.senai.devinhouse.m3s01.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@Slf4j
public class VeiculosController {

    @Autowired
    private VeiculoService service;

    private ModelMapper modelMapper = new ModelMapper(); // criando instancia pq só será usado aquo

    @PostMapping
    public ResponseEntity<VeiculoResponse> adicionar(@RequestBody @Valid VeiculoRequest request) {
        log.debug("Dados da request: {}", request);
        Veiculo veiculo = modelMapper.map(request, Veiculo.class);
        veiculo = service.inserir(veiculo);
        log.info("Placa {} cadastrada com sucesso!", veiculo.getPlaca());
        var resp = modelMapper.map(veiculo, VeiculoResponse.class);
        return ResponseEntity.created(URI.create(veiculo.getPlaca())).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> consultar() {
        List<Veiculo> veiculos = service.listar();
        log.info("Consulta retornando {} veiculos", veiculos.size());
        List<VeiculoResponse> veiculosResp = veiculos.stream()
                .map(v -> modelMapper.map(v, VeiculoResponse.class)).toList();
        return ResponseEntity.ok(veiculosResp);
    }

    @GetMapping("{placa}")
    public ResponseEntity<VeiculoResponse> consultar(@PathVariable String placa) {
        Veiculo veiculo = service.obter(placa);
        VeiculoResponse resp = modelMapper.map(veiculo, VeiculoResponse.class);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("{placa}")
    public ResponseEntity excluir(@PathVariable String placa) {
        service.excluir(placa);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{placa}/multas")
    public ResponseEntity multar(@PathVariable String placa) {
        var veiculo = service.adicionarMulta(placa);
        var resp = modelMapper.map(veiculo, VeiculoResponse.class);
        return ResponseEntity.ok(resp);
    }

}
