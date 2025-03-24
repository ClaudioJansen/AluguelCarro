package carlocate.application;

import carlocate.ClienteRepository;
import carlocate.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente getCliente(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElse(null);
    }

    public Cliente updateCliente(Long id, Cliente clienteDetails) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    Optional.ofNullable(clienteDetails.getNome()).ifPresent(cliente::setNome);
                    Optional.ofNullable(clienteDetails.getCpf()).ifPresent(cliente::setCpf);
                    Optional.ofNullable(clienteDetails.getRg()).ifPresent(cliente::setRg);
                    Optional.ofNullable(clienteDetails.getEndereco()).ifPresent(cliente::setEndereco);
                    Optional.ofNullable(clienteDetails.getProfissao()).ifPresent(cliente::setProfissao);
                    return clienteRepository.save(cliente);
                })
                .orElse(null);
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
