package com.litecase.boot.web.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

public class SMSUtil {
    /**
     * 发送短信
     *
     * @param signName 签名
     * @param templateCode 模板
     * @param phoneNumbers 手机号
     * @param params 参数
     */
    public static void sendMessage(String signName, String templateCode, String phoneNumbers, String params) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "", "");
        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();

        request.setSysRegionId("");
        request.setPhoneNumbers(phoneNumbers);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateCode("{\"code\":\"" + params + "\"}");

        try {
            SendSmsResponse response = client.getAcsResponse(request);
        }catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
