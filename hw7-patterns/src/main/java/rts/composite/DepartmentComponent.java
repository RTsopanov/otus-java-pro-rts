package rts.composite;

import rts.visitor.ATMVisitor;

import java.math.BigDecimal;

public interface DepartmentComponent {
    BigDecimal getBalance();

    void reset();

    void accept(ATMVisitor visitor);
}