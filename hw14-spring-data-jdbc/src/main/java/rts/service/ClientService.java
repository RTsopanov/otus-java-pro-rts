package rts.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rts.cachehw.HwCache;
import rts.dto.ClientDto;
import rts.exception.ClientNotFoundException;
import rts.model.Client;
import rts.repository.ClientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final HwCache<Long, ClientDto> cache;

    @Transactional
    public ClientDto save(Client client) {
        Client saved = clientRepository.save(client);
        ClientDto dto = ClientDto.from(saved);
        cache.put(dto.getId(), dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public ClientDto findById(Long id) {
        ClientDto cached = cache.get(id);
        if (cached != null) {
            log.info("Client id={} взят из кэша", id);
            return cached;
        }
        log.info("Client id={} идёт в БД", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found: id=" + id));

        ClientDto dto = ClientDto.from(client);
        cache.put(id, dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public Client findEntityById(Long id) {

        return clientRepository.findById(id)
                .orElseThrow(
                        () -> new ClientNotFoundException(
                                "Client not found: id=" + id
                        )
                );
    }

    @Transactional(readOnly = true)
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::from)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
        cache.remove(id);
    }
}