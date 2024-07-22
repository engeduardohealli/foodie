package healli.foodie.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSaleStatusRequest {

    private String id;
    @NotNull(message = "Sale status cannot be null.")
    private Boolean isSaleActive;
}
