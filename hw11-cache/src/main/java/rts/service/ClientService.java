package rts.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rts.cachehw.HwCache;
import rts.cachehw.MyCache;
import rts.model.Client;
import rts.repository.ClientRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final HwCache<Long, Client> cache = new MyCache<>();

    @Transactional
    public Client save(Client client) {
        Client saved = clientRepository.save(client);
        cache.put(saved.getId(), saved);
        return saved;
    }

    @Transactional(readOnly = true)
    public Client findById(Long id) {
        Client cached = cache.get(id);
        if (cached != null) {
            log.info("Client id={} взят из кэша", id);
            return cached;
        }
        log.info("Client id={} идёт в БД", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found: id=" + id));
        cache.put(id, client);
        return client;
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
        cache.remove(id);
    }
}