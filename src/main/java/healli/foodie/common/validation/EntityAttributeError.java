package healli.foodie.common.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EntityAttributeError {

    private String entityName;
    private String attribute;
    private Object rejectedValue;
    private String message;
}
