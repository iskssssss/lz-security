package cn.lz.tool.test.encrypt;

import cn.lz.tool.encrypt.RSAEncryptUtil;
import cn.lz.tool.encrypt.model.SwKeyPair;
import cn.lz.tool.encrypt.model.SwPublicKey;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/04 15:43
 */
public class TestMain {

	@Test
	public void test() throws NoSuchAlgorithmException {
//		SwKeyPair swKeyPair = SwKeyPair.genKeyPair();
		SwKeyPair swKeyPair = new SwKeyPair(
				"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIvQCQ44JKU98yVB" +
						"UJnwUHK+A9pvNlf0H08Ku8OYEmpejyk0RgBRgNME/z7ab/NA8ngjyiJcMt2Kq3QZ" +
						"GQjZldq834DoMLO3j+A6zDz/UXMNUvT0w7zpbKAaNsjJTcOg7DhAZa+0dAT6pJ2U" +
						"m6EbR7wlF4RZu4NHVXaPZWord65fAgMBAAECgYAP7OVrKjf6XoEYqETvE/GF8GDt" +
						"+6b8A8GhNj7G+8Z1OUojTmH+3UnIP5R3a1g/JBG6X5FBsrDUjVwUQFuap3YrJIpc" +
						"1IV5LzV+r/OgaF2IIe9P1ePPvJpPi4PZyHbd1KN9rdYi026M7sSfP7+F05Z6lSJw" +
						"FPwCWehDhuht8hickQJBAPiksOjvWXssQBq4zx1/JMpub/18nnr0OIXfmJh0EG6Q" +
						"/79KJyvqITRZhz6ZmRsg9GlCAhcJNfx039aCIZY3JrcCQQCP8wYU6xSY0h9N2MZ2" +
						"UFne6Yi7NTsEmuYPMz0TBvUFzFf5cwb84hRtCv4LJ0EPm3mGGHnycmbg0jR3QHaw" +
						"h82ZAkB5wz0XdU8esLbb3zEeQstjjt2tB0Ac+khL1wnFbuE1JDD6Hng8WzXTixxe" +
						"HZ4K2QKYIUkgutQDQ8DyLVwBUhTVAkBDrWKCmzOH1eJZ2z0Tixt1rh5WxeQFej4H" +
						"j3N7ap3wJ+6EnQwAANrRmYVvAPmZuOMdpIxQ7HBp6uo31tr2jCKhAkEAoJ7EUg0P" +
						"HN/LS8UMmr5Kb+dZltPxQjYjcunqiFXg6pa2rBn9yP2gt9nT5BvhTzDzdPlJdmHk" +
						"Qi1a3F2OUgFlrg==",
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCL0AkOOCSlPfMlQVCZ8FByvgPa" +
						"bzZX9B9PCrvDmBJqXo8pNEYAUYDTBP8+2m/zQPJ4I8oiXDLdiqt0GRkI2ZXavN+A" +
						"6DCzt4/gOsw8/1FzDVL09MO86WygGjbIyU3DoOw4QGWvtHQE+qSdlJuhG0e8JReE" +
						"WbuDR1V2j2VqK3euXwIDAQAB"
		);
		System.out.println(swKeyPair.getPrivateKeyStr());
		System.out.println(swKeyPair.getPublicKeyStr());
		System.out.println("------------------------------------------------------------");
		String encryptByPrivateKey = swKeyPair.encryptByPrivateKey("321");
		System.out.println("私钥加密：" + encryptByPrivateKey);
		System.out.println("公钥解密：" + swKeyPair.decryptByPublicKey(encryptByPrivateKey));
		System.out.println("------------------------------------------------------------");
		String encryptByPublicKey = swKeyPair.encryptByPublicKey("321");
		System.out.println("公钥加密：" + encryptByPublicKey);
		System.out.println("私钥解密：" + swKeyPair.decryptByPrivateKey(encryptByPublicKey));
	}
}
