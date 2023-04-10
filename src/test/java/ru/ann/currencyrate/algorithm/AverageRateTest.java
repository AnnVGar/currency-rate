package ru.ann.currencyrate.algorithm;

import org.junit.Test;
import ru.ann.currencyrate.domain.CurrencyData;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.repository.CurrencyDataRepository;
import ru.ann.currencyrate.service.CurrencyImportService;
import ru.ann.currencyrate.service.algorithm.AverageRate;
import ru.ann.currencyrate.common.CurrencyConstant;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class AverageRateTest {
    List<CurrencyData> list;

    public AverageRateTest() {
        CurrencyImportService.importCurrencyMap();
        list = CurrencyDataRepository.getListByCurrencyName(CurrencyName.EUR);
    }

    @Test
    public void getNextValue() {
        AverageRate averageRate = new AverageRate();
        BigDecimal actual = averageRate.getNextValue(list, LocalDate.parse("12.04.2023", CurrencyConstant.UPLOAD_FORMATTER));
        BigDecimal expected = new BigDecimal("83.1379");
        assertEquals(actual, expected);
    }
}