package healli.foodie.category.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Category {

    @Id
    private String id;
    private String title;
    private String description;
    private Boolean isSaleActive;
    private String ownerId;
}
