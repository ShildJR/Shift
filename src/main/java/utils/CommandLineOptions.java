package utils;


import org.apache.commons.cli.*;

import java.io.File;



public class CommandLineOptions {

    private String[] inputFiles;
    private boolean append = false;
    private String prefixFiles;
    private File pathFiles;
    private boolean shortStat=false;
    private boolean fullStat = false;

    public CommandLineOptions(String[] args){
        Options options = new Options();
        options.addOption("a",false,"Добавить строки к файлу, при условии что он существует");
        options.addOption("p",true,"Префикс выходных файлов");
        options.addOption("o",true,"Дирректория выходных файлов");
        options.addOption("s",false,"Короткая статистика по файлам");
        options.addOption("f",false,"Полная статистика по файлам");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try{
            if(args.length == 0){
                throw new RuntimeException("Требуется ввести аргументы для работы утилиты");
            }
            cmd = parser.parse(options,args);
            if(cmd.hasOption("a")){
                append = true;
            }
            if(cmd.hasOption("p")){
                prefixFiles = cmd.getOptionValue("p");
            }
            if(cmd.hasOption("o")){
                pathFiles=new File("." + cmd.getOptionValue("o"));
            }
            if(cmd.hasOption("s")){
                shortStat=true;
            }
            if(cmd.hasOption("f")){
                fullStat=true;
            }
            inputFiles = cmd.getArgs();
        }catch (ParseException pe){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "тестовое задание", options );
        }
    }


    public String[] getInputFiles() {
        return inputFiles;
    }

    public boolean isAppend() {
        return append;
    }

    public String getPrefixFiles() {
        return prefixFiles;
    }

    public File getPathFiles() {
        return pathFiles;
    }

    public boolean isShortStat() {
        return shortStat;
    }

    public boolean isFullStat() {
        return fullStat;
    }


}