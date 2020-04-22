package eternal.hoge.spring.boot.example.simple.utils;

import com.fasterxml.uuid.Generators;
import lombok.experimental.UtilityClass;
import lombok.val;

import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;


import java.util.Arrays;

@UtilityClass
public class CoreServiceUtil {

    public static String getEntityClassName(Object entityObject) {
        return entityObject.getClass().getSimpleName().split("[_.$]")[0];
    }


    public static <T> T beanCopyProperties(Object from, T to) {
        BeanUtils.copyProperties(from, to);
        return to;
    }

    @SafeVarargs
    public static <T> boolean isNonNullValue(T... t) {
        return !isNullValue(t);
    }


    @SafeVarargs
    public static <T> boolean isNullValue(T... t) {
        return Arrays.asList(t).contains(null);
    }

    public static void generateRequestId(String appName) {
        MDC.put("requestId", String.valueOf(Generators.timeBasedGenerator().generate()));
        MDC.put("appName", appName);
        MDC.put("loginUsername","ayus");
        CoreCustomMetrics.generateMetrics(CoreConstantUtil.MetricsConstant.RECEIVED_COUNTER);
    }

    public static void removeGenerateRequestId() {
        MDC.remove("requestId");
        MDC.remove("appName");
        MDC.remove("loginUsername");
    }


}
