package com.gerenciador.pedidos.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverter {

    // Define o formato de saída desejado
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Tenta converter a data com o formato específico e retorna uma data no formato yyyy-MM-dd
    public static String convertDate(String dateStr) {
        String[] dateFormats = {
                "dd/MM/yyyy",
                "MM/dd/yyyy",
                "yyyy/MM/dd",
                "dd-MM-yyyy",
                "MM-dd-yyyy",
                "yyyy-MM-dd"
        };

        for (String format : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDate date = LocalDate.parse(dateStr, formatter);
                return date.format(OUTPUT_FORMATTER);
            } catch (DateTimeParseException e) {
                // Continua tentando com o próximo formato
            }
        }

        throw new IllegalArgumentException("Data inválida ou não suportada: " + dateStr);
    }

}
