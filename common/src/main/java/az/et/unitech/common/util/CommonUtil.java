package az.et.unitech.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private static final AtomicInteger counter = new AtomicInteger(100_000);

    public static String generateId() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(formatter) + "C" + counter.getAndIncrement();
    }

}
