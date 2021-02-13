package com.mycompany.service;

import com.mycompany.model.User;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.*;
import com.opencsv.exceptions.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class HelperClass {

    public static boolean isExists(User user, String path){
       List<User> users = parseCSVtoUsers(path);
       for(User tmpUser: users){
           if(tmpUser.equals(user)){
               return true;
           }
       }
       return false;
    }

    public static void writeUserToCSV(User user, String path) {
        try (FileWriter writer = new FileWriter(path, true)) {
            ColumnPositionMappingStrategy<User> mapStrategy = new ColumnPositionMappingStrategy<>();
            mapStrategy.setType(User.class);
            String[] columns = new String[]{"name", "surname", "patronymic", "age", "salary", "email", "workplace"};
            mapStrategy.setColumnMapping(columns);
            StatefulBeanToCsvBuilder<User> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<User> beanWriter = builder.withMappingStrategy(mapStrategy).build();
            beanWriter.write(user);
        } catch (CsvRequiredFieldEmptyException | IOException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<User> parseCSVtoUsers(String path) {
        ArrayList<User> users = null;
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withSkipLines(1)
                .build())
        {
            ColumnPositionMappingStrategy<User> mapStrategy = new ColumnPositionMappingStrategy<>();
            mapStrategy.setType(User.class);
            String[] columns = new String[]{"name", "surname", "patronymic", "age", "salary", "email", "workplace"};
            mapStrategy.setColumnMapping(columns);
            CsvToBean<User> ctb = new CsvToBeanBuilder<User>(reader)
                    .withType(User.class).withMappingStrategy(mapStrategy).build();
            users = (ArrayList<User>) ctb.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static int findByFullName(List<User> users, String name, String surname) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equalsIgnoreCase(name) && users.get(i).getSurname().equalsIgnoreCase(surname)) {
                return i;
            }
        }
        return -1;
    }
}


