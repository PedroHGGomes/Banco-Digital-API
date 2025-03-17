package br.com.fiap.banco_digital.controller;

import br.com.fiap.banco_digital.model.Conta;
import br.com.fiap.banco_digital.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> cadastrarConta(@RequestBody @Valid Conta conta) {
        Conta novaConta = contaService.cadastrarConta(conta);
        return ResponseEntity.ok(novaConta);
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listarTodas() {
        return ResponseEntity.ok(contaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id) {
        return contaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Conta> buscarPorCpf(@PathVariable String cpf) {
        return contaService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/encerrar/{id}")
    public ResponseEntity<Void> encerrarConta(@PathVariable Long id) {
        contaService.encerrarConta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/deposito/{id}")
    public ResponseEntity<Void> depositar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        contaService.depositar(id, valor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/saque/{id}")
    public ResponseEntity<Void> sacar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        contaService.sacar(id, valor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/pix")
    public ResponseEntity<Void> realizarPix(@RequestParam Long idOrigem, @RequestParam Long idDestino, @RequestParam BigDecimal valor) {
        contaService.realizarPix(idOrigem, idDestino, valor);
        return ResponseEntity.ok().build();
    }
}

