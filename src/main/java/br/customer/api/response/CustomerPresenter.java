package br.customer.api.response;

import java.util.Collection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerPresenter {

  @ApiModelProperty(value = "Atributo responsável por armazenar o ID", example = "1")
  private Long id;

  @ApiModelProperty(value = "Atributo responsável por armazenar o RG", example = "0851282437")
  private String rg;
  
  @ApiModelProperty(value = "Atributo responsável por armazenar o nome do cliente", example = "George Silva")
  private String customerName;

  @ApiModelProperty(value = "Atributo responsável por armazenaro o CPF do cliente", example = "12312312387")
  private String document;
  
  
  private Collection<PetPresenter> listAnimal;
}
