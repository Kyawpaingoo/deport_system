package Infra.CSVReader;
import Model.CSVParsable;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CSVReader {
    private  static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static <T extends CSVParsable<T>> List<T> getList(String filePath, T entity){
        List<T> list = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line = br.readLine();

            while ((line = br.readLine()) != null)
            {
                String[] values = line.split(",");
                list.add(entity.parseFromCSV(values));
            }
        }
        catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return list;
    }

    public static  <T extends CSVParsable<T>> Map<Integer, T> getMap(String filePath, T entity) {
        Map<Integer, T> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                T obj = entity.parseFromCSV(values);
                map.put(obj.getId(), obj);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return map;
    }

    public static void insertRow(String filePath, String[] newRow)
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true)))
        {
            bw.write(String.join(",", newRow));
            bw.newLine();
        }
        catch (IOException e)
        {
            System.err.println("Error inserting to file: " + e.getMessage());
        }
    }

    public static void deleteRow(String filePath, int ID) {
        File inputFile = new File(filePath);
        File tempFile = new File("./src/DataFile/temp.csv");

        File tempDir = tempFile.getParentFile();
        if (!tempDir.exists() && !tempDir.mkdirs()) {
            System.err.println("Could not create directory for temporary file: " + tempDir);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean rowDeleted = false;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length > 0) {
                    try {
                        int rowID = Integer.parseInt(values[0]);
                        if (rowID != ID) {
                            bw.write(line);
                            bw.newLine();
                        } else {
                            rowDeleted = true;
                        }
                    } catch (NumberFormatException ex) {
                        System.err.println("Skipping row with invalid ID: " + line);
                    }
                }
            }

            if (!rowDeleted) {
                System.out.println("No matching row found for ID: " + ID);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + filePath);
            return;
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            return;
        }

        if (!inputFile.delete()) {
            System.err.println("Could not delete original file: " + inputFile.getAbsolutePath());
        } else if (!tempFile.renameTo(inputFile)) {
            System.err.println("Could not rename temp file to original file name: " + inputFile.getAbsolutePath());
        }
    }

    public static <T extends CSVParsable<T>> void writeListToCSV(String filePath, List<T> list, boolean isAppend)
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, isAppend)))
        {
            for (T entity : list) {
                bw.write(String.join(",", entity.toCSVRow()));
                bw.newLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static <T extends CSVParsable<T>> void updateRow(String filePath, T updatedEntity)
    {
        List<T> list = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (String.valueOf(updatedEntity.getId()).equals(values[0])) {
                    list.add(updatedEntity);
                    found = true;
                } else {
                    list.add(updatedEntity.parseFromCSV(values));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeListToCSV(filePath, list, false);

    }
}
