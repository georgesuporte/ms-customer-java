package br.customer.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PetPresenter {

  @ApiModelProperty(value = "Atributo responsável por armazenar o ID", example = "1")
  private Long id;

  @ApiModelProperty(value = "Atributo responsável por armazenar o nome do Pet", example = "Simba")
  private String nameAnimal;

}
