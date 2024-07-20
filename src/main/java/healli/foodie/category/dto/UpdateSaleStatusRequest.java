package healli.foodie.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSaleStatusRequest {

    @NotNull(message = "Id cannot be null.")
    @NotBlank(message = "Id is mandatory.")
    private String id;
    @NotNull(message = "Sale status cannot be null.")
    private Boolean isSaleActive;
}
