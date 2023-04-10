package ru.ann.currencyrate.service;

import lombok.extern.slf4j.Slf4j;
import ru.ann.currencyrate.common.CurrencyConstant;
import ru.ann.currencyrate.domain.CurrencyData;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.repository.CurrencyDataRepository;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CurrencyImportService {
    public static void importCurrencyMap() {
        Arrays.stream(CurrencyName.values()).forEach(currencyName -> CurrencyDataRepository.currencyMap.put(currencyName, fillCurrencyDataList(currencyName)));
    }


    /**
     * заполнение списка данных о курсе валют
     * из файла
     *
     * @param currencyName название валюты
     */
    private static List<CurrencyData> fillCurrencyDataList(CurrencyName currencyName) {
        List<CurrencyData> currencyDataList = new ArrayList<>();
        File file = new File(chageDirectory() + "." + CurrencyConstant.SOURCE_PATH + currencyName.name().toLowerCase() + CurrencyConstant.EXTENSION);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String str;
            while ((str = br.readLine()) != null) {
                String[] dataArr = str.split(";");
                int nominal = Integer.parseInt(dataArr[CurrencyConstant.NOMINAL_INDEX].replace(" ", ""));
                LocalDate date = LocalDate.parse(dataArr[CurrencyConstant.DATE_INDEX], CurrencyConstant.UPLOAD_FORMATTER);
                BigDecimal curs = new BigDecimal(dataArr[CurrencyConstant.CURS_INDEX].replace(",", "."));
                String name = dataArr[CurrencyConstant.NAME_INDEX];
                BigDecimal unitCurs = curs.divide(new BigDecimal(nominal), CurrencyConstant.ROUNDING_MODE);
                CurrencyData currencyData = new CurrencyData(nominal, date, curs, name, unitCurs);
                currencyDataList.add(currencyData);
            }
        } catch (FileNotFoundException e) {
            log.error("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencyDataList;
    }

    private static String chageDirectory() {
        String currentFile = System.getProperty("user.dir");
        if (currentFile.endsWith("target")) {
            return File.separator + "." + File.separator;
        }
        return "";
    }
}
