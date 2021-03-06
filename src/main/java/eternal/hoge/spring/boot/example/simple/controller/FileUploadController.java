package eternal.hoge.spring.boot.example.simple.controller;


//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.enums.ParameterIn;
//import io.swagger.v3.oas.annotations.media.Schema;
import eternal.hoge.spring.boot.example.simple.exception.BusinessException;
import io.micrometer.core.instrument.util.IOUtils;
//import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


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

    @DeleteMapping("/delete")
    public void singleFileDelete(@PathVariable("file") String file) throws IOException {

        log.info(" singleFileDelete  ");
        delete(file);
    }

    private void delete(String file) throws IOException{

        Path path = Paths.get(UPLOADED_FOLDER+file);

        boolean isDeleted = Files.deleteIfExists(path);
        if(isDeleted) {
            log.info("File deleted successfully");
        } else {
            log.info("File doesn't exist");
        }
    }

    @GetMapping("/list")
    public void listAllFiles() {

        log.info(" listAllFiles  ");
        showList(UPLOADED_FOLDER);
    }

    private  void showList(String directoryName){
        File directory = new File(directoryName);

        File[] fList = directory.listFiles();

        for (File file : fList){
            if (file.isFile()){
                log.info(file.getAbsolutePath());
            }
        }

    }

    @PostMapping("/createPost")
    @Consumes("multipart/form-data")
    public String createPost(@RequestParam(required = false, name = "trbpost") String trbpost,
                             @RequestParam(required = false, name = "fileData") List<MultipartFile> files,
                             @RequestParam(required = false, name = "fileNames") List<String> fileNames) throws BusinessException {
        return null;
    }


    @GetMapping("/csv")
    @Produces("text/plain")
    public String csvResponse(){
        log.info("  defaultget() post ");

        SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aa");
        //Setting the time zone
        dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
        List list = Arrays.asList("Content-Type: text/html;charset=ISO-8859-1",dateTimeInGMT.format(new Date()));


        String str  =  list.toString();
        str = str.replace("[","");
        str = str.replace("]","");

        List l1  = Arrays.asList("1","2","3");
        List l2  = Arrays.asList("a","b","c");
        String str1  =  l1.toString();
        str1 =  str1.replace("[","");
        str1 =  str1.replace("]","");

        str = str+"<br>";
        str = str+str1.toString();

        str1  =  l2.toString();
        str1 =  str1.replace("[","");
        str1 =  str1.replace("]","");

        str = str+"<br>";
        str = str+str1.toString();
        return str;



    }

}
