package eternal.hoge.spring.boot.example.simple.controller;

import eternal.hoge.spring.boot.example.simple.response.GenericResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("doc")
public interface IDocumentRest {

    @ApiOperation(value = "Upload multipartFiles by Ticket ", notes = "Upload list of files by ticketId")
    @PostMapping("/uploadFilesbyTicketId")
    ResponseEntity<GenericResponse> uploadFilesbyTicketId(@RequestParam(value = "level", required = false) String level, @RequestParam(value = "ticketId", required = false) String ticketId, @RequestParam(value = "file") List<MultipartFile> multipartFiles);

    @ApiOperation(value = "Upload multipartFiles", notes = "Upload list of files")
    @PostMapping("/uploadFiles")
    ResponseEntity<GenericResponse> uploadFiles(@RequestParam(value = "level") String level, @RequestParam(value = "file") List<MultipartFile> multipartFiles);

    @ApiOperation(value = "Download attachments", notes = "download attachment")
    @GetMapping("/downloadAttachement")
    Object downloadAttachement( @RequestParam(value = "documentId") String documentId, @RequestParam(value = "documentName") String documentName, HttpServletRequest request);
}

