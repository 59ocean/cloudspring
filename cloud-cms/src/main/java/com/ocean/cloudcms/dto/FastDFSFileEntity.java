package com.ocean.cloudcms.dto;

/**
 * @Author: maoqitian
 * @Date: 2018/10/26 0026 17:57
 * @Description: FastDFS 文件类
 */
 public class FastDFSFileEntity {
 //文件名称
 private String name;
 //内容
 private byte[] content;
 //文件类型
 private String ext;
 //md5值
 private String md5;
 //作者
 private String author;
 public FastDFSFileEntity(String name, byte[] content, String ext, String height,
                  String width, String author) {
   super();
   this.name = name;
   this.content = content;
   this.ext = ext;
   this.author = author;
  }

  public FastDFSFileEntity(String name, byte[] content, String ext) {
   super();
   this.name = name;
   this.content = content;
   this.ext = ext;
   }

   public String getName() {
   return name;
   }

   public void setName(String name) {
   this.name = name;
   }

   public byte[] getContent() {
   return content;
   }

   public void setContent(byte[] content) {
   this.content = content;
   }

   public String getExt() {
   return ext;
   }

   public void setExt(String ext) {
   this.ext = ext;
   }

   public String getMd5() {
   return md5;
   }

   public void setMd5(String md5) {
   this.md5 = md5;
   }

   public String getAuthor() {
   return author;
   }

   public void setAuthor(String author) {
   this.author = author;
   }
  }