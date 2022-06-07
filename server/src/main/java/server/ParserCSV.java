package server;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import data.Person;
import data.StudyGroup;
import exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.CollectionManager;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ParserCSV {

    private Logger logger;


    public ParserCSV(Logger logger){
        this.logger = logger;
    }

    public Hashtable<Long, StudyGroup> getCollectionFromFile(File file, CollectionManager studyGroupCollection) throws FileNotFoundException{

        int counter = 0;
        Hashtable<Long, StudyGroup> studyGroupHashtable = new Hashtable<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            CSVReader csvReader = new CSVReader(isr);

            List<String[]> list = csvReader.readAll();

            for (String[] arr : list) {
                try {
                    counter += 1;
                    StudyGroup studyGroup = StudyGroup.valueOf(arr);
                    if (studyGroupCollection.isIdUsed(studyGroup.getId())){
                        throw new DuplicateIdException();
                    }
                    if(studyGroup.getId() == 0) {
                        studyGroup.setId(studyGroupCollection.getMinFreeId());
                    }
                    studyGroupHashtable.put(studyGroup.getId(), studyGroup);
                    studyGroupCollection.addIdToUsed(studyGroup.getId());
                    logger.info("Элемент " + counter + " добавлен в коллекцию");
                }
                catch (WrongAmountOfValuesException e){
                    logger.warn("Неверное количество значений в строке " + counter + ", элемент добавлен не будет");
                }
                catch (NumberOutOfBoundsException e){
                    logger.warn("Числовое значение выходит за одз в строке " + counter);
                }
                catch (NullValueException e) {
                    logger.warn("Значение не может быть null в строке " + counter);
                }
                catch (DuplicateIdException e){
                    logger.warn("У данного объекта в строке " + counter + " id, который уже присутствует в базе");
                }
                catch (NumberFormatException e){
                    logger.warn("Строка " + counter + " не будет добавлена в коллекцию из-за неверного формата");
                }
            }
        }
        catch (IOException | CsvException e){
        }
        return studyGroupHashtable;
    }

    public void setFileFromCollection(Hashtable<Long, StudyGroup> hashtable) throws FileNotFoundException{
        try{
        FileWriter fileWriter = new FileWriter("output.csv", false);
        CSVWriter csvWriter = new CSVWriter(fileWriter);
        for (StudyGroup studyGroup: hashtable.values()){
            String[] values = stringArrFromStudyGroup(studyGroup);
            csvWriter.writeNext(values);
        }
            csvWriter.close();

            logger.info("Коллекция записана в файл");
        }
        catch (IOException e){
            if (e.getMessage().contains("(Отказано в доступе)")) System.out.println("Нет прав на запись в файл");
            else
                System.out.println("Файл, в который должна происходить запись данных, не существует");
        }
    }

    private String[] stringArrFromStudyGroup(StudyGroup studyGroup){
        String[] arr = new String[16];
        arr[0] = String.valueOf(studyGroup.getId());
        arr[1] = studyGroup.getName();
        arr[2] = String.valueOf(studyGroup.getCoordinates().getX());
        arr[3] = String.valueOf(studyGroup.getCoordinates().getY());
        arr[4] = studyGroup.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        arr[5] = String.valueOf(studyGroup.getStudentsCount());
        arr[6] = String.valueOf(studyGroup.getExpelledStudents());
        arr[7] = String.valueOf(studyGroup.getShouldBeExpelled());
        arr[8] = String.valueOf(studyGroup.getSemesterEnum());

        Person admin = studyGroup.getGroupAdmin();
        arr[9] = admin.getName();
        arr[10] = String.valueOf(admin.getHeight());
        arr[11] = String.valueOf(admin.getHairColor());
        arr[12] = String.valueOf(admin.getNationality());
        arr[13] = String.valueOf(admin.getLocation().getX());
        arr[14] = String.valueOf(admin.getLocation().getY());
        arr[15] = String.valueOf(admin.getLocation().getZ());

        return arr;
    }

}