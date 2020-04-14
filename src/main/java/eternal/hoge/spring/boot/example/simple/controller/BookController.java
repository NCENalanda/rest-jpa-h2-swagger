package eternal.hoge.spring.boot.example.simple.controller;

import eternal.hoge.spring.boot.example.simple.entity.Book;
import eternal.hoge.spring.boot.example.simple.repository.IBookRepository;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/book")
@Slf4j
@Api
public class BookController {

    @Autowired
    private IBookRepository iBookRepository;

    @GetMapping("getBooks")
    @ApiOperation(value = "Retrieve All books ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class,
            authorizations = {
            @Authorization(value = "OAuth2Security", scopes = {
                    @AuthorizationScope(scope = "viewBookGet", description = "View API")
            })
    }, tags={ "AWS Lambda (Individual)",  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Requested ARN List of the API is returned ", response = String.class),
            @ApiResponse(code = 404, message = "Not Found. Requested resource does not exist. ") })
    public  List<Book>  getListBook(){
        return iBookRepository.findAll();
    }


    @PostMapping("getBooks")
    @ApiOperation(value = "Retrieve All books ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
            @Authorization(value = "OAuth2Security", scopes = {
                    @AuthorizationScope(scope = "viewBookPost", description = "View API")
            })
    }, tags={ "AWS Lambda (Individual)",  })
    public  List<Book>  getListBook1(){
        return iBookRepository.findAll();
    }

    @GetMapping("getBook/{id}")
    @ApiOperation(value = "Retrieve  book by  id ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
            @Authorization(value = "OAuth2Security", scopes = {
                    @AuthorizationScope(scope = "viewBookById", description = "View API")
            })
    }, tags={ "AWS Lambda (Individual)",  })
    public Book getBook(@PathVariable("id") Long id){

        return iBookRepository.findById(id).get();
    }

    @ApiIgnore
    @GetMapping("searchBook")
//    @ApiOperation(value = "search  book ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
//            @Authorization(value = "OAuth2Security", scopes = {
//                    @AuthorizationScope(scope = "searchBook", description = "View API")
//            })
//    }, tags={ "AWS Lambda (Individual)",  })
    //public List searchBook(@RequestParam("name") String name,@RequestParam("author") String author,@RequestParam("price") Double price){

    public List searchBook(@RequestParam(value = "name",required = false) String name,@RequestParam(value = "author",required = false) String author,@RequestParam(value = "price",required = false) Double price){
        if(name!=null)
        return iBookRepository.findByName(name);
        if(author!=null)
            return iBookRepository.findByAuthor(author);
        if(author!=null)
            return iBookRepository.findByPrice(price);
        else return  new ArrayList();
    }

    @PostMapping("/createBook")
    @ApiOperation(value = "create  book ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
            @Authorization(value = "OAuth2Security", scopes = {
                    @AuthorizationScope(scope = "createBook", description = "View API")
            })
    }, tags={ "AWS Lambda (Individual)",  })
    public  Book createBook(@RequestBody Book book){
        return iBookRepository.save(book);
    }

    @PostMapping("/createBooks")
    @ApiOperation(value = "create  books ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
            @Authorization(value = "OAuth2Security", scopes = {
                    @AuthorizationScope(scope = "createBooks", description = "View API")
            })
    }, tags={ "AWS Lambda (Individual)",  })
    public  List<Book> createBook(@RequestBody List<Book> books){
        return iBookRepository.saveAll(books);
    }

    @DeleteMapping("/deleteBook")
    @ApiOperation(value = "Retrieve All books ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
            @Authorization(value = "OAuth2Security", scopes = {
                    @AuthorizationScope(scope = "deleteBook", description = "View API")
            })
    }, tags={ "AWS Lambda (Individual)",  })
    public  void deleteBook(@RequestBody Book book){
         iBookRepository.deleteById(book.getId());
    }
}
