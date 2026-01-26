package distribuidora.scrapping.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
	public static Date getStartDate(LocalDate date) {
		return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date getEndDate(LocalDate date) {
		return Date.from(date.atTime(23,59,59,59).atZone(ZoneId.systemDefault()).toInstant());
	}
}
