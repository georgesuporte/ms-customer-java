package br.customer.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.customer.api.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByCpf(String cpf);
}