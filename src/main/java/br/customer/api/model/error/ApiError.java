package br.customer.api.model.error;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Error", description = "Possíveis Erros de validação:")
public class ApiError {

    @ApiModelProperty(value = "O timestamp em que o erro ocorreu.", required = true, example = "2020-05-04T13:21:57.757")
    private String timestamp;

    @ApiModelProperty(value = "O status HTTP.", required = true, example = "500")
    private int status;

    @ApiModelProperty(value = "A descrição do status HTTP.", required = true, example = "INTERNAL_SERVER_ERROR")
    private HttpStatus error;

    @ApiModelProperty(value = "Mensagem resumindo o erro.", required = true, example = "Dados não encontrado.")
    private String message;

    @ApiModelProperty(value = "Nome do Metodo.", example = "deleteCustomer")
    private String method;


    @ApiModelProperty(value = "Caminho / Url.", required = true, example = "/v1/customer")
    private String path;

    @ApiModelProperty(value = "Informação detalhada dos erros ocorridos.")
    private List<ErrorDetails> errorDetails;

    public ApiError() {
        super();
    }

    public ApiError(final HttpStatus badRequest, final String message, final List<ErrorDetails> errorDetails) {
        super();
            this.timestamp = LocalDateTime.now().toString();
            this.status = badRequest.value();
            this.error = badRequest;
            this.message = message;
            this.errorDetails = errorDetails;
    
    }

    public ApiError(final HttpStatus badRequest, final String message, final ErrorDetails error) {
        super();
        this.timestamp = LocalDateTime.now().toString();
        this.status = badRequest.value();
        this.error = badRequest;
        this.message = message;
        errorDetails = Arrays.asList(error);
    }

}