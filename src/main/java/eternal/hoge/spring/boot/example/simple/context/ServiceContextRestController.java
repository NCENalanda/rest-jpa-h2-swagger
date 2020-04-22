package eternal.hoge.spring.boot.example.simple.context;


import eternal.hoge.spring.boot.example.simple.response.GenericResponse;
import lombok.val;
import org.json.simple.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("context")
@ApiIgnore
public class ServiceContextRestController {



    @GetMapping(value = "/getServiceContext")
    public GenericResponse getApplication() {
        val app = new JSONObject();
        app.put("serviceName", "reactive");
        return GenericResponse.ok(app).build();
    }
}
