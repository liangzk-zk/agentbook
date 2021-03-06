/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.file.sercice.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.Icon;

import org.springframework.web.multipart.MultipartFile;

import com.ab.app.agentbook.file.sercice.FileService;
import com.ab.app.agentbook.file.util.FileUtil;
import com.ab.app.agentbook.file.util.ImageUtil;
import com.ab.app.agentbook.util.IPUtil;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年10月30日
*/
public class FileServiceImpl implements FileService{
    private String fileRootPath;
    
    public String getFileRootPath() {
        return fileRootPath;
    }

    public void setFileRootPath(String fileRootPath) {
        this.fileRootPath = fileRootPath;
    }

    @Override
    public String uploadQRcode(HttpServletRequest request,Long payType, MultipartFile file) {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        String ipaddr = IPUtil.getIpAddr(request);
        String contextPath = request.getContextPath();
        if(payType==0) {
            fileName = "WeChatQRcode.png";
        }else if(payType==1) {
            fileName = "ALIQRcode.png";
        }else if(payType==2) {
            fileName = "SERQRcode.png";
        }else if(payType==3) {
            fileName = "SYNOPSIS.ppt";
        }
        
        String path = fileRootPath +File.separator+"company"+File.separator+"qrcode"+File.separator+fileName;
        //创建文件路径
        File dest = new File(path);
        //判断文件是否已经存在
//        if (dest.exists()) {
//            return "文件已经存在";
//        }
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            //上传文件
            file.transferTo(dest); //保存文件
        } catch (IOException e) {
            return "上传失败";
        }
        path ="http://"+ipaddr+File.separator+contextPath+File.separator+path;
        return path.replace(fileRootPath, "static");
    }

	@Override
	public String uploadPaymentVoucher(HttpServletRequest request,MultipartFile file) {
		// 获取文件名
        String fileName = file.getOriginalFilename();
        String ipaddr = IPUtil.getIpAddr(request);
        String contextPath = request.getContextPath();
        String path = fileRootPath +File.separator+"paymentvoucher"+File.separator+fileName;
        //创建文件路径
        File dest = new File(path);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            //上传文件
            file.transferTo(dest); //保存文件
        } catch (IOException e) {
            return "上传失败";
        }
        path ="http://"+ipaddr+File.separator+contextPath+File.separator+path;
        return path.replace(fileRootPath, "static");
	}

    @Override
    public String updateCpmpayCarousel(HttpServletRequest request, MultipartFile file) {
     // 获取文件名
        String fileName = file.getOriginalFilename();
        //原文件名后缀
        String ipaddr = IPUtil.getIpAddr(request);
        String contextPath = request.getContextPath();
        String path = fileRootPath +File.separator+"carousel"+File.separator+fileName;
        //创建文件路径
        File dest = new File(path);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            Icon icon = ImageUtil.getFixedIcon(FileUtil.multipartFileToFile(file),path, 479, 180);
            path ="http://"+ipaddr+File.separator+contextPath+File.separator+icon;
        } catch (IOException e) {
        } catch (Exception e) {
            return "上传失败";
        }
        return path.replace(fileRootPath, "static");
    }

}
