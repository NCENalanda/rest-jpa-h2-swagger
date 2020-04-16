package eternal.hoge.spring.boot.example.simple.controller;

import eternal.hoge.spring.boot.example.simple.entity.Book;
import eternal.hoge.spring.boot.example.simple.entity.Document;
import eternal.hoge.spring.boot.example.simple.repository.IDocumentRepository;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("document")
@Api
@Slf4j
public class DocmentController {

    @Autowired
    private IDocumentRepository  documentRepository;

    @GetMapping("getDocuments")
    @ApiOperation(value = "Retrieve All books ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class,
            authorizations = {
                    @Authorization(value = "OAuth2Security", scopes = {
                            @AuthorizationScope(scope = "viewDocumentGet", description = "View API")
                    })
            }, tags={ "AWS Lambda (Individual)",  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Requested ARN List of the API is returned ", response = String.class),
            @ApiResponse(code = 404, message = "Not Found. Requested resource does not exist. ") })
    public List<Document> getListDocument(){
        return documentRepository.findAll();
    }



    @GetMapping("getDocumentsByUserId")
    @ApiOperation(value = "Retrieve All Documents by userId ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class,
            authorizations = {
                    @Authorization(value = "OAuth2Security", scopes = {
                            @AuthorizationScope(scope = "viewDocumentGet", description = "View API")
                    })
            }, tags={ "AWS Lambda (Individual)",  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Requested ARN List of the API is returned ", response = String.class),
            @ApiResponse(code = 404, message = "Not Found. Requested resource does not exist. ") })
    public List<Document> getListDocumentByUserId(@RequestParam(value = "userId",required = false) Long userId){
        log.info("userId  : "+userId);
        return documentRepository.findByUserId(userId);
    }




    @GetMapping("getDocument/{id}")
    @ApiOperation(value = "Retrieve  Document by  id ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
            @Authorization(value = "OAuth2Security", scopes = {
                    @AuthorizationScope(scope = "viewDocumentById", description = "View API")
            })
    }, tags={ "AWS Lambda (Individual)",  })
    public Document getDocument(@PathVariable("id") Long id){

        return documentRepository.findById(id).get();
    }



    @PostMapping("/createDocument")
    @ApiOperation(value = "create  Document ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
            @Authorization(value = "OAuth2Security", scopes = {
                    @AuthorizationScope(scope = "createDocument", description = "create API")
            })
    }, tags={ "AWS Lambda (Individual)",  })
    public  Document createDocument(@RequestBody Document document){
        return documentRepository.save(document);
    }

    @PostMapping("/createDocuments")
    @ApiOperation(value = "create  Documents ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
            @Authorization(value = "OAuth2Security", scopes = {
                    @AuthorizationScope(scope = "createDocuments", description = "create API")
            })
    }, tags={ "AWS Lambda (Individual)",  })
    public  List<Document> createDocument(@RequestBody List<Document> documents){
        return documentRepository.saveAll(documents);
    }

    @DeleteMapping("/deleteDocument")
    @ApiOperation(value = "remove Documents ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
            @Authorization(value = "OAuth2Security", scopes = {
                    @AuthorizationScope(scope = "deleteDocument", description = "delete API")
            })
    }, tags={ "AWS Lambda (Individual)",  })
    public  void deleteDocument(@RequestBody Document document){
        documentRepository.deleteById(document.getId());
    }
}
