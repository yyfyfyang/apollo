package com.ctrip.framework.apollo.configservice.controller;


import com.ctrip.framework.apollo.biz.service.AppService;
import com.ctrip.framework.apollo.common.entity.App;
import com.ctrip.framework.apollo.common.entity.DecryptEntry;
import com.ctrip.framework.apollo.common.utils.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/decrypt")
public class DecryptController {
    private static final Logger logger = LoggerFactory.getLogger(DecryptController.class);
    @Autowired
    private AppService appService;

    @GetMapping(value = "/{appId}")
    public DecryptEntry decryptValue(@PathVariable String appId,
                                     @RequestParam(value = "timestamp", required = true) String timestamp,
                                     @RequestParam(value = "publicKey", required = true) String publicKey,
                                     @RequestParam(value = "encryptedValue", required = true) String encryptedValue,
                                     @RequestParam(value = "ip", required = false) String clientIp) {
        try {
            logger.info("call decrypt [{}] [{}]", appId, encryptedValue);
            App app = appService.findOne(appId);
            // 获取私钥进行解密
            String decryptedValue = SecurityUtil.decrypt(app.getPrivateKey(), encryptedValue);
            // 用传输过来的客户端公钥进行加密
            String encryptedValueByClientPublicKey = SecurityUtil.encrypt(publicKey, decryptedValue);
            Map<String, String> secMap = new HashMap<>(8);
            secMap.put("timestamp", timestamp);
            secMap.put("decryptedValue", decryptedValue);
            // 加签
            String macString = SecurityUtil
                    .md5(SecurityUtil.getSignSrcSkipNull(secMap, true, "&").getBytes(StandardCharsets.UTF_8));
            return new DecryptEntry(encryptedValueByClientPublicKey, macString);
        } catch (Exception ex) {
            logger.warn("decrypt exeception:", ex);
            return new DecryptEntry("", "");
        }
    }


}
