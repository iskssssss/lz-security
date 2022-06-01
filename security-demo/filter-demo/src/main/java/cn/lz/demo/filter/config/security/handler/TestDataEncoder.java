//package cn.lz.demo.filter.config.security.handler;
//
//import cn.lz.demo.filter.model.R;
//import cn.lz.security.defaults.DefaultDataEncoder;
//import cn.lz.security.utils.ServletUtil;
//import cn.lz.tool.core.bytes.ByteUtil;
//import cn.lz.tool.core.string.StringUtil;
//import cn.lz.tool.http.enums.ContentTypeEnum;
//import cn.lz.tool.json.JsonUtil;
//import org.springframework.stereotype.Component;
//
///**
// * TODO
// *
// * @author 孔胜
// * @version 版权 Copyright(c)2021 LZ
// * @date 2021/11/10 16:43
// */
//@Component
//public class TestDataEncoder extends DefaultDataEncoder {
//
//	@Override
//	public byte[] encrypt(byte[] bytes) {
//		R r = ByteUtil.toObject(bytes, R.class);
//		Object data = r.getData();
//		if (StringUtil.isEmpty(data)) {
//			return bytes;
//		}
//		r.setData(ByteUtil.toObject(super.encrypt(ByteUtil.toBytes(data))));
//		return ByteUtil.toBytes(JsonUtil.toJsonString(r));
//	}
//
//	@Override
//	public byte[] decrypt(byte[] bytes) {
//		return super.decrypt(bytes);
//	}
//}
