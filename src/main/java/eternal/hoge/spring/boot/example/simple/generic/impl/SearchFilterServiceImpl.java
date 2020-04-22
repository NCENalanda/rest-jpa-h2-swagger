package eternal.hoge.spring.boot.example.simple.generic.impl;


import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;
import eternal.hoge.spring.boot.example.simple.generic.ISearchFilterService;
import eternal.hoge.spring.boot.example.simple.response.GenericResponse;
import eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil;
import eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ResponseKey;
import eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.StatusConstantNumber;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ResponseStatus.INTERNAL_SERVER_ERROR;
import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ValueConstant.EXCEPTION_LABEL_TAG;
import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ValueConstant.INSIDE_METHOD;


@SuppressWarnings("PlaceholderCountMatchesArgumentCount")
@Service
@Slf4j
class SearchFilterServiceImpl implements ISearchFilterService {

    @PersistenceContext
    private EntityManager em;

    private String extraCriteria = null;



    private JSONObject searchByFilter(Class entity, String search, String orderBy, String orderType, Integer page, Integer size) {
        val searchByFilter = "searchByFilter";

        log.trace(INSIDE_METHOD);
        val queryObject = new JSONObject();
        try {
            val builder = em.getCriteriaBuilder();
            val searchCriteria = builder.createQuery(entity);
            val countCriteria = builder.createQuery(Long.class);
            val root = searchCriteria.from(entity);
            val visitor = new JpaPredicateVisitor().defineRoot(root);
            val countVisitor = new JpaPredicateVisitor().defineRoot(countCriteria.from(entity));

            getRootNode(search, builder, searchCriteria, countCriteria, root, visitor, countVisitor);

            getOrderList(orderBy, orderType, builder, searchCriteria, root);

            val query = em.createQuery(searchCriteria);
            val queryCount = em.createQuery(countCriteria);
            queryObject.put(ResponseKey.COUNT, queryCount.getSingleResult());

            if (page != null && size != null && size > 0) {
                query.setFirstResult(size * page).setMaxResults((size * (page + 1)) - (size * page));
            }
            if (size != null && size > 0)
                queryObject.put(ResponseKey.DATA, query.getResultList());
            else
                queryObject.put(ResponseKey.DATA, Collections.singletonList("Count Data"));

            queryObject.put(ResponseKey.ERROR, false);

            return queryObject;
        } catch (Exception e) {
            log.error("Exception occurred in the processing of data", e);
            queryObject.put(ResponseKey.ERROR, true);
            queryObject.put(ResponseKey.DATA, e.getMessage());

            return queryObject;
        }
    }

    private void getOrderList(String orderBy, String orderType,
                              final CriteriaBuilder builder,
                              final CriteriaQuery searchCriteria, final Root root) {
        orderType = orderType != null ? orderType.toLowerCase() : "desc";
        val orderList = new ArrayList<>();

        List<String> orderByLists = new ArrayList<>();

        if (orderBy != null)
            orderByLists = Arrays.asList(orderBy.split(","));


        for (val item : orderByLists) {
            if (orderType.equals("desc")) {
                orderList.add(builder.desc(root.get(item)));
            } else {
                orderList.add(builder.asc(root.get(item)));
            }
        }
        searchCriteria.orderBy(orderList);
    }

    private void getRootNode(String search, final CriteriaBuilder builder,
                             final CriteriaQuery searchCriteria,
                             final CriteriaQuery<Long> countCriteria,
                             final Root root, final JpaPredicateVisitor visitor,
                             final JpaPredicateVisitor countVisitor) {
        Node rootNode;

        if (search != null && !search.isEmpty()) {
            rootNode = new RSQLParser().parse(search);
            // Visit the node to retrieve CriteriaQuery
            val predicate = rootNode.accept(visitor, em);
            val countPredicate = rootNode.accept(countVisitor, em);

            countCriteria.select(builder.count(root));

            // Use generated predicate as you like
            searchCriteria.where((Expression<Boolean>) predicate);
            countCriteria.where((Expression<Boolean>) countPredicate);
        }
    }

    @Override
    public void addCriteria(String extraCriteria) {
        this.extraCriteria = extraCriteria;
    }

    private String createCriteriaFiql(String search) {
        if (this.extraCriteria != null) {
            if (search == null) {
                search = "";
            }
            search = search + this.extraCriteria;
        }
        return search;
    }


    @Override
    public GenericResponse search(Class entity, String search, String orderBy, String orderType, Integer page, Integer size) {
        log.trace(INSIDE_METHOD);
        JSONObject data;

        search = createCriteriaFiql(search);
        this.extraCriteria = null;
        if (page == null && size == null) {
            page = CoreConstantUtil.ConstantNumber.ZERO;
            size = CoreConstantUtil.ConstantNumber.ONE_TWENTY;
        }
        data = searchByFilter(entity, search, orderBy, orderType, page, size);

        if (Boolean.parseBoolean(data.get(ResponseKey.ERROR).toString()))
            return GenericResponse.ok()
                    .setSearchFilterData(Collections.singletonList(data.get(ResponseKey.DATA)))
                    .setStatus(INTERNAL_SERVER_ERROR)
                    .setStatusCode(StatusConstantNumber.INTERNAL_SERVER_ERROR)
                    .setPage(page)
                    .build();

        val totalCount = (Long) data.get(ResponseKey.COUNT);
        return GenericResponse.ok()
                .setSearchFilterData(data.get(ResponseKey.DATA))
                .setPage(page)
                .setTotalCount(totalCount.intValue())
                .setPageSize(size)
                .build();
    }
}
