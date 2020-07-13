package com.superzmz.www.action;

import com.superzmz.www.bean.Photo;
import com.superzmz.www.bean.User;
import com.superzmz.www.service.UploadService;
import com.superzmz.www.tool.FormVerifyUtils;;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Scope("prototype")
public class UploadAction {

    @Resource
    UploadService uploadService ;

    @RequestMapping("/uploadUI.action")
    public String uploadUI(){

        return "upload/uploadUI";
    }

    @RequestMapping("/upload_list.action")
    public String list(Map map){

        List<Photo> list = uploadService.findAll();
        map.put("photoList", list);

        return "upload/list";
    }

    @RequestMapping("/upload_list_own.action")
    public String ownlist(Map map,HttpSession session){
        User currentUser = (User) session.getAttribute("user");

        List<Photo> list = uploadService.findOwnPhoto(currentUser.getId());
        map.put("photoList", list);

        return "upload/list";
    }

    @RequestMapping("/photo_delete.action")
    public String delete(Long id){
        //ɾ���ļ�
        Photo photo = uploadService.getById(id);
        String url = photo.getUrl();
        File file = new File(url);
        if (file.exists()) {
            boolean flag = file.delete();
            System.out.println("delete photo "+flag);
        }
        //ɾ������
        uploadService.delete(id);
        return "redirect:/upload_list.action";
    }


    @RequestMapping("/upload.action")
    public String upload(MultipartFile file, HttpSession session,Map map) throws IOException {

        if(!FormVerifyUtils.checkUpload(file)){
            map.put("error", "��������");
            return "upload/uploadUI";
        }

        User currentUser = (User) session.getAttribute("user");
        String name = file.getName();
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        String urlFolder = session.getServletContext().getContextPath()+"/upload/";
        String destFolder = session.getServletContext().getRealPath("/upload/");

        String fileName = UUID.randomUUID().toString();

        String fileDest = destFolder + "\\" + fileName + suffix;
        String imageUrl = urlFolder + fileName + suffix;

        System.out.println("�ļ���Ϣ:"+file.getOriginalFilename()+" "+file.getContentType());
        // �ϴ�
        FileUtils.copyInputStreamToFile(file.getInputStream(),new File(fileDest));

        Photo photo = new Photo();
        photo.setName(name);
        photo.setUserId(currentUser.getId());
        photo.setUsername(currentUser.getName());
        photo.setUrl(imageUrl);

        uploadService.save(photo);

        return "redirect:/upload_list.action";
    }


}
