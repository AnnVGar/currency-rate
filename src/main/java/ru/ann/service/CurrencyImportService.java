package ru.ann.service;

import lombok.extern.slf4j.Slf4j;
import ru.ann.domain.CurrencyData;
import ru.ann.domain.CurrencyName;
import ru.ann.respository.CurrencyDataRespository;
import ru.ann.util.DateUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CurrencyImportService {
    private static final int RATE_DAY_QUANTITY = 7;
    private static final String SEPARATOR = File.separator;
    private static final RoundingMode roundingMode = RoundingMode.HALF_UP;
    private static final CurrencyDataRespository respository = new CurrencyDataRespository();


    public static void importCurrencyMap(){
        Arrays.stream(CurrencyName.values()).forEach(currencyName ->respository.currencyMap.put(currencyName,fillCurrencyDataList(currencyName)));
    }


    /**
     * заполнение списка данных о курсе валют
     * из файла
     *
     * @param currencyName название валюты
     */
    private static List<CurrencyData> fillCurrencyDataList(CurrencyName currencyName) {
        List<CurrencyData> currencyDataList = new ArrayList<>();

        File file = new File(System.getProperty("user.dir") + chageDirectory()+SEPARATOR + "src"+SEPARATOR+"main"+SEPARATOR+"resources"+SEPARATOR+currencyName.name().toLowerCase() + ".csv");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            for (int i = 0; i < RATE_DAY_QUANTITY; i++) {
                String[] commandLine = br.readLine().split(";");
                int nominal = Integer.parseInt(commandLine[0]);
                LocalDate date = LocalDate.parse(commandLine[1], DateUtils.UPLOAD_FORMATTER);
                BigDecimal curs = new BigDecimal(commandLine[2].replace(",","."));
                String cdx = commandLine[3];
                BigDecimal value = curs.divide(new BigDecimal(nominal), roundingMode);
                CurrencyData currencyData = new CurrencyData(nominal, date, curs,cdx, value);
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

    private static String chageDirectory(){
        String arr[] = System.getProperty("user.dir").split("\\"+SEPARATOR);
        if(arr[arr.length-1].equals("target")){
            return File.separator +".." +File.separator;
        }
        return "";
    }
}
