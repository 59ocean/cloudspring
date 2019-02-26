package com.ocean.cloudcms.interceptor;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.ocean.cloudcms.exception.ExceptionInfo;
import com.ocean.cloudcms.exception.RetCode;
import com.ocean.cloudcms.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;



@ControllerAdvice(basePackages="com.ocean.cloudcms")
public class GlobalExceptionHandler {
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/***
	 * 捕捉业务异常
	 * @param req
	 * @param res
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = ServiceException.class)
	@ResponseBody
	public ExceptionInfo jsonErrorHandler(HttpServletRequest req, HttpServletResponse res, ServiceException e) throws Exception {
		res.setStatus(500);
		ExceptionInfo exInfo=new ExceptionInfo();
		exInfo.setExceptionType(ExceptionInfo.BIZ_SERVICE_EXCEPTION);
		exInfo.setMsg(e.getMessage());
		exInfo.setRetCode(e.getRetCode());
		return exInfo;
		
	}
	
	/***
	 * 捕捉数据库异常
	 * @param req
	 * @param res
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = SQLException.class)
	@ResponseBody
	public ExceptionInfo jsonErrorHandler(HttpServletRequest req, HttpServletResponse res, SQLException e) throws Exception {
		res.setStatus(500);
		ExceptionInfo exInfo=new ExceptionInfo();
		exInfo.setExceptionType(ExceptionInfo.BIZ_SQL_EXCEPTION);
		exInfo.setMsg(e.getMessage());
		exInfo.setRetCode(RetCode.DB_EXCUTE_FAIL);
		return exInfo;
		
	}
	
	/***
	 * 捕捉数据库异常
	 * @param req
	 * @param res
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ExceptionInfo jsonErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) throws Exception {
		res.setStatus(500);
		ExceptionInfo exInfo=new ExceptionInfo();
		exInfo.setExceptionType(ExceptionInfo.BIZ_OTHER_EXCEPTION);
		exInfo.setMsg(e.getMessage());
		exInfo.setRetCode(RetCode.UNKNOW_EXCEPTION);
		return exInfo;
		
	}
	
	/***
	 * 参数验证捕捉
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = ValidationException.class)
	@ResponseBody
	public ExceptionInfo jsonErrorHandler(ValidationException exception) {
		ExceptionInfo exInfo = new ExceptionInfo();
		exInfo.setExceptionType(ExceptionInfo.BIZ_PARAM_VLD_EXCEPTION);
		exInfo.setRetCode(RetCode.PARAM_CHECK_FAIL);
		if (exception instanceof ConstraintViolationException) {
			ConstraintViolationException exs = (ConstraintViolationException) exception;
			Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
			StringBuffer msg = new StringBuffer();
			for (ConstraintViolation<?> item : violations) {
				/** 打印验证不通过的信息 */
				msg.append(item.getMessage() + ",");
			}
			exInfo.setMsg(msg.toString());
		} else {
			exInfo.setMsg("参数验证不通过");
		}

		return exInfo;
	}
	
	/***
	 * 参数验证捕捉
	 * 
	 * @param
	 * @return
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class,MissingServletRequestParameterException.class })
	@ResponseBody
	public ExceptionInfo jsonErrorHandler(Exception ex) {
		ExceptionInfo exInfo = new ExceptionInfo();
		exInfo.setExceptionType(ExceptionInfo.BIZ_PARAM_VLD_EXCEPTION);
		exInfo.setRetCode(RetCode.PARAM_CHECK_FAIL);
		String msg = "参数验证失败";
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
			msg = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		}
		if (ex instanceof MethodArgumentTypeMismatchException) {
			MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
			logger.error("参数转换失败,方法：" + exception.getParameter().getMethod().getName() + "，参数：" + exception.getName()
					+ ",msg：" + exception.getLocalizedMessage());
			msg = "参数转换失败"+exception.getMessage();
		}
		if (ex instanceof MissingServletRequestParameterException) {
			MissingServletRequestParameterException exception = (MissingServletRequestParameterException) ex;
			logger.error("请求参数失败,msg："+exception.getLocalizedMessage());
			msg = "请求参数失败"+exception.getMessage();
		}
		exInfo.setMsg(msg);
		return exInfo;
	}

}
