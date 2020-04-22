package eternal.hoge.spring.boot.example.simple.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import eternal.hoge.spring.boot.example.simple.utils.View;
import lombok.Getter;

import javax.persistence.Basic;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class GenericResponse {

    @JsonView(Basic.class)
    private Object data;

    @JsonView({Basic.class, View.Count.class})
    private String status;

    @JsonView(Basic.class)
    private Integer statusCode;

    @JsonView(Basic.class)
    private String contentType;

    @JsonView(Basic.class)
    private Integer contentLength;

    private Object message;

    private Integer previousPage;

    private Integer currentPage;

    private Integer nextPage;

    private Integer currentPageCount;

    private Integer pageSize;

    @JsonView(View.Count.class)
    private Integer totalCount;

    private Integer totalPage;

    GenericResponse(GenericResponseBuilder builder) {
        this.data = builder.getData();
        this.message = builder.getMessage();
        this.status = builder.getStatus();
        this.statusCode = builder.getStatusCode();
        this.contentType = builder.getContentType();
        this.contentLength = builder.getContentLength();
        this.previousPage = builder.getPreviousPage();
        this.currentPage = builder.getCurrentPage();
        this.nextPage = builder.getNextPage();
        this.currentPageCount = builder.getCurrentPageCount();
        this.pageSize = builder.getPageSize();
        this.totalCount = builder.getTotalCount();
        this.totalPage = builder.getTotalPage();
    }

    public static GenericResponseBuilder ok() {
        return GenericResponseBuilder.getInstance();
    }

    public static GenericResponseBuilder ok(Object data) {
        return GenericResponseBuilder.getInstance().setData(data);
    }

    public static GenericResponseBuilder ok(Integer statusCode) {
        return GenericResponseBuilder.getInstance().setStatusCode(statusCode);
    }

    public static GenericResponse noContent() {
        return GenericResponseBuilder.getInstance().noContent().build();
    }

    public static GenericResponse serverError() {
        return GenericResponseBuilder.getInstance().serverError().build();
    }

    public static GenericResponse serverError(Object data) {
        return GenericResponseBuilder.getInstance().serverError(data).build();
    }

    public static GenericResponse serverResourceError(Object data, String status, Integer statusCode) {
        return GenericResponseBuilder.getInstance().serverResourceError(data, status, statusCode).build();
    }

}
