package com.loan_craft.upload.utils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

public class CloudinaryUpload {

    private Cloudinary cloudinary;
    private Dotenv dotenv;

    public CloudinaryUpload() {
        this.dotenv = Dotenv.load();
        this.cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));;
    }

    public String uploadPancard(MultipartFile pancardFile){
        try {
            System.out.println("cloud pan upload entered");
            Map params = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", true,
                    "overwrite", false,
                    "resource_type", "raw",
                    "folder", "loanCraft/pancard"
            );
            System.out.println("create cloud pan file");
            File convFile = File.createTempFile("upload-", ".pdf");
            System.out.println("could pan tranfer");
            pancardFile.transferTo(convFile);
            System.out.println("cloudn pan upoad end");
            Map uploadRes = cloudinary.uploader().upload(convFile, params);
            System.out.println("cloud pan upload finish");
            return (String) uploadRes.get("secure_url");
        }
        catch(Exception e){
            System.out.println("catch upload error pan");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String uploadSalarySlip(MultipartFile salarySlipFile){
        try {
            Map params = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", true,
                    "overwrite", false,
                    "resource_type", "raw",
                    "folder", "loanCraft/salarySlip"
            );
            File convFile = File.createTempFile("upload-", ".pdf");
            salarySlipFile.transferTo(convFile);
            Map uploadRes = cloudinary.uploader().upload(convFile, params);
            return (String) uploadRes.get("secure_url");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
