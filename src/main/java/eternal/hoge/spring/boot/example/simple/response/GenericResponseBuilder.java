package eternal.hoge.spring.boot.example.simple.response;

import eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil;
import lombok.Getter;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.StatusConstantNumber.*;

@Getter
public final class GenericResponseBuilder {

    private Object data;
    private Object message;
    private String status;
    private Integer statusCode;
    private String contentType;
    private Integer contentLength;
    private Integer previousPage;
    private Integer currentPage;
    private Integer nextPage;
    private Integer currentPageCount;
    private Integer page;
    private Integer pageSize;
    private Integer totalCount;
    private Integer totalPage;
    private boolean search = false;

    static GenericResponseBuilder getInstance() {
        return new GenericResponseBuilder();
    }

    public GenericResponseBuilder setData(Object data) {
        this.search = false;
        this.data = data;
        return this;
    }

    public GenericResponseBuilder setMessage(Object message) {
        this.message = message;
        return this;
    }

    public GenericResponseBuilder setSearchFilterData(Object data) {
        this.search = true;
        this.data = data;
        return this;
    }

    public GenericResponseBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public GenericResponseBuilder setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public GenericResponseBuilder setPage(Integer page) {
        if (page != null)
            this.page = page;
        return this;
    }

    public GenericResponseBuilder setPageSize(Integer pageSize) {
        if (this.page != null)
            this.pageSize = pageSize;
        return this;
    }

    public GenericResponseBuilder setTotalCount(Integer totalCount) {
        if (this.page != null)
            this.totalCount = totalCount;
        return this;
    }

    public GenericResponseBuilder setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public GenericResponseBuilder setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    GenericResponseBuilder noContent() {
        this.data = new ArrayList<>();
        this.status = CoreConstantUtil.ResponseStatus.NO_CONTENT;
        this.statusCode = NO_CONTENT;
        return this;
    }

    GenericResponseBuilder serverError() {
        getServerError("Oops! Something Went Wrong");
        return this;
    }

    GenericResponseBuilder serverError(Object data) {
        getServerError(data);
        return this;
    }

    private void getServerError(Object data) {

        this.data = "Oops! Something Went Wrong";
        if (!this.data.equals(data.toString()))
            this.message = data;
        this.status = CoreConstantUtil.ResponseStatus.INTERNAL_SERVER_ERROR;
        this.statusCode = INTERNAL_SERVER_ERROR;
        this.contentType = MediaType.TEXT_PLAIN_VALUE;
        this.contentLength = 1;
    }

    GenericResponseBuilder serverResourceError(Object data, String status, Integer statusCode) {
        this.data = data;
        this.status = status;
        this.statusCode = statusCode;
        this.contentType = MediaType.TEXT_PLAIN_VALUE;

        return this;
    }

    private void setOtherInfo() {
        this.contentType = MediaType.APPLICATION_JSON_VALUE;
        if (this.page != null)
            setPagination();
        else
            this.contentLength = ((List) this.data).size();
        if (this.status == null) {
            this.status = ((List) data).isEmpty() ? CoreConstantUtil.ResponseStatus.NO_CONTENT : CoreConstantUtil.ResponseStatus.SUCCESS;
        }
        if (this.statusCode == null) {
            this.statusCode = ((List) data).isEmpty() ? NO_CONTENT : OK;
        }
    }

    private void setPagination() {
        if (this.pageSize != null && this.pageSize > 0 && this.totalCount != null) {
            this.totalPage = (int) Math.ceil((double) this.totalCount / this.pageSize);
        }
        this.currentPageCount = ((List) this.data).size();
        this.previousPage = this.page == 0 ? 0 : this.page - 1;
        this.currentPage = this.page;
        this.nextPage = this.pageSize != null && this.currentPageCount >= this.pageSize ? this.page + 1 : 0;
    }

    private void setOtherInfo(Object data) {

        if (data != null) {
            if (data instanceof HashMap) {
                this.contentLength = ((HashMap) data).size();
            } else if (data instanceof List) {
                this.contentLength = ((List) data).size();
            } else if (data instanceof String) {
                this.contentLength = ((String) data).length();
            }


            if (this.contentType == null) {
                this.contentType = MediaType.APPLICATION_JSON_VALUE;
            }

            if (this.status == null && this.statusCode == null) {
                this.status = CoreConstantUtil.ResponseStatus.SUCCESS;
                this.statusCode = OK;
            }

            setStatusAndCode();
        }
    }

    private void setStatusAndCode() {
        if (this.statusCode == null) {
            setStatusCode();
        } else if (this.status == null) {
            setStatus();
        }
    }

    private void setStatusCode() {
        switch (this.status) {
            case CoreConstantUtil.ResponseStatus.SUCCESS:
                this.statusCode = OK;
                break;
            case CoreConstantUtil.ResponseStatus.NO_CONTENT:
                this.statusCode = NO_CONTENT;
                break;
            case CoreConstantUtil.ResponseStatus.NOT_FOUND:
                this.statusCode = NOT_FOUND;
                break;
            case CoreConstantUtil.ResponseStatus.INTERNAL_SERVER_ERROR:
                this.statusCode = INTERNAL_SERVER_ERROR;
                break;
            case CoreConstantUtil.ResponseStatus.NOT_IMPLEMENTED_SERVER_ERROR:
                this.statusCode = NOT_IMPLEMENTED_SERVER_ERROR;
                break;
            case CoreConstantUtil.ResponseStatus.SERVICE_UNAVAILABLE:
                this.statusCode = SERVICE_UNAVAILABLE;
                break;
            default:
                // do nothing
        }
    }

    private void setStatus() {

        switch (String.valueOf(this.statusCode)) {
            case "200":
                this.status = CoreConstantUtil.ResponseStatus.SUCCESS;
                break;
            case "204":
                this.status = CoreConstantUtil.ResponseStatus.NO_CONTENT;
                break;
            case "404":
                this.status = CoreConstantUtil.ResponseStatus.NOT_FOUND;
                break;
            case "500":
                this.status = CoreConstantUtil.ResponseStatus.INTERNAL_SERVER_ERROR;
                break;
            case "501":
                this.status = CoreConstantUtil.ResponseStatus.NOT_IMPLEMENTED_SERVER_ERROR;
                break;
            case "503":
                this.status = CoreConstantUtil.ResponseStatus.SERVICE_UNAVAILABLE;
                break;
            default:
                // do nothing
        }
    }

    public GenericResponse build() {
        if (search) {
            setOtherInfo();
        } else {
            setOtherInfo(data);
        }
        return new GenericResponse(this);
    }
}
