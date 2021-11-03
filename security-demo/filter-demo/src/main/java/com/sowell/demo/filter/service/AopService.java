package com.sowell.demo.filter.service;

import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/01 16:55
 */
@Component
public class AopService {

	public void log() {
		System.out.println("com.sowell.demo.filter.service.AopService.log()");
	}
}
