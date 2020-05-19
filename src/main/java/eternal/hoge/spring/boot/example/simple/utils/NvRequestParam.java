package eternal.hoge.spring.boot.example.simple.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class NvRequestParam {

    private Map<String, String> queryParam;
    private Map<String, String> header;
    private Map<String, String> json;
    private List<String> pathParam;
    private Object data;
    private String host;
    private String port;
    private Boolean isSsl;
    private String method;
    private String identifier;
    private Boolean isAuth;
    private String url;

    public static Map<String, String> getHashMap() {
        return new HashMap<>();
    }

}
