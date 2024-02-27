package util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConvertor {

    public static String getCurrentDateTimeForScreenshot() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        return sdf.format(DateUtils.truncate(new Date(), java.util.Calendar.SECOND));
    }
    public static String getCurrentDateForReportName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(DateUtils.truncate(new Date(), java.util.Calendar.SECOND));
    }
}
