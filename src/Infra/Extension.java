package Infra;

import java.time.LocalDate;
import java.util.Random;

public class Extension {
    public static LocalDate getLocalTime()
    {
        return LocalDate.now();
    }

    public static int generateNumber()
    {
        Random random = new Random();
        int randomNumber = random.nextInt(10000) + 1;
        return randomNumber;
    }
}
