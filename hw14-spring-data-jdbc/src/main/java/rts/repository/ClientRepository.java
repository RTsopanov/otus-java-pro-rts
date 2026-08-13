package rts.repository;

import org.springframework.data.repository.CrudRepository;
import rts.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    @Override
    List<Client> findAll();
}