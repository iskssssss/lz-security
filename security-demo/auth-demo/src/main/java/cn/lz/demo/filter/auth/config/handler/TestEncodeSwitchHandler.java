package cn.lz.demo.filter.auth.config.handler;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.handler.EncodeSwitchHandler;
import cn.lz.tool.core.string.StringUtil;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/08 10:51
 */
@Component
public class TestEncodeSwitchHandler implements EncodeSwitchHandler {

	@Override
	public boolean isDecrypt(
			BaseRequest<?> request
	) {
		//System.out.println("TestEncryptSwitchHandler.decrypt(...)");
		final String decrypt = request.getHeader("decrypt");
		if (StringUtil.isEmpty(decrypt)) {
			return false;
		}
		return decrypt.intern() == "true";
	}

	@Override
	public boolean isEncrypt(
			BaseRequest<?> request
	) {
		//System.out.println("TestEncryptSwitchHandler.encrypt(...)");
		final String encrypt = request.getHeader("encrypt");
		if (StringUtil.isEmpty(encrypt)) {
			return false;
		}
		return encrypt.intern() == "true";
	}

    @Override
    public String responseContentType(BaseRequest<?> request) {
        return null;
    }
}
