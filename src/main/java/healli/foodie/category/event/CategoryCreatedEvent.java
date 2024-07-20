package healli.foodie.category.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryCreatedEvent {
    private String id;
}
