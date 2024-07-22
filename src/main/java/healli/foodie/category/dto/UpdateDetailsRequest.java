package healli.foodie.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDetailsRequest {

    private String id;
    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name is mandatory.")
    private String title;
    @NotNull(message = "Description cannot be null.")
    @NotBlank(message = "Description is mandatory.")
    private String description;;
    @NotNull(message = "Owner id cannot be null.")
    @NotBlank(message = "Owner id is mandatory.")
    private String ownerId;
}
