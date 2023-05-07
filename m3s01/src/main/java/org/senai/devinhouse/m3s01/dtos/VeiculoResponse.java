package org.senai.devinhouse.m3s01.dtos;

import lombok.Data;

@Data
public class VeiculoResponse {

    private String placa;

    private String tipo;

    private String cor;

    private Integer anoDeFabricacao;

    private Integer qtdMultas;

}
