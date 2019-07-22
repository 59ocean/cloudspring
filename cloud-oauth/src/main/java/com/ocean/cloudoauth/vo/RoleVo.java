package com.ocean.cloudoauth.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author chenhy
 * @date @time 2019/7/18 15:28
 */
@Data
@Accessors
public class RoleVo {
	private Long roleId;

	private String roleName;

	private String roleSign;

	private String remark;

	private Long userIdCreate;

	private Date gmtCreate;

	private Date gmtModified;
	/**
	 * 角色code用于springsecurity角色标识码
	 */
	private String roleCode;


}