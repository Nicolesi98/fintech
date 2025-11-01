package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Login;
import br.com.fiap.fintech_backend.model.Usuario;
import br.com.fiap.fintech_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.fiap.fintech_backend.model.MessageError.SENHA_INCORRETA;
import static br.com.fiap.fintech_backend.model.MessageError.USUARIO_NAO_ENCONTRADO;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario login(Login login) {
        return usuarioRepository.findByEmail(login.getEmail())
                .map(usuarioEntity -> {
                    if(usuarioEntity.getSenha().equals(login.getSenha())){
                        return usuarioEntity;
                    }else{
                        throw new RuntimeException(SENHA_INCORRETA);
                    }
                })
                .orElseThrow(() -> new RuntimeException(USUARIO_NAO_ENCONTRADO));
    }

    public boolean deleteById(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
