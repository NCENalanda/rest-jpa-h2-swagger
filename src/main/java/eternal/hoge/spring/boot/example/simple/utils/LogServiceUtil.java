package eternal.hoge.spring.boot.example.simple.utils;

import lombok.experimental.UtilityClass;
import net.logstash.logback.argument.StructuredArgument;

import java.util.ArrayList;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@UtilityClass
public class LogServiceUtil {

    public static void errorMetrics(Exception ex) {
        CoreCustomMetrics.generateExceptionMetrics(ex);
    }

    public static Builder logKV() {
        return Builder.getInstance();
    }

    public static class Builder {

        private List<StructuredArgument> kv;

        Builder() {
            kv = new ArrayList<>();
        }

        static Builder getInstance() {
            return new Builder();
        }

        public Builder add(String key, Object value) {
            kv.add(kv(key, value));
            return this;
        }

        public Builder add(StructuredArgument structuredArgument) {
            kv.add(structuredArgument);
            return this;
        }

        public Object[] build() {
            return kv.toArray();
        }
    }

}
