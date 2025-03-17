package br.com.fiap.banco_digital.repository;

import br.com.fiap.banco_digital.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByCpfTitular(String cpfTitular);
}

