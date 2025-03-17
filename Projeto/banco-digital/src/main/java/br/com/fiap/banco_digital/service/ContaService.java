package br.com.fiap.banco_digital.service;

import br.com.fiap.banco_digital.model.Conta;
import br.com.fiap.banco_digital.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta cadastrarConta(Conta conta) {
        conta.setDataAbertura(java.time.LocalDate.now());
        conta.setAtiva(true);
        return contaRepository.save(conta);
    }

    public List<Conta> listarTodas() {
        return contaRepository.findAll();
    }

    public Optional<Conta> buscarPorId(Long id) {
        return contaRepository.findById(id);
    }

    public Optional<Conta> buscarPorCpf(String cpfTitular) {
        return contaRepository.findByCpfTitular(cpfTitular);
    }

    @Transactional
    public void encerrarConta(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setAtiva(false);
        contaRepository.save(conta);
    }

    @Transactional
    public void depositar(Long id, BigDecimal valor) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);
    }

    @Transactional
    public void sacar(Long id, BigDecimal valor) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);
    }

    @Transactional
    public void realizarPix(Long idOrigem, Long idDestino, BigDecimal valor) {
        sacar(idOrigem, valor);
        depositar(idDestino, valor);
    }
}

