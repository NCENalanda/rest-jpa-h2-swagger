package eternal.hoge.spring.boot.example.simple.utils;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.json.simple.JSONObject;

import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.StatusConstantNumber.*;
import static eternal.hoge.spring.boot.example.simple.utils.CoreServiceUtil.getEntityClassName;

@SuppressWarnings("unchecked")
@UtilityClass
public class ExceptionResponseUtil {

    public static JSONObject getExceptionResponse(Exception ex, Integer errorStatusCode) {
        val exceptionResponse = new JSONObject();
        switch (getEntityClassName(ex)) {
            case "UnknownHostException":
                exceptionResponse.put(IntegrationConstantUtil.ValueConstant.ERROR_MSG, "Getting unknown host exception :: " + ex.getLocalizedMessage());
                exceptionResponse.put(IntegrationConstantUtil.ValueConstant.ERROR_STATUS_CODE,CoreConstantUtil.StatusConstantNumber.ORIGIN_IS_UNREACHABLE);
                return exceptionResponse;
            case "ConnectException":
                exceptionResponse.put(IntegrationConstantUtil.ValueConstant.ERROR_MSG, "Getting connection time out  exception");
                exceptionResponse.put(IntegrationConstantUtil.ValueConstant.ERROR_STATUS_CODE, CoreConstantUtil.StatusConstantNumber.CONNECTION_TIMED_OUT);
                return exceptionResponse;
            case "MalformedURLException":
                exceptionResponse.put(IntegrationConstantUtil.ValueConstant.ERROR_MSG, "Getting malformed URL exception :: " + ex.getLocalizedMessage());
                exceptionResponse.put(IntegrationConstantUtil.ValueConstant.ERROR_STATUS_CODE, CoreConstantUtil.StatusConstantNumber.UPGRADE_REQUIRED);
                return exceptionResponse;
            case "FileNotFoundException":
                exceptionResponse.put(IntegrationConstantUtil.ValueConstant.ERROR_MSG, "Getting file not found exception :: " + ex.getLocalizedMessage());
                exceptionResponse.put(IntegrationConstantUtil.ValueConstant.ERROR_STATUS_CODE, CoreConstantUtil.StatusConstantNumber.NOT_FOUND);
                return exceptionResponse;
            default:
                exceptionResponse.put(IntegrationConstantUtil.ValueConstant.ERROR_MSG, ex.getMessage());
                exceptionResponse.put(IntegrationConstantUtil.ValueConstant.ERROR_STATUS_CODE, errorStatusCode);
                return exceptionResponse;

        }
    }
}
