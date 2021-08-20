package br.customer.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.customer.api.entity.PetEntity;

public interface PetRepository extends JpaRepository<PetEntity, Long> {
 
}