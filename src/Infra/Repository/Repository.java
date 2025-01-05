package Infra.Repository;

import Infra.CSVReader.CSVReader;
import Model.CSVParsable;

import javax.swing.text.html.Option;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Repository<T extends CSVParsable<T>> implements IRepository<T> {
    private final String filePath;
    private final T entity;
    private final boolean isMapBased;

    public Repository(String filePath, T entity,boolean isMapBased)
    {
        this.filePath = filePath;
        this.entity = entity;
        this.isMapBased = isMapBased;
    }

    @Override
    public List<T> getList() {
        return CSVReader.getList(filePath, entity);
    }

    @Override
    public Map<Integer, T> getMap() {
        return CSVReader.getMap(filePath, entity);
    }

    @Override
    public boolean insert(T entity) {
        try {
            String[] row = entity.toCSVRow();
            CSVReader.insertRow(filePath, row);
            return true;
        }
        catch (Exception e)
        {
            System.err.println("Error inserting row: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id)
    {
        try{
            CSVReader.deleteRow(filePath, id);
            return true;
        }
        catch (Exception e)
        {
            System.err.println("Error inserting row: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<T> getById(int id) {
        if(isMapBased){
            Map<Integer, T> map = getMap();
            return Optional.ofNullable(map.get(id));
        }
        else {
            List<T> list = getList();
            return list.stream().filter(d -> d.getId() == id).findFirst();
        }
    }

    @Override
    public List<T> whereAsList(Predicate<T> predicate) {
        return getList().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, T> whereAsMap(Predicate<T> predicate) {
        return getMap().entrySet()
                .stream().filter(
                        d -> predicate.test(d.getValue())
                )
                .collect(Collectors
                        .toMap(
                                Map.Entry:: getKey,
                                Map.Entry:: getValue
                        )
                );
    }

    @Override
    public Map<Integer, T> sortAsMap(Predicate<T> predicate) {
        Map<Integer, T> sourceMap = getMap();
        if (sourceMap == null) {
            return Collections.emptyMap();
        }

        return sourceMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null && predicate.test(entry.getValue()))
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    @Override
    public List<T> Search(Predicate<T> predicate) {
        if(isMapBased){
           return getMap().values()
                   .stream().filter(predicate)
                   .collect(Collectors.toList());
        }
        else {
            return getList().stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public T update(T entity) {
        try {
            CSVReader.updateRow(filePath, entity);
            Optional<T> result = getById(entity.getId());

            return result.orElse(null);
        } catch (Exception e) {
            System.err.println("Error updating row: " + e.getMessage());
            throw new RuntimeException("Failed to update entity", e);
        }
    }

    public Optional<T> FirstOrDefault(Predicate<T> predicate) {
        if (isMapBased) {
            return getMap().values()
                    .stream()
                    .filter(predicate)
                    .findFirst();
        } else {
            return getList().stream()
                    .filter(predicate)
                    .findFirst();
        }
    }
}
