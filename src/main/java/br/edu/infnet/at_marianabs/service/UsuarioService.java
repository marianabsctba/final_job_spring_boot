package br.edu.infnet.at_marianabs.service;

import br.edu.infnet.at_marianabs.execption.ResourceNotFoundException;
import br.edu.infnet.at_marianabs.model.Usuario;
import br.edu.infnet.at_marianabs.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(String id, Usuario usuarioAtualizado) {
        if(usuarioRepository.existsById(id)){
            usuarioAtualizado.setId(id);
            return usuarioRepository.save(usuarioAtualizado);
        }
        throw new ResourceNotFoundException("Usuario não encontrado com id: " + id);
    }

    public void remover(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario não encontrado com id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
