package com.whyrkj.demo.controller;

import com.alibaba.fastjson.JSON;
import com.whyrkj.demo.config.UploadProperties;
import com.whyrkj.demo.config.model.FileUploadResponse;
import com.whyrkj.demo.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangsanfeng
 * @date 2019/6/18 10:50
 */
@RestController
public class FileUploadController {

    private Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @Resource
    private ResourceLoader resourceLoader;

    @Resource
    private UploadProperties uploadProperties;


    /**
     * @param files
     * @param request
     * @return String    返回类型
     * @Title: upload
     * @Description: TODO(上传)
     */
    @PostMapping("/upload")
    public List<FileUploadResponse> upload(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
        List<FileUploadResponse> list = new ArrayList<FileUploadResponse>();
        // 获取文件存放路径
        String path = uploadProperties.getBasePath() + DateUtil.format(new Date(), "yyyyMMdd") + "/";
        // 判断文件夹是否存在，不存在则创建路径
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        for (MultipartFile file : files) {
            FileUploadResponse rs = new FileUploadResponse();
            String contentType = file.getContentType();
            String originalFilename = file.getOriginalFilename();
            String fileName = String.valueOf(System.currentTimeMillis()) + originalFilename.substring(originalFilename.lastIndexOf("."));
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(path + fileName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
                rs.setContentType(contentType);
                rs.setFileName(fileName);
                if (path.contains("/var/local")) {
                    String str = "/var/local";
                    int index = path.indexOf("/var/local") + str.length();
                    System.out.println(index);
                    path = path.substring(index, path.length());
                }
                rs.setUrl(path + fileName);
                rs.setType("success");
                rs.setMsg("文件上传成功");
                log.info("文件上传成功:" + JSON.toJSONString(rs));
            } catch (Exception e) {
                rs.setType("fail");
                rs.setMsg("文件上传失败");
                log.error("上传文件失败:" + e);
            }
            list.add(rs);
        }
        return list;
    }

    /**
     * @param date
     * @param filename
     * @return
     * @Description: 显示图片
     */
    @GetMapping(value = "/system/uploadFile/{date}/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable String date, @PathVariable String filename) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(uploadProperties.getBasePath()
                    + File.separator + date, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param fileName
     * @return ResponseEntity<?>    返回类型
     * @Title: download
     * @Description: 下载
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String fileName)
            throws IOException {
        String filePath =  uploadProperties.getBasePath() + fileName;
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }


}
