package ru.ann.currencyrate.common;

import java.io.File;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

public class CurrencyConstant {
    public static final DateTimeFormatter UPLOAD_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter TO_STRING_FORMATTER = DateTimeFormatter.ofPattern("eee dd.MM.yyyy");
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    public static int SCALE = 2;
    public static final int NOMINAL_INDEX = 0;
    public static final int DATE_INDEX = 1;
    public static final int CURS_INDEX = 2;
    public static final int NAME_INDEX = 3;
    public static final String SEPARATOR = File.separator;
    public static final String EXTENSION = ".csv";
    public static final String SOURCE_PATH = SEPARATOR + "src" + SEPARATOR + "main" + SEPARATOR + "resources" + SEPARATOR+"csv"+SEPARATOR;


}
