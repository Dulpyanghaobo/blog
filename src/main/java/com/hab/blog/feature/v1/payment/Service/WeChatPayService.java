package com.hab.blog.feature.v1.payment.Service;

import com.hab.blog.feature.v1.payment.util.WeChatPayUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeChatPayService {

    // 微信支付的配置参数
    private final String appId = "YOUR_APP_ID";
    private final String mchId = "YOUR_MERCHANT_ID";
    private final String apiKey = "YOUR_API_KEY";
    private final String unifiedOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    // 创建支付订单
    public Map<String, String> createPaymentOrder(String openid, String outTradeNo, int totalFee, String description, String clientIp) throws Exception {

        // 构建支付订单请求参数
        Map<String, String> requestData = new HashMap<>();
        requestData.put("appid", appId);
        requestData.put("mch_id", mchId);
        requestData.put("nonce_str", WeChatPayUtils.generateNonceStr());
        requestData.put("body", description);
        requestData.put("out_trade_no", outTradeNo);
        requestData.put("total_fee", String.valueOf(totalFee));  // 单位为分
        requestData.put("spbill_create_ip", clientIp);
        requestData.put("notify_url", "https://your-domain.com/wechat/pay/notify");  // 回调通知URL
        requestData.put("trade_type", "JSAPI");  // 小程序支付类型
        requestData.put("openid", openid);

        // 生成签名
        String sign = WeChatPayUtils.generateSignature(requestData, apiKey);
        requestData.put("sign", sign);

        // 将请求数据转换为XML格式并发送给微信支付API
        String requestXml = WeChatPayUtils.mapToXml(requestData);
        String responseXml = WeChatPayUtils.sendRequestToWeChat(unifiedOrderUrl, requestXml);

        // 解析微信支付返回的XML
        Map<String, String> responseData = WeChatPayUtils.xmlToMap(responseXml);

        // 检查返回结果，成功则返回前端所需的支付参数
        if ("SUCCESS".equals(responseData.get("return_code")) && "SUCCESS".equals(responseData.get("result_code"))) {
            Map<String, String> paymentInfo = new HashMap<>();
            paymentInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));  // 时间戳
            paymentInfo.put("nonceStr", WeChatPayUtils.generateNonceStr());  // 随机字符串
            paymentInfo.put("package", "prepay_id=" + responseData.get("prepay_id"));  // prepay_id
            paymentInfo.put("signType", "MD5");

            // 再次生成签名，用于前端发起支付
            String paySign = WeChatPayUtils.generateSignature(paymentInfo, apiKey);
            paymentInfo.put("paySign", paySign);

            return paymentInfo;
        } else {
            throw new RuntimeException("微信支付下单失败：" + responseData.get("return_msg"));
        }
    }
}

