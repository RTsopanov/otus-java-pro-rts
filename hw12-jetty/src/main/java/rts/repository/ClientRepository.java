package rts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rts.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}