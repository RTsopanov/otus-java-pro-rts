package rts.memento;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class State {
    private BigDecimal balance;

    public State copy() {
        return new State(this.balance);
    }
}