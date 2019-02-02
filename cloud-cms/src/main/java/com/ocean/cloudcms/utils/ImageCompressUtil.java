package com.ocean.cloudcms.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 * @Author: chenhy
 * @Date: 2019-2-2
 * @Version 1.0
 */
public class ImageCompressUtil {

    /**
     * 图片压缩
     * @param bytes 文件转换的字节流
     * @param name  文件名
     * @param width 压缩后宽度
     * @param needWatermark 是否需要水印
     * @return
     * @throws Exception
     */
    public static String[] compress(byte[] bytes, String name, int width, boolean needWatermark) throws Exception {
      /*  File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath();
        File file = new File(courseFile, name);
        OutputStream output = new FileOutputStream(file);

        BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);

        bufferedOutput.write(bytes);
        bufferedOutput.close();
        output.close();


        //获取原图的高和宽
        int imgWidth = getImgWidth(file);
        int imgHeight = getImgHeight(file);

        //设置对应的高度
        int height = width * imgHeight / imgWidth;
        int index = name.lastIndexOf(".");
        String suffix = name.substring(index);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;

        File newFile = new File(courseFile, fileName);
        //设置水印
        String compressImage = setWatermark(needWatermark, courseFile, width, height, file, newFile);
        String normalImage = "";
        if (needWatermark) {
            String normalName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
            File normalFile = new File(courseFile, normalName);
            normalImage = setWatermark(needWatermark, courseFile, getImgWidth(file), getImgHeight(file), file, normalFile);
            normalFile.delete();
        } else {
            normalImage = getFastdfsUrl(file);
        }


        newFile.delete();

        file.delete();*/
        //return new String[]{normalImage, compressImage};
        return null;
    }


    /**
     * 设置水印
     */
    public static byte[] setWatermark(InputStream inputStream,boolean needWatermark, String courseFile, int width, int height) throws Exception {
        // 在内存当中加水印
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (needWatermark) {
            //获取水印图
            String groupName = "group1";
            String remoteFileName = "M00/00/00/rBMwl1xVEnSAW9EdAAAQK3b3kGc988.png";
            InputStream syInputStream = FastDFSClient.getInstance().downFile(groupName,remoteFileName);
            //File path = new File()
            File watermark = new File("F://sy.png");
            FileUtils.inputstreamtofile(syInputStream,watermark);
            double watermarkWidth = getImgWidth(watermark);
            double watermarkHeight = getImgHeight(watermark);
            //获取同样3/4大小的水印图
            File newWatermark = new File("F://qivay.png");
            double proportion = watermarkWidth / watermarkHeight;
            watermarkWidth = width * 3 / 8;
            watermarkHeight = new Double(watermarkWidth / proportion).intValue();
            if (height * 3 / 8< watermarkHeight) {
                watermarkHeight = height * 3 / 8;
                watermarkWidth = new Double(watermarkHeight * proportion).intValue();
            }
          /*  File oldFile = new File("F://yuan.png");
            FileUtils.inputstreamtofile(inputStream,oldFile);
            System.out.println(getImgHeight(oldFile));*/


            Thumbnails.of(watermark.getPath())
                    .forceSize(new Double(watermarkWidth).intValue(), new Double(watermarkHeight).intValue())
                    .outputQuality(0.8f)
                    .toFile(newWatermark.getPath());

            Thumbnails.of(inputStream)
                    .forceSize(width,height) //图片宽高
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(newWatermark), 0.5f)
                    .outputQuality(0.8f)
                    .toOutputStream(out);
            /*Thumbnails.of(watermark.getPath())
                    .forceSize(new Double(watermarkWidth).intValue(), new Double(watermarkHeight).intValue())
                    .outputQuality(0.8f)
                    .toFile(newWatermark.getPath());

            Thumbnails.of(file.getPath())
                    .forceSize(width, height)
                    .watermark(Positions.CENTER, ImageIO.read(newWatermark), 0.5f)
                    .outputQuality(0.8f)
                    .toFile(newFile.getPath());*/
            newWatermark.delete();
            watermark.delete();
        } else {
           /* Thumbnails.of(file.getPath())
                    .forceSize(width, height)
                    .outputQuality(0.8f)
                    .toFile(newFile.getPath());*/
           return null;
        }
        return out.toByteArray();
    }

    /**
     * 获取图片上传到fastdfs上的url
     *
     * @param newFile
     * @return
     * @throws IOException
     */
    private static String getFastdfsUrl(File newFile){
        /*StringBuilder sb = new StringBuilder();
        String[] ids = null;
        FileInputStream fileInputStream = new FileInputStream(newFile);
        byte[] fileBytes = new byte[0];
        try {
            fileBytes = FileUtils.file2byte(newFile);
            ids = helper.upload(fileBytes, newFile.getName());
            if (ids.length == 2) {
                sb.append("/" + ids[0] + "/");
                sb.append(ids[1]);
            }
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();*/
        return null;
    }


    /**
     * 压缩图片，根据上传得到的文件
     *
     * @param mf
     * @param width
     * @return
     * @throws Exception
     */
    public static String[] compressFile(MultipartFile mf, int width,  boolean needWatermark) throws Exception {
       /* byte[] bytes = mf.getBytes();
        return compress(bytes, mf.getOriginalFilename(), width, helper, needWatermark);*/
       return  null;
    }

    /**
     * 获取图片宽度
     *
     * @param file 图片文件
     * @return 宽度
     */
    public static int getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = ImageIO.read(is);
            ret = src.getWidth(); // 得到原图宽
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * 获取图片高度
     *
     * @param file 图片文件
     * @return 高度
     */
    public static int getImgHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getHeight(); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


}
