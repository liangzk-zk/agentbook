/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.file.util;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.springframework.web.multipart.MultipartFile;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年11月23日
*/
public class ImageUtil {
    /**
     * 按输入的任意宽高改变图片的大小
     * @param filePath
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    public static Icon getFixedIcon(File file,String filePath, int width, int height) throws Exception{
        BufferedImage bi = ImageIO.read(file);
        double wRatio = (new Integer(width)).doubleValue() / bi.getWidth(); //宽度的比例
        double hRatio = (new Integer(height)).doubleValue() / bi.getHeight(); //高度的比例
        Image image = bi.getScaledInstance(width,height,Image.SCALE_SMOOTH); //设置图像的缩放大小
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(wRatio,hRatio),null);   //设置图像的缩放比例
        image = op.filter(bi,null);
        File zoomFile = new File(filePath);
        Icon ret = null;
        try{
            ImageIO.write((BufferedImage)image, "png", zoomFile);
            ret = new ImageIcon(zoomFile.getPath());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}
