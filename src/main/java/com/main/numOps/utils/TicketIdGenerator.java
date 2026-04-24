package com.main.numOps.utils;


import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketIdGenerator {

    private static final int SIZE = 8; // aumentei pra reduzir colisão

    private static final char[] ALPHABET =
            "ABCDEFGHJKLMNPQRSTUVWXYZ23456789".toCharArray();

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HHmmss");

    public static String gerar(String prefixo) {

        String hora = LocalDateTime.now().format(FORMATTER);

        String random = NanoIdUtils.randomNanoId(
                NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                ALPHABET,
                SIZE
        );

        return prefixo + "-" + random + "-"  + hora;
    }
}
