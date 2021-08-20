package br.customer.api.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value="ListCustomerPresenter", description="ListCustomerPresenter")
@Builder
@Getter
public class ResponseData {
    private List<CustomerPresenter> data;
}
