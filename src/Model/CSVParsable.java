package Model;

public interface CSVParsable<T> {
    T parseFromCSV(String[] values);
    String[] toCSVRow();
    default Integer getId() {
        return null;
    }
}
