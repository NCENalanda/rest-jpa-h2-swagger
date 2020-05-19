package eternal.hoge.spring.boot.example.simple.service.impl;


import eternal.hoge.spring.boot.example.simple.response.GenericResponse;
import eternal.hoge.spring.boot.example.simple.service.IRequestService;
import eternal.hoge.spring.boot.example.simple.utils.NvRequestParam;

import eternal.hoge.spring.boot.example.simple.utils.httpconnection.GenericUrlConnection;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.simple.parser.JSONParser;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static eternal.hoge.spring.boot.example.simple.utils.ConnectionServiceUtil.*;
import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.StatusConstantNumber.*;
import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ValueConstant.EXCEPTION_LABEL_TAG;
import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ValueConstant.INSIDE_METHOD;
import static eternal.hoge.spring.boot.example.simple.utils.ExceptionResponseUtil.getExceptionResponse;
import static eternal.hoge.spring.boot.example.simple.utils.IntegrationConstantUtil.ValueConstant.ERROR_STATUS_CODE;
import static eternal.hoge.spring.boot.example.simple.utils.IntegrationConstantUtil.ValueConstant.IDENTIFIER;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
public class RequestServiceImpl implements IRequestService {

    @Override
    public GenericResponse listRequest(List<NvRequestParam> listRequest, HttpServletRequest httpServletRequest) {
        return null;
    }

    private  GenericResponse  processRequest(NvRequestParam request, HttpServletRequest httpServletRequest){
        val methodName = "processRequest";


        log.trace(INSIDE_METHOD, methodName);
        log.info("Request for getting data processRequest");
        val jsonParser = new JSONParser();
        final GenericUrlConnection genericUrlConnection;
        try {
            val url = request.getUrl();
            if (request.getMethod().equalsIgnoreCase("GET"))
                genericUrlConnection = sendGetRequest(url, request);
            else
                genericUrlConnection = sendPostRequest(url, request, true);

            return getGenericResponse(request, jsonParser, genericUrlConnection);
        } catch (Exception e) {
            log.error("process {} request", request.getIdentifier(), kv(EXCEPTION_LABEL_TAG, e.getMessage()), e);

            return GenericResponse.serverError(e);
        }

    }

    private String getProcessRequest(@RequestBody NvRequestParam request) {
        return "process " + request.getIdentifier() + " Request";
    }

    private GenericResponse getParsingData(JSONParser jsonParser, Object data, Integer statusCode) {
        Object jsonObject;
        GenericResponse response;
        try {
            jsonObject = jsonParser.parse((String) data);
            response = GenericResponse.ok().setData(jsonObject).setStatusCode(statusCode).build();
        } catch (Exception e) {
            log.error("Exception occurred while parsing data", kv(EXCEPTION_LABEL_TAG, e.getMessage()), e);
            response = GenericResponse.ok().setData(data).setStatusCode(statusCode).build();
        }
        return response;
    }



    private GenericResponse getGenericResponse(@RequestBody NvRequestParam request, JSONParser jsonParser, GenericUrlConnection genericUrlConnection) {
        GenericResponse response;
        val data = genericUrlConnection.getData();
        val statusCode = genericUrlConnection.getStatusCode();
        val logMsg = "Got response code from " + request.getIdentifier() + " request";
        if (statusCode.equals(OK) || statusCode.equals(NO_CONTENT)) {
            log.debug(logMsg, kv("responseCode", statusCode));
            response = getParsingData(jsonParser, data, statusCode);
        } else {
            val exception = genericUrlConnection.getException();
            val exceptionResponse = getExceptionResponse(exception, statusCode);
            log.error(logMsg, kv("responseCode", exceptionResponse.get(ERROR_STATUS_CODE)), kv(EXCEPTION_LABEL_TAG, exceptionResponse.get("errorMsg")), exception);
            response = GenericResponse.ok().setData(exceptionResponse).build();
        }
        MDC.remove(IDENTIFIER);
        return response;
    }


}
