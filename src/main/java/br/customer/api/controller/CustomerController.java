package br.customer.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.customer.api.exceptions.BadRequestException;
import br.customer.api.mapper.MapperCustomer;
import br.customer.api.model.error.ApiError;
import br.customer.api.request.CustomerCreateRequest;
import br.customer.api.request.CustomerRequest;
import br.customer.api.response.ResponseData;
import br.customer.api.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/v1/customer")
@Api(tags = {"Customer"}, description = "Cadastro cliente")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @ApiOperation(httpMethod = "GET", value = "Listar Clientes Cadastrados", response = ResponseData.class, responseContainer = "List")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class),
        @ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseData> listCustomer(@RequestHeader HttpHeaders headers) {	
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseData.builder().data(new MapperCustomer().mapperCustomerEntityToCustomerPresenter(service.findAll())).build()
        );
    }


    @ApiOperation(httpMethod = "GET", value = "Consultar Cadastro", response = ResponseData.class, responseContainer = "List")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class),
        @ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping(value = "/{document}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseData> findZipCode(@RequestHeader HttpHeaders headers, @PathVariable(value = "document") String document) {	
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                ResponseData.builder().data(new MapperCustomer().mapperCustomerEntityToListCustomerPresenter(service.findByCpf(document))).build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


    @ApiOperation(httpMethod = "POST", value = "Cadastrar Cliente")
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class),
        @ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> saveCustomer(@RequestHeader HttpHeaders headers, 
    @Valid  @RequestBody CustomerCreateRequest customerCreateRequest) {	
        try {
            if(service.findByCpf(customerCreateRequest.getDocument()).isEmpty()) {
                service.save(new MapperCustomer().mapperCustomerCreateRequestToEntity(customerCreateRequest));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível cadastrar o cliente.");
        }
    }

    @ApiOperation(httpMethod = "Update", value = "Atualizar cadastro")
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class),
        @ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateCustomer(@RequestHeader HttpHeaders headers, 
    @RequestBody CustomerRequest customerRequest) {	
        try {
            if(!service.findById(customerRequest.getId()).isEmpty()) {
                service.save(new MapperCustomer().mapperCustomerRequestToEntity(customerRequest));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível atualizar dados do Cliente.");
        }
    }

    @ApiOperation(httpMethod = "Delete", value = "Deletar cadastro")
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class),
        @ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteCustomer(@RequestHeader HttpHeaders headers, 
    @RequestBody CustomerRequest customerRequest) {	
        try {
            if(!service.findById(customerRequest.getId()).isEmpty()) {
                service.deleteById(customerRequest.getId());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            throw new BadRequestException("Erro ao deletar. Cliente informado inválido.");
        }
    }
}
