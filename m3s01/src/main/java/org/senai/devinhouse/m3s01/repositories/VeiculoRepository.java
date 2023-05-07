package org.senai.devinhouse.m3s01.repositories;

import org.senai.devinhouse.m3s01.models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, String> {
}