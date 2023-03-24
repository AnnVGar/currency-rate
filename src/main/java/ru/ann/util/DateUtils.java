package ru.ann.util;

import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final DateTimeFormatter UPLOAD_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter TO_STRING_FORMATTER = DateTimeFormatter.ofPattern("eee dd.MM.yyyy");

    private DateUtils() {

    }

}
