package com.mycompany.controller;

import com.mycompany.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import static com.mycompany.service.HelperClass.*;



@Controller
public class FileUploadController {

    private static final String CSV_UPLOADED_FILE_PATH = "src/main/resources/files/newUser.csv";
    private static final String CSV_USERS_FILE_PATH = "src/main/resources/files/users.csv";

    @GetMapping("/fileUpload")
    public String showFileUploadForm() {
        return "fileUpload";
    }

    @PostMapping("/fileUpload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        if(file.isEmpty()){
            return "/failedFileUpload";
        }
        Path path = Paths.get(CSV_UPLOADED_FILE_PATH);
        try{
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<User> users = parseCSVtoUsers(CSV_UPLOADED_FILE_PATH);
        if (isExists(users.get(0), CSV_USERS_FILE_PATH)) {
            return "failedSubmit";
        }
        writeUserToCSV(users.get(0), CSV_USERS_FILE_PATH);
        deleteFile(path);
        model.addAttribute("file", file);
        return "successFileUpload";
    }


    public void deleteFile(Path path){
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


