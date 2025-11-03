package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Meta;
import br.com.fiap.fintech_backend.model.Usuario;
import br.com.fiap.fintech_backend.repository.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class MetaService {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private UsuarioService usuarioService;


    public List<Meta> findAll(Long userId) {
        return usuarioService.findById(userId).map(Usuario::getMetas).orElse(emptyList());
    }

    public Optional<Meta> findById(Long id) {
        return metaRepository.findById(id);
    }

    public Meta save(Long userId, Meta meta) {
        var usuarioOpt = usuarioService.findById(userId);
        var metaSalva = metaRepository.save(meta);
        usuarioOpt.ifPresent(usuarioEntity -> {
            usuarioEntity.getMetas().add(metaSalva);
            usuarioService.save(usuarioEntity);
        });
        return metaSalva;
    }

    public Meta update(Meta meta) {
        return metaRepository.save(meta);
    }

    public boolean deleteById(Long id) {
        if (metaRepository.existsById(id)) {
            metaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
