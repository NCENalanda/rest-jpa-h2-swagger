package eternal.hoge.spring.boot.example.simple.utils.httpconnection;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static  eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ValueConstant.INSIDE_METHOD;

@Getter
@Slf4j
public class GenericUrlConnection {

    private Object data;
    private Integer statusCode;
    private Exception exception;

    GenericUrlConnection(GenericUrlConnectionBuilder builder) {
        this.data = builder.getData();
        this.statusCode = builder.getStatusCode();
        this.exception = builder.getException();
    }

    public static GenericUrlConnectionBuilder con(){ return GenericUrlConnectionBuilder.getInstance();}

    public static GenericUrlConnection sendGetRequest(String url, Map<String, String> requestProperty) {
        log.trace(INSIDE_METHOD, "sendGetRequest");
        return getRequest(url, requestProperty, false);
    }

    public static GenericUrlConnection sendGetRequest(String url, Map<String, String> requestProperty, boolean isSsl) {
        log.trace(INSIDE_METHOD, "sendGetRequest");
        return getRequest(url, requestProperty, isSsl);
    }

    private static GenericUrlConnection getRequest(String url, Map<String, String> requestProperty, boolean isSsl) {
        if (isSsl)
            return sslRequestBuilder(url, "GET", requestProperty)
                    .sendRequest("")
                    .build();
        else
            return requestBuilder(url, "GET", requestProperty)
                    .sendRequest("")
                    .build();
    }

    public static GenericUrlConnection sendPostRequest(String url, Map<String, String> requestProperty, Object requestBody) {
        log.trace(INSIDE_METHOD, "sendPostRequest");
        return postRequest(url, requestProperty, requestBody, false);
    }

    public static GenericUrlConnection sendPostRequest(String url, Map<String, String> requestProperty, Object requestBody, boolean isSsl) {
        log.trace(INSIDE_METHOD, "sendPostRequest");
        return postRequest(url, requestProperty, requestBody, isSsl);
    }

    private static GenericUrlConnection postRequest(String url, Map<String, String> requestProperty, Object requestBody, boolean isSsl) {
        if (isSsl)
            return sslRequestBuilder(url, "POST", requestProperty)
                    .setRequestBody(requestBody)
                    .sendRequest("")
                    .build();
        else
            return requestBuilder(url, "POST", requestProperty)
                    .setRequestBody(requestBody)
                    .sendRequest("")
                    .build();

    }

    private static GenericUrlConnectionBuilder requestBuilder(String url, String methodType, Map<String, String> requestProperty) {
        return con()
                .setUrl(url)
                .setRequestMethod(methodType)
                .setRequestProperty(requestProperty);
    }

    private static GenericUrlConnectionBuilder sslRequestBuilder(String url, String methodType, Map<String, String> requestProperty) {
        return con()
                .getCertified()
                .setUrl(url)
                .setRequestMethod(methodType)
                .setRequestProperty(requestProperty);
    }

}
