package com.ocean.fastdfs.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.ocean.fastdfs.service.IFastdfsClient;
import com.ocean.fastdfs.util.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "文件下载")
@RequestMapping("/fileDownLoad")
public class FastdfsDownLoadAct {
	private IFastdfsClient fastdfsClient;

	@ApiOperation(value = "文件下载")
	@RequestMapping(value="/downLoad",method=RequestMethod.GET)
	public void downLoad(@RequestParam(name = "filepath", required = true) String filepath,
			@RequestParam(name = "filename", required = true) String filename, HttpServletResponse response) {
		OutputStream out = null;
		InputStream is = null;
		try {
			out = response.getOutputStream();
			byte[] bytes = null;
			if(filepath.contains("group1")){
				bytes = fastdfsClient.download(filepath);
			}else{
				bytes = HttpUtil.getByte("http://file.caibab.com/upload/"+filepath, null);
			}
			response.setContentType("application/octet-stream;charset=UTF-8");
			// Content-Disposition
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));
			is = new ByteArrayInputStream(bytes);
			byte[] buffer = new byte[1024 * 5];
			int len = 0;
			while ((len = is.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			try {
				if (is != null) {
					is.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public IFastdfsClient getFastdfsClient() {
		return fastdfsClient;
	}

	public void setFastdfsClient(IFastdfsClient fastdfsClient) {
		this.fastdfsClient = fastdfsClient;
	}
	
	
	
}
