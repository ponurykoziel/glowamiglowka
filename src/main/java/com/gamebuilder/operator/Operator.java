package com.gamebuilder.operator;

import com.gamebuilder.domain.Entity;
import com.gamebuilder.domain.Component;

import java.util.List;

public interface Operator extends Entity {
    int getOperandCount();
    List<List<Component>> getOperands();
    OperatorContract getContract();
}
