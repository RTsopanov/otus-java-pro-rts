package rts.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rts.model.Client;
import rts.repository.ClientRepository;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found: id=" + id));
    }
}