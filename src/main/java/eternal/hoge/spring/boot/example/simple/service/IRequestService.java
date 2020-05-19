package eternal.hoge.spring.boot.example.simple.service;


import eternal.hoge.spring.boot.example.simple.response.GenericResponse;
import eternal.hoge.spring.boot.example.simple.utils.NvRequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IRequestService {
    GenericResponse listRequest(List<NvRequestParam> listRequest, HttpServletRequest httpServletRequest);
}
