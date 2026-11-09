package com.biblioteca.domain.port.out;

import java.time.LocalDate;

/** Abstrae el reloj para que el dominio sea determinista y fácil de probar. */
public interface ClockPort {
    LocalDate today();
}
