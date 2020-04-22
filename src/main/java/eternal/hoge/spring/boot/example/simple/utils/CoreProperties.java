package eternal.hoge.spring.boot.example.simple.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix = "core")
@Component
@Getter
@Setter
public class CoreProperties {

    private String appName= "reactive";

    private String baseWarDirectory;

    private Integer fileNameLength;

    private String objectStorageEndpoint;

    private String objectStorageAccessKey;

    private String objectStorageSecretKey;

    private String objectStorageBucketName;

    private String profile;

    private String metricsName = "reactive";

}
