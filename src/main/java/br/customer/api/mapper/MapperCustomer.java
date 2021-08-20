package br.customer.api.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.customer.api.entity.CustomerEntity;
import br.customer.api.entity.PetEntity;
import br.customer.api.request.CustomerCreateRequest;
import br.customer.api.request.CustomerRequest;
import br.customer.api.response.CustomerPresenter;
import br.customer.api.response.PetPresenter;

public class MapperCustomer {

    private List<CustomerPresenter> listCustomer = new ArrayList<>();

    private List<PetEntity> animal = new ArrayList<PetEntity>();

    private List<PetPresenter> listAnimal = new ArrayList<PetPresenter>();

    public CustomerEntity mapperCustomerRequestToEntity(CustomerRequest customerRequest) {
        
        customerRequest.getAnimal().forEach(x -> animal.add(PetEntity.builder().id(x.getId()).name(x.getName()).build()));

        customerRequest.getAnimal();
        return CustomerEntity.builder().id(customerRequest.getId())
        .name(customerRequest.getCustomerName())
        .rg(customerRequest.getRg())
        .cpf(customerRequest.getDocument())
        .animal(animal).build();
        
    }

    public CustomerEntity mapperCustomerCreateRequestToEntity(CustomerCreateRequest customerCreateRequest) {

        customerCreateRequest.getAnimal().forEach(x -> animal.add(PetEntity.builder().name(x.getName()).build()));

        return CustomerEntity.builder()
        .name(customerCreateRequest.getCustomerName())
        .rg(customerCreateRequest.getRg())
        .cpf(customerCreateRequest.getDocument())
        .animal(animal).build();
        
    }

    public List<CustomerPresenter> mapperCustomerEntityToCustomerPresenter(List<CustomerEntity> listCustomerEntity) {
        
        listCustomerEntity.forEach(x -> listCustomer.add(mapperCustomerEntity(x)));
        return listCustomer;
    }

    public List<CustomerPresenter> mapperCustomerEntityToListCustomerPresenter(Optional<CustomerEntity> customerEntity) {
        listCustomer.add(mapperCustomerEntity(customerEntity.get()));
        return listCustomer;
    }


    private CustomerPresenter mapperCustomerEntity(CustomerEntity customerEntity) {

        customerEntity.getAnimal().forEach(x -> listAnimal.add(PetPresenter.builder().id(x.getId()).nameAnimal(x.getName()).build()));
        
        return CustomerPresenter.builder().id(customerEntity.getId())
        .customerName(customerEntity.getName())
        .rg(customerEntity.getRg())
        .document(customerEntity.getCpf())
        .listAnimal(listAnimal).build();
    }
}
