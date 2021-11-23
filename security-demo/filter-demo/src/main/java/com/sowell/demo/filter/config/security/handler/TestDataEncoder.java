package com.sowell.demo.filter.config.security.handler;

import com.sowell.security.defaults.DefaultDataEncoder;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/10 16:43
 */
@Component
public class TestDataEncoder extends DefaultDataEncoder {

	@Override
	public String encrypt(byte[] bytes) {
		return super.encrypt(bytes);
	}

	@Override
	public Object decrypt(byte[] bytes) {
		return super.decrypt(bytes);
	}
}
