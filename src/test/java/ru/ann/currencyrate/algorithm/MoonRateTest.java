package ru.ann.currencyrate.algorithm;

import org.junit.Test;
import ru.ann.currencyrate.domain.CurrencyData;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.repository.CurrencyDataRepository;
import ru.ann.currencyrate.service.CurrencyImportService;
import ru.ann.currencyrate.service.algorithm.MoonRate;
import ru.ann.currencyrate.common.CurrencyConstant;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.ann.currencyrate.service.algorithm.MoonRate.getNearestDataByDate;

public class MoonRateTest {

    List<CurrencyData> list;

    public MoonRateTest() {
        CurrencyImportService.importCurrencyMap();
        list = CurrencyDataRepository.getListByCurrencyName(CurrencyName.EUR);
    }

    @Test
    public void getNextValueTest() {
        MoonRate moonRate = new MoonRate();
        BigDecimal actual = moonRate.getNextValue(list,LocalDate.parse("12.04.2023", CurrencyConstant.UPLOAD_FORMATTER));
        BigDecimal expected = new BigDecimal("85.9752");
        assertEquals(actual, expected);
    }

    @Test
    public void getNearestDataByDateForExistDate() {
        BigDecimal actual = getNearestDataByDate(LocalDate.parse("01.03.2023", CurrencyConstant.UPLOAD_FORMATTER), list).getUnitCurs();
        BigDecimal expected = new BigDecimal("79.5134");
        assertEquals(actual, expected);
    }

    @Test
    public void getNearestDataByDateForMissDate() {
        BigDecimal actual = getNearestDataByDate(LocalDate.parse("25.02.2023", CurrencyConstant.UPLOAD_FORMATTER), list).getUnitCurs();
        BigDecimal expected = new BigDecimal("79.5716");
        assertEquals(actual, expected);
    }
}