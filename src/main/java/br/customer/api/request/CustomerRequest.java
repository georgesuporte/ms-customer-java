package br.customer.api.request;

import java.util.Collection;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value="CustomerRequest", description="CustomerRequest")
@Getter
public class CustomerRequest {

  @ApiModelProperty(value = "Atributo responsável por armazenar o ID", example = "1")
  private Long id;

  @ApiModelProperty(value = "Atributo responsável por armazenar o RG", example = "0851282437")
	@Size(min = 1, message = "Tamanho do campo inválido/incorreto")
  @NotEmpty(message = "Campo obrigatório.")
  @Pattern(regexp = "[0-9\\s\\-]+", message = "Campo obrigatório.")
  private String rg;

  @ApiModelProperty(value = "Atributo responsável por armazenar o nome do cliente", example = "George Silva")
	@Size(min = 1, message = "Tamanho do campo inválido/incorreto")
	@NotEmpty(message = "Campo obrigatório.")
  private String customerName;

  @ApiModelProperty(value = "Atributo responsável por armazenaro o CPF do cliente", example = "12312312387")
	@Size(min = 1, message = "Tamanho do campo inválido/incorreto")
  @NotEmpty(message = "Campo obrigatório.")
  @Pattern(regexp = "[0-9]+", message = "Campo obrigatório.")
  private String document;

  @ApiModelProperty(value = "Atributo responsável por armazenar a Lista de Pets")
  private Collection<PetRequest> animal;
}
