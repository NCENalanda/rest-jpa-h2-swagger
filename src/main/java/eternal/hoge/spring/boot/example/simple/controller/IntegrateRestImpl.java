package eternal.hoge.spring.boot.example.simple.controller;



//import com.inn.service.IConfigValueService;
import eternal.hoge.spring.boot.example.simple.service.IRequestService;

import eternal.hoge.spring.boot.example.simple.utils.NvRequestParam;
import eternal.hoge.spring.boot.example.simple.utils.httpconnection.GenericUrlConnection;
import eternal.hoge.spring.boot.example.simple.response.GenericResponse;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.simple.parser.JSONParser;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static eternal.hoge.spring.boot.example.simple.utils.ConnectionServiceUtil.*;
import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.StatusConstantNumber.*;
import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ValueConstant.EXCEPTION_LABEL_TAG;
import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ValueConstant.INSIDE_METHOD;
import static eternal.hoge.spring.boot.example.simple.utils.ExceptionResponseUtil.getExceptionResponse;
import static eternal.hoge.spring.boot.example.simple.utils.IntegrationConstantUtil.ValueConstant.ERROR_STATUS_CODE;
import static eternal.hoge.spring.boot.example.simple.utils.IntegrationConstantUtil.ValueConstant.IDENTIFIER;
import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
@RequestMapping("controller")
@Slf4j
public class IntegrateRestImpl {

//    @Autowired
//    private IConfigValueService configValueService;

    @Autowired
    private IRequestService requestService;

//    @Autowired
//    private JaegerTracerService jaegerTracerService;


//    @PostMapping(value = "getDataFromServer")
//    public GenericResponse getDataFromServer(@RequestBody NvRequestParam request, HttpServletRequest httpServletRequest) {
//        val methodName = "getDataFromServer";
//
//
//        log.trace("INSIDE_METHOD", methodName);
//        log.info("Request for getting data from server");
//        val jsonParser = new JSONParser();
//        final GenericUrlConnection genericUrlConnection;
//        try {
//            val url = configValueService.getCompleteUrl(request);
//            if (request.getMethod().equalsIgnoreCase("GET"))
//                genericUrlConnection = sendGetRequest(url, request);
//            else
//                genericUrlConnection = sendPostRequest(url, request, true);
//
//            return getGenericResponse(request, span, jsonParser, genericUrlConnection);
//        } catch (Exception e) {
//            log.error("process {} request", request.getIdentifier(), kv(EXCEPTION_LABEL_TAG, e.getMessage()), e);
//            spanException(span, e);
//            return GenericResponse.serverError(e);
//        }
//
//    }

    @PostMapping(value = "getDataFromServer2")
    public GenericResponse getDataFromServer2(@RequestBody NvRequestParam request, HttpServletRequest httpServletRequest) {
        val methodName = "getDataFromServer";

        MDC.put(IDENTIFIER, getProcessRequest(request));
        log.trace(INSIDE_METHOD, methodName);
        log.info("Request for getting data from server");
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

    @PostMapping(value = "getDataFromServer3")
    public GenericResponse getDataFromServer3(@RequestBody List<NvRequestParam> listRequest, HttpServletRequest httpServletRequest) {
        val methodName = "getDataFromServer";
        requestService.listRequest(listRequest,httpServletRequest);
        List<GenericResponse> listGenericResponse = new ArrayList<>();
        for (NvRequestParam request:listRequest) {



        MDC.put(IDENTIFIER, getProcessRequest(request));
        log.trace(INSIDE_METHOD, methodName);
        log.info("Request for getting data from server");
        val jsonParser = new JSONParser();
        final GenericUrlConnection genericUrlConnection;
        try {
            val url = request.getUrl();
            if (request.getMethod().equalsIgnoreCase("GET"))
                genericUrlConnection = sendGetRequest(url, request);
            else
                genericUrlConnection = sendPostRequest(url, request, true);

             listGenericResponse.add(getGenericResponse(request, jsonParser, genericUrlConnection));
        } catch (Exception e) {
            log.error("process {} request", request.getIdentifier(), kv(EXCEPTION_LABEL_TAG, e.getMessage()), e);

            listGenericResponse.add(GenericResponse.serverError(e));
        }
        }
            return GenericResponse.ok(listGenericResponse).build();


    }

//    @PostMapping("/acRestClient")
//    public GenericResponse createTicketForClient(@RequestBody NvRequestParam request, HttpServletRequest httpServletRequest, @RequestParam(value = "transactionId") String transactionId) {
//        val methodName = "createTicketForClient";
//
//        MDC.put(IDENTIFIER, getProcessRequest(request));
//        log.trace(INSIDE_METHOD, methodName);
//        log.info("Request for creating ticket for accenture client");
//        val jsonParser = new JSONParser();
//        final GenericUrlConnection genericUrlConnection;
//        try {
//
//            val url = configValueService.getCompleteUrl(request);
//            configValueService.acSetRequestHeader(request, transactionId, span);
//            if (request.getMethod().equals("POST"))
//                genericUrlConnection = sendPostRequest(url, request, false);
//            else if (request.getMethod().equals("DELETE"))
//                genericUrlConnection = sendDeleteRequest(url + "/" + transactionId, request);
//            else
//                genericUrlConnection = sendGetRequest(url + "/" + transactionId, request);
//
//            return getGenericResponse(request, span, jsonParser, genericUrlConnection);
//
//        } catch (Exception e) {
//            log.error("process {} request", request.getIdentifier(), kv(EXCEPTION_LABEL_TAG, e.getMessage()), e);
//            spanException(span, e);
//            return GenericResponse.serverError(e);
//        }
//
//    }


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



}
