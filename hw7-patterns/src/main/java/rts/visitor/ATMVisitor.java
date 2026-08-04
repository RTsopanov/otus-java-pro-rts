package rts.visitor;

import rts.composite.ATM;

public interface ATMVisitor {
    void visit(ATM atm);
}