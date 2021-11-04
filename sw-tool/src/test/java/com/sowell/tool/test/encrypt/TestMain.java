package com.sowell.tool.test.encrypt;

import com.sowell.tool.encrypt.model.SwPublicKey;
import org.junit.Test;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/04 15:43
 */
public class TestMain {

	@Test
	public void test(){
		SwPublicKey publicKey = new SwPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFi9vYzmI+ktpZsv7VTKOEHrNqpRSm5QaA0EHYv72G72ehgTKNuHw0CDskASCJ85gKMzy5uANX+6Q4gz9uAE/1duH3Oc9zxLiyr4iptrZj4v667kW5VcXXdho4shP6LOX+8KaW0ddG6Rt9BX8qUjM8J+7yVjo4EJzJ3Pri0TvvZwIDAQAB");

		System.out.println(publicKey.decrypt("PsuRjxhVIoeRLKt13qr1szxNdmceF9CtRr7ECB3BY3R/yVLVW3r7fmS2cN5Wihl2GoGIcXFK0gxh+LYchdK8ev5Iw2lVqcbHojC4HjOWFMFxn6NoKfXyREycIXIYY77BfDPKduJlhuNg+kk8AlH9vs9/WiQQWhqm1600icUAjXc="));


	}
}
