package utils;

//import lombok.RequiredArgsConstructor;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

//@RequiredArgsConstructor
public class FileProcess {

    public final static String postFixFileNameInteger = "integers.txt";
    public final static String postFixFileNameDouble = "floats.txt";
    public final static String postFixFileNameString = "strings.txt";

    private final CommandLineOptions clo;
    private final  List<BigInteger> integers = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();
    private final  List<BigDecimal> floats = new ArrayList<>();

    public FileProcess(CommandLineOptions clo) {
        this.clo = clo;
    }

    public void fillFiles() throws IOException {
        if(clo.getPathFiles()!=null) {
            if (!clo.getPathFiles().exists()) {
                clo.getPathFiles().mkdirs();
            }
        }
        String fileNameInteger = getOutputFileName(postFixFileNameInteger);
        String fileNameDouble =  getOutputFileName(postFixFileNameDouble);
        String fileNameString =  getOutputFileName(postFixFileNameString);

        createFileIfNotExists(fileNameInteger);
        createFileIfNotExists(fileNameDouble);
        createFileIfNotExists(fileNameString);

        try (PrintWriter writerInteger = new PrintWriter(new BufferedWriter(new FileWriter(fileNameInteger,clo.isAppend())));
             PrintWriter writerDouble = new PrintWriter(new BufferedWriter(new FileWriter(fileNameDouble,clo.isAppend())));
             PrintWriter writerString = new PrintWriter(new BufferedWriter(new FileWriter(fileNameString,clo.isAppend()))))
        {

            integers.forEach(writerInteger::println);
            floats.forEach(writerDouble::println);
            strings.forEach(writerString::println);

            if(clo.isShortStat()) {
                StatUtils.shortStat(integers,strings,floats,fileNameInteger,fileNameDouble,fileNameString);
            }else if(clo.isFullStat()){
                StatUtils.fullStat(integers,strings,floats,fileNameInteger,fileNameDouble,fileNameString);
            }

        }

    }

    private String getOutputFileName(String postFix) {
        String path = (clo.getPathFiles() != null) ? clo.getPathFiles().toString() : ".";
        String prefix = (clo.getPrefixFiles() != null) ? clo.getPrefixFiles() : "";

        return path + File.separator + prefix + postFix;
    }


    public void processInputFiles() {
        try {
            List<Scanner> scanners = new ArrayList<>();
            for (String fileName : clo.getInputFiles()) {
                scanners.add(new Scanner(new File(fileName)).useLocale(Locale.US));
            }

            while (!scanners.isEmpty()) {
                ListIterator<Scanner> it = scanners.listIterator();
                while (it.hasNext()) {
                    Scanner scanner = it.next();
                    if (!scanner.hasNext()) {
                        it.remove();
                        continue;
                    }
                    processScanner(scanner);
                }
            }

            fillFiles();


        } catch (FileNotFoundException e){
            throw new RuntimeException("Файл не найден");
        } catch (IOException e){
            throw new RuntimeException("Неизвестная ошибка с обработкой файлов");
        }

    }

    private void processScanner(Scanner scanner){
        if (scanner.hasNextBigInteger()) {
            integers.add(scanner.nextBigInteger());
        } else if (scanner.hasNextBigDecimal()) {
            floats.add(scanner.nextBigDecimal());
        } else if (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isEmpty())
                strings.add(line);
        }
    }

    private void createFileIfNotExists(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

}
