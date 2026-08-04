package rts.visitor;

import lombok.Data;
import rts.composite.ATM;

import java.math.BigDecimal;

@Data
public class BalanceSumVisitor implements ATMVisitor {
    private BigDecimal total = BigDecimal.valueOf(0);

    @Override
    public void visit(ATM atm) {
        total = total.add(atm.getBalance());
    }
}