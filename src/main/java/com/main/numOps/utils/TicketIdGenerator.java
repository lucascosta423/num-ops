package com.main.numOps.utils;


import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

public class TicketIdGenerator {

    private static final int SIZE = 8;

    public static String gerar(String prefixo) {
        return prefixo + "-" + NanoIdUtils.randomNanoId(
                NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                NanoIdUtils.DEFAULT_ALPHABET,
                SIZE
        );
    }
}
