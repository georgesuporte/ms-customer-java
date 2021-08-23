package br.customer.api.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.RollbackException;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.customer.api.entity.PetEntity;
import br.customer.api.repository.PetRepository;

@Service
@Transactional
public class PetService  {

    @Autowired
	private PetRepository repository;


	public void save(PetEntity petEntity) {
		repository.save(petEntity);

	}

	public Optional<PetEntity> findById(Long id) {
		return repository.findById(id);
	}

	public List<PetEntity> findAll() {
		return repository.findAll();
	}
    
	public void deleteById(Long id) {
		repository.deleteById(id);	
	}

	@Transactional(rollbackFor =ServiceException.class)
	public List<PetEntity> saveAll(Set<PetEntity> set) {  
		try {
			return repository.saveAll(set);
		} catch (Exception e) {
			throw new RollbackException("Erro ao cadastrar cliente. Exception:[" + e.getMessage() + "]"); 
		}
		
	}

	
    
}
