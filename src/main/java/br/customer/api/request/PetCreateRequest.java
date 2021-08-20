package br.customer.api.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PetCreateRequest {

    @ApiModelProperty(value = "Atributo responsável por armazenar o nome do pet", example = "Simba")
	@Size(min = 1, message = "Tamanho do campo inválido/incorreto")
	@NotEmpty(message = "Campo obrigatório.")
    private String name;
}
