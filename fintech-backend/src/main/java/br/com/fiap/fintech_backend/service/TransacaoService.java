package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Transacao;
import br.com.fiap.fintech_backend.model.Usuario;
import br.com.fiap.fintech_backend.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Transacao> findAll(Long userId) {
        return usuarioService.findById(userId).map(Usuario::getContas)
            .orElse(emptyList())
            .stream()
            .flatMap(c -> transacaoRepository.findByConta(c).stream())
            .toList();
    }

    public Optional<Transacao> findById(Long id) {
        return transacaoRepository.findById(id);
    }

    public Transacao save(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public boolean deleteById(Long id) {
        if (transacaoRepository.existsById(id)) {
            transacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
