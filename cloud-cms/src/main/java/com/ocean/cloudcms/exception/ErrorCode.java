package com.ocean.cloudcms.exception;


public enum ErrorCode {

    FILE_PATH_ISNULL("error.fastdfs.file_path_isnull", "文件路径为空"),

    FILE_ISNULL("error.fastdfs.file_isnull", "文件为空"),
    
    FILE_SUFFIX_FAILED("error.fastdfs.file_suffix_error", "文件后缀有问题"),

    FILE_UPLOAD_FAILED("error.fastdfs.file_upload_failed", "文件上传失败"),

    FILE_NOT_EXIST("error.fastdfs.file_not_exist", "文件不存在"),

    FILE_DOWNLOAD_FAILED("error.fastdfs.file_download_failed", "文件下载失败"),

    FILE_DELETE_FAILED("error.fastdfs.file_delete_failed", "删除文件失败"),

    FILE_SERVER_CONNECTION_FAILED("error.fastdfs.file_server_connection_failed", "文件服务器连接失败"),

    FILE_OUT_SIZE("error.fastdfs.file_server_connection_failed", "文件超过大小"),
    
    FILE_ERROR_IMAGE_SCALE("error.file.image_scare","不支持的图片格式"),

    FILE_TYPE_ERROR_IMAGE("error.file.type.image", "图片类型错误");



    public String CODE;

    public String MESSAGE;

    ErrorCode(String CODE, String MESSAGE){
        this.CODE = CODE;
        this.MESSAGE = MESSAGE;
    }

}
