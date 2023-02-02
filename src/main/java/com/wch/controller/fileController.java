package com.wch.controller;
import com.wch.base.BaseController;
import com.wch.pojo.Item;
import com.wch.utils.SystemContext;
import com.wch.utils.UUIDUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@RestController
public class fileController extends BaseController{
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile[] file) throws IOException {
        System.out.println("文件");
        System.out.println(file);
        for (MultipartFile multipartFile : file) {
            System.out.println("进入");
            String originalFilename = multipartFile.getOriginalFilename();//获取文件的名字
            multipartFile.transferTo(new File("D:\\JAVACX\\OnlineFruitStore\\src\\main\\webapp\\res\\upload\\" + originalFilename));
            System.out.println(originalFilename);
        }
    }

    @RequestMapping(value = "upload2",method = RequestMethod.POST)
    public String uploa2(MultipartFile file, HttpServletRequest request) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());

        // uploads文件夹位置
        String rootPath = request.getSession().getServletContext().getRealPath("res/upload/"+file.getOriginalFilename());
        // 原始名称
        String originalFileName = file.getOriginalFilename();
        // 新文件名
        String newFileName = "sliver" + res + originalFileName.substring(originalFileName.lastIndexOf("."));
        // 创建年月文件夹
        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH)+1));

         //新文件
        File newFile = new File(rootPath + File.separator + dateDirs + File.separator + newFileName);
         //判断目标文件所在目录是否存在
        if( !newFile.getParentFile().exists()) {
            // 如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }
        System.out.println(newFile);
        // 将内存中的数据写入磁盘
        file.transferTo(newFile);
        // 完整的url
        String fileUrl = date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH)+1) + "/" + newFileName;
        return  fileUrl;
    }

    @RequestMapping(value = "upload3",method = RequestMethod.POST)
    public void upload3(MultipartFile file) throws IOException {
        System.out.println("文件");
        System.out.println(file);
        String originalFilename = file.getOriginalFilename();//获取文件的名字
        file.transferTo(new File("D:\\JAVACX\\OnlineFruitStore\\src\\main\\webapp\\res\\upload\\" + originalFilename));
        System.out.println(originalFilename);
        }


    @RequestMapping(value = "upload1",method = RequestMethod.POST)
    private String upload1(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println("进入");
        System.out.println(file);
        String n= UUIDUtils.create();
        String path= SystemContext.getRealPath()+"\\res\\upload\\"+n+ file.getOriginalFilename();
        //String path="D:\\JAVACX\\OnlineFruitStore\\src\\main\\webapp\\res\\upload\\"+n+ file.getOriginalFilename();//设置文件保存的路径
        file.transferTo(new File(path));//将上传文件写到服务器上指定的文件
        System.out.println("路径");
        System.out.println(request.getContextPath()+"\\res\\upload\\"+n+ file.getOriginalFilename());
        return request.getContextPath()+"\\res\\upload\\"+n+ file.getOriginalFilename();//返回相对路径
    }

}

