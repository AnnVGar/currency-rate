package ru.ann.algorithm;

import org.junit.Test;
import ru.ann.domain.CurrencyData;
import ru.ann.domain.CurrencyName;
import ru.ann.respository.CurrencyDataRespository;
import ru.ann.service.CurrencyImportService;
import ru.ann.util.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class AverageRateTest {
    List<CurrencyData> list;

    public AverageRateTest() {
        CurrencyImportService.importCurrencyMap();
        list = CurrencyDataRespository.getListByCurrencyName(CurrencyName.EUR);
    }

    @Test
    public void getNextValue() {
        AverageRate averageRate = new AverageRate();
        BigDecimal actual = averageRate.getNextValue(list, LocalDate.parse("12.04.2023", DateUtils.UPLOAD_FORMATTER));
        BigDecimal expected = new BigDecimal("83.1379");
        assertEquals(actual, expected);
    }
}