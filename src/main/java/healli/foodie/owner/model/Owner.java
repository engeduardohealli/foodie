package healli.foodie.owner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("owners")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Owner {

    @Id
    private String id;
}
