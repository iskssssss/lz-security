package cn.lz.security.defaults.encoder;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.lz.security.handler.DataEncoder;
import cn.lz.security.log.LzLogger;
import cn.lz.security.log.LzLoggerUtil;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * AES 请求加解密处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/26 17:21
 */
public class AESDataEncoder implements DataEncoder {
    private final LzLogger logger = LzLoggerUtil.getLzLogger(AESDataEncoder.class);
    private AES aes;

    @Override
    public void init(Map<String, String> params) {
        String key = params.get("key");
        String iv = params.get("iv");
        aes = new cn.hutool.crypto.symmetric.AES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(StandardCharsets.UTF_8));
        aes.setIv(iv.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public byte[] encrypt(byte[] bytes) {
        logger.info("响应数据加密");
        final String encryptResult = aes.encryptBase64(bytes);
        return encryptResult.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] decrypt(byte[] encryptData) {
        logger.info("请求数据解密");
        final String encryptResult = aes.decryptStr(Base64.decode(encryptData));
        return encryptResult.getBytes(StandardCharsets.UTF_8);
    }
}
