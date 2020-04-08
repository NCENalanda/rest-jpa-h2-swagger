package eternal.hoge.spring.boot.example.simple.controller;

import eternal.hoge.spring.boot.example.simple.entity.Book;
import eternal.hoge.spring.boot.example.simple.repository.IBookRepository;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/book")
@Slf4j
@Api
public class BookController {

    @Autowired
    private IBookRepository iBookRepository;

    @GetMapping("getBooks")
    @ApiOperation(value = "getBooks", nickname = "getBooks", notes = "", authorizations = {
            @Authorization(value = "default", scopes = {
                    @AuthorizationScope(scope = "TELE", description = "for telestra")
            })    }, tags={  })
    public  List<Book>  getListBook(){
        return iBookRepository.findAll();
    }

    @PostMapping("getBooks")
    @ApiOperation(value = "getBooks", nickname = "getBooks", notes = "", authorizations = {
            @Authorization(value = "default", scopes = {
                    @AuthorizationScope(scope = "INNO-TECHM", description = "for both"),
                    @AuthorizationScope(scope = "TELE", description = "for telestra"),
                    @AuthorizationScope(scope = "TECHM", description = "for  techm"),
                    @AuthorizationScope(scope = "innoeye", description = "only for  innoeye")
            })    }, tags={  })
    public  List<Book>  getListBook1(){
        return iBookRepository.findAll();
    }

    @GetMapping("getBook/{id}")
    @ApiOperation(value = "getBook/{id}", nickname = "getBook/{id}", notes = "", authorizations = {
            @Authorization(value = "default", scopes = {
                    @AuthorizationScope(scope = "INNO-TECHM", description = "for both"),
                    @AuthorizationScope(scope = "TELE", description = "for telestra"),
                    @AuthorizationScope(scope = "TECHM", description = "for  techm"),
                    @AuthorizationScope(scope = "innoeye", description = "only for  innoeye")
            })    }, tags={  })
    public Book getBook(@PathVariable("id") Long id){

        return iBookRepository.findById(id).get();
    }

    @PostMapping("/createBook")
    @ApiOperation(value = "createBook", nickname = "createBook", notes = "", authorizations = {
            @Authorization(value = "default", scopes = {
                    @AuthorizationScope(scope = "INNO-TECHM", description = "for both"),
                    @AuthorizationScope(scope = "TELE", description = "for telestra"),
                    @AuthorizationScope(scope = "TECHM", description = "for  techm"),
                    @AuthorizationScope(scope = "innoeye", description = "only for  innoeye")
            })    }, tags={  })
    public  Book createBook(@RequestBody Book book){
        return iBookRepository.save(book);
    }

    @PostMapping("/createBooks")
    @ApiOperation(value = "createBooks", nickname = "createBooks", notes = "", authorizations = {
            @Authorization(value = "default", scopes = {
                    @AuthorizationScope(scope = "INNO-TECHM", description = "for both"),
                    @AuthorizationScope(scope = "TELE", description = "for telestra"),
                    @AuthorizationScope(scope = "TECHM", description = "for  techm"),
                    @AuthorizationScope(scope = "innoeye", description = "only for  innoeye")
            })    }, tags={  })
    public  List<Book> createBook(@RequestBody List<Book> books){
        return iBookRepository.saveAll(books);
    }

    @DeleteMapping("/deleteBook")
    @ApiOperation(value = "deleteBook", nickname = "deleteBook", notes = "", authorizations = {
            @Authorization(value = "default", scopes = {
                    @AuthorizationScope(scope = "INNO-TECHM", description = "for both"),
                    @AuthorizationScope(scope = "TELE", description = "for telestra"),
                    @AuthorizationScope(scope = "TECHM", description = "for  techm"),
                    @AuthorizationScope(scope = "innoeye", description = "only for  innoeye")
            })    }, tags={  })
    public  void deleteBook(@RequestBody Book book){
         iBookRepository.deleteById(book.getId());
    }
}
