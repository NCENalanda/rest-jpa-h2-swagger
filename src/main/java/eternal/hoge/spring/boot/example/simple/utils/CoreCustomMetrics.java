package eternal.hoge.spring.boot.example.simple.utils;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class CoreCustomMetrics {

    private static final String REQUEST = "request";
    private static Counter receivedCounter;
    private static Counter respondedCounter;
    private static Counter dbQueryFailureCounter;
    private static Counter dbConnectFailureCounter;
    private static Counter unknownErrorCounter;

    @Autowired
    private CoreCustomMetrics(MeterRegistry meterRegistry,CoreProperties coreProperties) {
        val appName = coreProperties.getAppName();
        CoreCustomMetrics.initizeMetrics(meterRegistry, appName);
    }

    public static void initizeMetrics(MeterRegistry meterRegistry, String appName) {
        receivedCounter = meterRegistry.counter(appName, REQUEST, "received");
        respondedCounter = meterRegistry.counter(appName, REQUEST, "responded");
        dbQueryFailureCounter = meterRegistry.counter(appName, REQUEST, "db_query_failure");
        dbConnectFailureCounter = meterRegistry.counter(appName, REQUEST, "db_connection_failure");
        unknownErrorCounter = meterRegistry.counter(appName, REQUEST, "unknown_exception");
    }

    public static void generateMetrics(String type) {
        if (type.equalsIgnoreCase("receivedCounter")) {
            receivedCounter.increment();
        } else if (type.equalsIgnoreCase("respondedCounter")) {
            respondedCounter.increment();
        }
    }

    public static void generateExceptionMetrics(Throwable ex) {
        val caughtException = ex.getClass().getSimpleName();
        if (caughtException.contains("SQLGrammarException")) {
            dbQueryFailureCounter.increment();
        } else if (caughtException.contains("DataAccessResourceFailureException")) {
            dbConnectFailureCounter.increment();
        } else if (caughtException.contains("InvalidDataAccessResourceUsageException")) {
            dbQueryFailureCounter.increment();
        } else {
            unknownErrorCounter.increment();
        }
    }
}
