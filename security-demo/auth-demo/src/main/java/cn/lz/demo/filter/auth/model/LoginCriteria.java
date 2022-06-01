package cn.lz.demo.filter.auth.model;

import cn.lz.tool.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/04 10:30
 */
public class LoginCriteria extends BaseModel {

	@ApiModelProperty(value = "用户名", required = true)
	private String username;

	@ApiModelProperty(value = "密码", required = true)
	private String pwd;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "LoginCriteria{" +
				"username='" + username + '\'' +
				", pwd='" + pwd + '\'' +
				'}';
	}
}
