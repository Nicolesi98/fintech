package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Conta;
import br.com.fiap.fintech_backend.model.Usuario;
import br.com.fiap.fintech_backend.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Conta> findAll(Long userId) {
        return usuarioService.findById(userId).map(Usuario::getContas).orElse(emptyList());
    }

    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }

    public Conta save(Long userId, Conta conta) {
        var usuarioOpt = usuarioService.findById(userId);
        var contaSalva = contaRepository.save(conta);
        usuarioOpt.ifPresent(usuarioEntity -> {
            usuarioEntity.getContas().add(contaSalva);
            usuarioService.save(usuarioEntity);
        });
        return contaSalva;
    }

    public Conta update(Conta conta){
        return contaRepository.save(conta);
    }

    public boolean deleteById(Long id) {
        if (contaRepository.existsById(id)) {
            contaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
