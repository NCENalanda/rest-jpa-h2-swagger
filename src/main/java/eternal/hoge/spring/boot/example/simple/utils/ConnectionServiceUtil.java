package eternal.hoge.spring.boot.example.simple.utils;

import eternal.hoge.spring.boot.example.simple.utils.httpconnection.GenericUrlConnection;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ValueConstant.INSIDE_METHOD;
import static eternal.hoge.spring.boot.example.simple.utils.IntegrationConstantUtil.ValueConstant.REQUEST_IDENTIFIER;
import static net.logstash.logback.argument.StructuredArguments.kv;

@UtilityClass
@Slf4j
public class ConnectionServiceUtil {

    public static GenericUrlConnection sendGetRequest(String url, NvRequestParam request) {
        log.trace(INSIDE_METHOD, "sendGetRequest");
        log.info("Sending http get request", LogServiceUtil.logKV().add(REQUEST_IDENTIFIER, request.getIdentifier()).build());
        return getGenericUrlConnection(url, request, "GET");

    }

    public static GenericUrlConnection sendDeleteRequest(String url, NvRequestParam request) {
        log.trace(INSIDE_METHOD, "sendDeleteRequest");
        log.info("Sending http delete request", LogServiceUtil.logKV().add(REQUEST_IDENTIFIER, request.getIdentifier()).build());
        return getGenericUrlConnection(url, request, "DELETE");

    }

    private static GenericUrlConnection getGenericUrlConnection(String url, NvRequestParam request, String requestType) {
        if (request.getIsSsl() != null && request.getIsSsl())
            return GenericUrlConnection.con()
                    .getCertified()
                    .setUrl(url)
                    .setRequestMethod(requestType)
                    .setRequestProperty(request.getHeader())
                    .sendRequest(request.getIdentifier())
                    .build();
        else
            return GenericUrlConnection.con()
                    .setUrl(url)
                    .setRequestMethod(requestType)
                    .setRequestProperty(request.getHeader())
                    .sendRequest(request.getIdentifier())
                    .build();
    }

    public static GenericUrlConnection sendPostRequest(String url, NvRequestParam request, boolean isCheckJson) throws  ParseException {
        log.trace(INSIDE_METHOD, "sendPostRequest");
        log.info("Sending http post request", LogServiceUtil.logKV().add(REQUEST_IDENTIFIER, request.getIdentifier()).build());
        Object data = null;
        if (request.getData() != null) {
            data = request.getData();
            if (isCheckJson) {
                JSONParser parser = new JSONParser();
                Object jsonObject = parser.parse(data.toString());
                if ((jsonObject instanceof JSONObject) || (jsonObject instanceof JSONArray)) {
                    log.info("Inside @method sendPostRequest valid json", kv("data", jsonObject));
                } else
                    throw new UnknownError("not a json object");
            }
        }

        if (request.getIsSsl() != null && request.getIsSsl())
            return GenericUrlConnection.con()
                    .getCertified()
                    .setUrl(url)
                    .setRequestMethod("POST")
                    .setRequestProperty(request.getHeader())
                    .setRequestBody(data)
                    .sendRequest(request.getIdentifier())
                    .build();
        else
            return GenericUrlConnection.con()
                    .setUrl(url)
                    .setRequestMethod("POST")
                    .setRequestProperty(request.getHeader())
                    .setRequestBody(data)
                    .sendRequest(request.getIdentifier())
                    .build();
    }

}
