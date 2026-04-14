package com.main.numOps.dtos.Number;

import java.util.Set;

public record RequestReserveNumberDTO (
        Integer provedor,
        Set<Integer> idsNumeros
){
}
