package cn.lz.security.defaults.encoder;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.DES;
import cn.lz.security.handler.DataEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * DES 请求加解密处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/7/4 10:09
 */
public class DESDataEncoder implements DataEncoder {
    private DES des;

    @Override
    public void init(Map<String, String> params) {
        String key = params.get("key");
        String iv = params.get("iv");
        des = new DES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(StandardCharsets.UTF_8));
        des.setIv(iv.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public byte[] encrypt(byte[] bytes) {
        return des.encrypt(bytes);
    }

    @Override
    public byte[] decrypt(byte[] bytes) {
        return des.decrypt(bytes);
    }
}
