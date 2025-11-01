package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Conta;
import br.com.fiap.fintech_backend.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }

    public Conta save(Conta conta) {
        return contaRepository.save(conta);
    }

    public void deleteById(Long id) {
        contaRepository.deleteById(id);
    }
}
