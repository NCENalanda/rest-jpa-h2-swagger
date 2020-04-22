package eternal.hoge.spring.boot.example.simple.generic;

import eternal.hoge.spring.boot.example.simple.response.GenericResponse;


public interface ISearchFilterService {

    GenericResponse search(Class entity, String search, String orderBy, String orderType, Integer page, Integer size);

    void addCriteria(String extraCriteria);
}
