package rts.composite;

import lombok.extern.slf4j.Slf4j;
import rts.observer.EventPublisher;
import rts.observer.ResetEvent;
import rts.visitor.ATMVisitor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Department implements DepartmentComponent {
    List<DepartmentComponent> departmentComponentList = new ArrayList<>();
    private final EventPublisher eventPublisher = new EventPublisher();

    public void add(DepartmentComponent component) {
        departmentComponentList.add(component);
        eventPublisher.subscribe(component);
    }

    @Override
    public BigDecimal getBalance() {
        return departmentComponentList.stream()
                .map(DepartmentComponent::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void reset() {
        log.info("Событие reset инициировано");
        eventPublisher.publish(new ResetEvent());
    }

    @Override
    public void accept(ATMVisitor visitor) {
        departmentComponentList.forEach(c -> c.accept(visitor));
    }

}