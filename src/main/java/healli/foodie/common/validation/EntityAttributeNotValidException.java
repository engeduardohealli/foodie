package healli.foodie.common.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EntityAttributeNotValidException extends RuntimeException {

    private List<EntityAttributeError> errors;

    public EntityAttributeNotValidException(List<EntityAttributeError> errors) {

        this.errors = errors;
    }

    public EntityAttributeNotValidException(EntityAttributeError error) {

        this.errors = new ArrayList<>();
        this.errors.add(error);
    }
}
