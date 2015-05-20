import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by artur.shamsiev on 20.05.2015
 */
public class DateTes {
    @Test
    public void testDate() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        Date date = new Date();
        date.toInstant();
        LocalDateTime localTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        System.out.println(localTime.format(formatter));
        System.out.println(date);

    }
}
