package cn.lz.demo.filter.controller.token;

import cn.lz.demo.filter.model.AccessTokenVO;
import cn.lz.demo.filter.model.R;
import cn.lz.security.LzCoreManager;
import cn.lz.security.annotation.DataEncodeSwitch;
import cn.lz.security.config.TokenConfig;
import cn.lz.security.defaults.DefaultAuthDetails;
import cn.lz.security.annotation.ExcludeInterface;
import cn.lz.security.token.AccessTokenUtil;
import cn.lz.security.plugins.utils.RedisUtil;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.core.number.NumberUtil;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.encrypt.EncryptUtil;
import cn.lz.tool.encrypt.model.AppKeyPair;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证相关接口
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/22 16:36
 */
@RestController
@ExcludeInterface
@RequestMapping("/auth")
@Api(tags = "认证相关接口")
public class AccessTokenController {

	@DataEncodeSwitch()
	@ApiOperation(value = "获取AccessToken", notes = "获取AccessToken1")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "appKey", value = "appKey", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "sign", value = "sign", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "timestamp", value = "时间戳", required = true)
	})
	@GetMapping(value = "/genAccessToken", produces = "application/json; charset=utf-8")
	public R<Object> getAccessToken(
			@RequestParam("appKey") String appKey,
			@RequestParam("sign") String sign,
			@RequestParam("timestamp") String timestamp
	) {
        // 校验请求参数是否正确
        if (!NumberUtil.checkNumber(timestamp)) {
            return R.error(RCode.ERROR_PARAMS);
        }
        // 校验当前应用是否存在
        final String appSecret = ((String) RedisUtil.hashGet("APP::LIST", appKey));
        if (StringUtil.isEmpty(appSecret)) {
            return R.error(RCode.APP_ID_NOT_EXIST);
        }
        // 校验加密数据是否匹配
        String verSign = EncryptUtil.sign(appKey, appSecret, Long.parseLong(timestamp));
        if (!sign.equals(verSign)) {
            return R.error(RCode.APP_ID_NOT_EXIST);
        }
        // 生成token
        final DefaultAuthDetails defaultAuthDetails = new DefaultAuthDetails();
        defaultAuthDetails.setId(appKey);
        defaultAuthDetails.setIdentifier(appKey);
        defaultAuthDetails.setCredential(appSecret);
        final String token = AccessTokenUtil.generateAccessToken(defaultAuthDetails, true);
        final TokenConfig tokenConfig = LzCoreManager.getLzConfig().getTokenConfig();
        return R.success(new AccessTokenVO() {{
            setAccessToken(token);
            setTtl(tokenConfig.getTimeoutForMillis());
        }});
    }

	@ApiOperation(value = "getAppKeyPair", notes = "getAppKeyPair")
	@GetMapping(value = "/getAppKeyPair", produces = "application/json; charset=utf-8")
	public R<AppKeyPair> getAppKeyPair() {
		final AppKeyPair appKeyPair = AppKeyPair.generateAppPair();
		RedisUtil.hashSet("APP::LIST", appKeyPair.getAppKey(), appKeyPair.getAppSecret());
		return R.success(appKeyPair);
	}

	@ApiOperation(value = "sign", notes = "sign")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "appKey", value = "appKey", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "appSecret", value = "appSecret", required = true),
	})
	@GetMapping(value = "/sign", produces = "application/json; charset=utf-8")
	public R<Map<String, Object>> sign(
			@RequestParam("appKey") String appKey,
			@RequestParam("appSecret") String appSecret
	) {
		Map<String, Object> resultMap = new HashMap<>(4);
		final long currentTimeMillis = System.currentTimeMillis();
		final String sign = EncryptUtil.sign(appKey, appSecret, currentTimeMillis);
		resultMap.put("sign", sign);
		resultMap.put("currentTimeMillis", currentTimeMillis);
		return R.success(resultMap);
	}
}
