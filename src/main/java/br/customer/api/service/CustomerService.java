package br.customer.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.customer.api.entity.CustomerEntity;
import br.customer.api.entity.PetEntity;
import br.customer.api.repository.CustomerRepository;

@Transactional
@Service
public class CustomerService  {

    @Autowired
	private CustomerRepository repository;

	@Autowired
	private PetService petService;
	

	@Transactional(rollbackFor = { TransactionException.class }, propagation = Propagation.REQUIRED)
	public void save(CustomerEntity customerEntity) {
		CustomerEntity client = repository.save(customerEntity);

		List<PetEntity> animal = new ArrayList<PetEntity>();
		client.getAnimal().forEach(x -> animal.add(PetEntity.builder().id(x.getId()).name(x.getName()).customer(findById(client.getId()).get()).build()));
		client.setAnimal(animal);
		petService.saveAll(client.getAnimal());
	}

	public Optional<CustomerEntity> findById(Long id) {
		return repository.findById(id);
	}

	public List<CustomerEntity> findAll() {
		return repository.findAll();
	}
    
	public void deleteById(Long id) {
		repository.deleteById(id);	
	}

	public Optional<CustomerEntity> findByCpf(String cpf) {
		return repository.findByCpf(cpf);	
	}


	
    
}
