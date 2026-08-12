package rts.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class Phone {
    @Id
    private Long id;

    private String number;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }
}