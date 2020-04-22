package eternal.hoge.spring.boot.example.simple.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class CoreConstantUtil {

    /**
     *
     */
    @UtilityClass
    public static final class ResponseStatus {
        public static final String SUCCESS = "SUCCESS";
        public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
        public static final String NOT_IMPLEMENTED_SERVER_ERROR = "NOT_IMPLEMENTED_SERVER_ERROR";
        public static final String NO_CONTENT = "NO_CONTENT";
        public static final String INVALID_PATH_SEQUENCE = "INVALID_PATH_SEQUENCE";
        public static final String FILE_NOT_FOUND = "FILE_NOT_FOUND";
        public static final String NOT_CREATE_DIRECTORY = "NOT_CREATE_DIRECTORY";
        public static final String FAILED = "FAILED";
        public static final String SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE";
        public static final String NOT_FOUND = "NOT_FOUND";
    }

    /**
     *
     */
    @UtilityClass
    public static final class ResponseKey {
        public static final String DATA = "data";
        public static final String STATUS = "status";
        public static final String STATUS_INFO = "statusInfo";
        public static final String STATUS_CODE = "statusCode";
        public static final String COUNT = "count";
        public static final String MESSAGE = "message";
        public static final String MEDIA_TYPE = "mediaType";
        public static final String ERROR = "error";
        public static final String HTTP_STATUS_CODE = "http.status_code";

    }

    /**
     *
     */
    @UtilityClass
    public static final class ValueConstant {
        public static final String AUTHORIZATION = "Authorization";
        public static final String APP_NAME = "Omnicare";
        public static final String EXCEPTION_LABEL_TAG = "exceptionLabelTag";
        public static final String EXCEPTION_METHOD = "Exception @method {}";
        public static final String SET_URL = "setUrl";
        public static final String INSIDE_METHOD = "Inside @method {} >>>>>";
        public static final String FROM_METHOD = "Return from the method {} <<<<<";
        public static final String BEARER = "Bearer ";
        public static final String CONTENT_TYPE = "Content-Type";

    }

    /**
     *
     */
    @UtilityClass
    public static final class CoreMessage {
        public static final String DATA_CREATED = "Successfully created data";
        public static final String DATA_UPDATED = "Successfully updated data";
        public static final String DATA_DELETED = "Successfully deleted data";
        public static final String SOMETHING_WENT_WRONG = "Something Went Wrong!";

    }

    /**
     *
     */
    @UtilityClass
    public static final class ConstantNumber {
        public static final Integer ZERO = 0;
        public static final Integer ONE = 1;
        public static final Integer TWO = 2;
        public static final Integer THREE = 3;
        public static final Integer FOUR = 4;
        public static final Integer FIVE = 5;
        public static final Integer SIX = 6;
        public static final Integer SEVEN = 7;
        public static final Integer EIGHT = 8;
        public static final Integer NINE = 9;
        public static final Integer TEN = 10;
        public static final Integer ELEVEN = 11;
        public static final Integer TWELVE = 12;
        public static final Integer TWENTY = 20;
        public static final Integer FIFTY = 50;
        public static final Integer SIXTY = 60;
        public static final Integer ONE_TWENTY = 120;
    }

    /**
     *
     */

    @UtilityClass
    public static final class StatusConstantNumber {
        public static final Integer OK = 200;
        public static final Integer NO_CONTENT = 204;
        public static final Integer NOT_FOUND = 404;
        public static final Integer BAD_REQUEST_ERROR = 400;
        public static final Integer UPGRADE_REQUIRED = 426 ;
        public static final Integer INTERNAL_SERVER_ERROR = 500;
        public static final Integer NOT_IMPLEMENTED_SERVER_ERROR = 501;
        public static final Integer SERVICE_UNAVAILABLE = 503;
        public static final Integer GATEWAY_TIMEOUT = 504;
        public static final Integer ORIGIN_IS_UNREACHABLE = 523;
        public static final Integer CONNECTION_TIMED_OUT = 522;
    }

    /**
     *
     */
    @UtilityClass
    public static final class HistoryConstant {

        public static final String HISTORY_CREATED = "Successfully Created History";

    }

    @UtilityClass
    public static final class DocumentValueConstant {
        public static final long MIN_FILE_SIZE = 5120;
        public static final int ONE_KB_I = 1024;
        public static final float ONE_KB_F = 1024.0f;
        public static final int FOUR_KB = 4096;

    }


    @UtilityClass
    public static final class MetricsConstant {
        public static final String RECEIVED_COUNTER = "receivedCounter";
        public static final String RESPONDED_COUNTER = "respondedCounter";
        public static final String UNKNOWN_ERROR_COUNTER = "dbQueryFailureCounter";
        public static final String DB_QUERY_FAILURE_COUNTER = "dbConnectFailureCounter";
        public static final String DB_CONNECT_FAILURE_COUNTER = "unknownErrorCounter";
    }

}
