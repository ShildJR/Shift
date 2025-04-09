package utils;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StatUtils {

    private static final PrintStream outputStream = System.out;
    private static final String shortStatsFormat = "%s: %d\n";
    private static final String fullStatsIntFormat = "%s: максимальное число: %d\tминимальное число: %d\tсумма: %d\tсреднее число: %d\tкол-во чисел: %d\n";
    private static final String fullStatsDoubleFormat = "%s: максимальное число: %s\tминимальное число: %s\tсумма: %s\tсреднее число: %s\tкол-во чисел: %d\n";
    private static final String fullStatsStringFormat = "%s: количество: %d\tмин. кол-во символов в строке: %d\tмax. кол-во символов в строке: %d\n";


    public static void shortStat(List<BigInteger> integers, List<String> strings, List<BigDecimal> floats, String integersFile, String floatsFile, String stringsFile){
        outputStream.println("Короткая статистика по файлам: " );
        outputStream.printf(shortStatsFormat,integersFile,integers.size());
        outputStream.printf(shortStatsFormat,floatsFile,floats.size());
        outputStream.printf(shortStatsFormat,stringsFile,strings.size());

    }
    private static void fullStatInteger(List<BigInteger> integers,String integersFile){
        Optional<BigInteger> minInt = integers.stream().min(BigInteger::compareTo);
        Optional<BigInteger> maxInt = integers.stream().max(BigInteger::compareTo);
        BigInteger sumInt = integers.stream().reduce(BigInteger.ZERO, BigInteger::add);
        BigInteger meanInt = integers.isEmpty() ? BigInteger.ZERO : sumInt.divide(BigInteger.valueOf(integers.size()));
        outputStream.printf(fullStatsIntFormat,integersFile,maxInt.orElse(BigInteger.ZERO),minInt.orElse(BigInteger.ZERO),sumInt,meanInt,integers.size());
    }

    private static void fullStatDouble(List<BigDecimal> floats, String floatsFile){
        Optional<BigDecimal> minDbl = floats.stream().min(BigDecimal::compareTo);
        Optional<BigDecimal> maxDbl = floats.stream().max(BigDecimal::compareTo);
        BigDecimal sumDbl = floats.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal meanDbl = floats.isEmpty() ? BigDecimal.ZERO : sumDbl.divide(BigDecimal.valueOf(floats.size()), BigDecimal.ROUND_HALF_UP);
        outputStream.printf(fullStatsDoubleFormat,floatsFile,maxDbl.orElse(BigDecimal.ZERO),minDbl.orElse(BigDecimal.ZERO),sumDbl,meanDbl,floats.size());

    }

    private static void fullStatString( List<String> strings,String stringsFile){
        Optional<String> minStr =  strings.stream().min(Comparator.comparingInt(String::length));
        Optional<String> maxStr =  strings.stream().max(Comparator.comparingInt(String::length));
        outputStream.printf(fullStatsStringFormat,stringsFile,strings.size(), minStr.map(String::length).orElse(0), maxStr.map(String::length).orElse(0));

    }

    public static void fullStat(List<BigInteger> integers, List<String> strings, List<BigDecimal> floats, String integersFile, String floatsFile, String stringsFile){
        outputStream.println("Полная статистика по файлам: " );
        fullStatInteger(integers,integersFile);
        fullStatDouble(floats,floatsFile);
        fullStatString(strings,stringsFile);


    }
}
