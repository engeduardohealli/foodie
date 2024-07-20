package healli.foodie.category.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategorySaleStatusUpdatedEvent {
    private String id;
}
