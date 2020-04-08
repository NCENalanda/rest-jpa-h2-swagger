package eternal.hoge.spring.boot.example.simple.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Component
@Order(1)
public class TransactionFilter  implements Filter {

    @Override
    public void doFilter(
    ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        log.info("Starting a transaction for req : {}", req.getRequestURI());

        Enumeration enumeration  = req.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String  string = (String) enumeration.nextElement();
            String  value  =  req.getHeader(string);
            log.info("request :: "+string+" : "+value);
        }

        chain.doFilter(request, response);
        HttpServletResponse  res  =  (HttpServletResponse)response;
         List<String> list =  (List<String>) res.getHeaderNames();
         for (String s:list){
             String  value  =  res.getHeader(s);
             log.info("response :: "+s+" : "+value);
         }
        log.info("Committing a transaction for req : {}", req.getRequestURI());
        log.info("____________________________________________________________________________________");
    }
}
