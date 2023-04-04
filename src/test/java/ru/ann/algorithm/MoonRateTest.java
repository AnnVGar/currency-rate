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

import static org.junit.Assert.assertEquals;
import static ru.ann.algorithm.MoonRate.getNearestDataByDate;

public class MoonRateTest {

    List<CurrencyData> list;

    public MoonRateTest() {
        CurrencyImportService.importCurrencyMap();
        list = CurrencyDataRespository.getListByCurrencyName(CurrencyName.EUR);
    }

    @Test
    public void getNextValueTest() {
        MoonRate moonRate = new MoonRate();
        BigDecimal actual = moonRate.getNextValue(list,LocalDate.parse("12.04.2023", DateUtils.UPLOAD_FORMATTER));
        BigDecimal expected = new BigDecimal("85.9752");
        assertEquals(actual, expected);
    }

    @Test
    public void getNearestDataByDateForExistDate() {
        BigDecimal actual = getNearestDataByDate(LocalDate.parse("01.03.2023", DateUtils.UPLOAD_FORMATTER), list).getValue();
        BigDecimal expected = new BigDecimal("79.5134");
        assertEquals(actual, expected);
    }

    @Test
    public void getNearestDataByDateForMissDate() {
        BigDecimal actual = getNearestDataByDate(LocalDate.parse("25.02.2023", DateUtils.UPLOAD_FORMATTER), list).getValue();
        BigDecimal expected = new BigDecimal("79.5716");
        assertEquals(actual, expected);
    }
}