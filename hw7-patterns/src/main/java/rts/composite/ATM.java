package rts.composite;

import lombok.extern.slf4j.Slf4j;
import rts.annotation.EventListener;
import rts.memento.Memento;
import rts.memento.State;
import rts.observer.ResetEvent;
import rts.visitor.ATMVisitor;

import java.math.BigDecimal;

@Slf4j
public class ATM implements DepartmentComponent {
    private int id;
    private State state;
    private Memento initialMemento;

    public ATM(int id, State state) {
        this.id = id;
        this.state = state;
        this.initialMemento = new Memento(state.copy());
    }

    @Override
    public BigDecimal getBalance() {
        return state.getBalance();
    }

    @Override
    public void reset() {
        this.state = initialMemento.getSavedState();
        log.info("ATM {}  восстановлен, баланс = {}", id, state.getBalance());
    }

    @EventListener
    public void onReset(ResetEvent event) {
        reset();
    }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}