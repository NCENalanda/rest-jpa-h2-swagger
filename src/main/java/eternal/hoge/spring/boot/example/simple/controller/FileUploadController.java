package eternal.hoge.spring.boot.example.simple.controller;

import io.micrometer.core.instrument.util.IOUtils;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.enums.ParameterIn;
//import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


//import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("File")
//@Tag(name = "items")
public class FileUploadController {


    private  static Logger log   = LoggerFactory.getLogger(FileUploadController.class);
    private static String UPLOADED_FOLDER =  "";
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestPart("file") MultipartFile file) {

        if (file.isEmpty()) {
            //redirectAttributes.addFlashAttribute("message", "Please select a file to upload");

            log.info("file isEmpty ");
            return "Please select a file to upload";
        }

        try {

            // Get the file and save it somewhere
            log.info("file uploaded processing");
            byte[] bytes = file.getBytes();
            log.info("File size : "+file.getSize());
            log.info("UPLOADED_FOLDER"+file.getOriginalFilename());
            log.info("UPLOADED_FOLDER"+UPLOADED_FOLDER);

            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            log.error("error "+e.getMessage()+"  class "+e.getClass().getName());
            e.printStackTrace();
            return "file uploaded unsuccessfully";
        }

        log.info("file uploaded successfully");
        return "file uploaded successfully";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    @GetMapping("readFile")
    public  String  readUploadedFile2(@RequestParam("fileName") String fileName){
        try {
            fileName = UPLOADED_FOLDER+fileName;
            log.info("fileName : "+fileName);
            // log.info("json  : "+json);
            File initialFile = new File(fileName);
            InputStream targetStream = new FileInputStream(initialFile);
            String str = IOUtils.toString(targetStream,UTF_8);
            log.info("str  : "+str);
            return  str;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
