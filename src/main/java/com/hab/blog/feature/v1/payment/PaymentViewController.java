package com.hab.blog.feature.v1.payment;

import com.hab.blog.feature.v1.payment.util.WeChatPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/wechat/pay")
public class PaymentViewController {

    @PostMapping("/notify")
    public String handlePaymentNotification(HttpServletRequest request) {
        try {
            // 解析微信支付回调的 XML 数据
            String notificationXml = WeChatPayUtils.getRequestBody(request);
            Map<String, String> notificationData = WeChatPayUtils.xmlToMap(notificationXml);

            // 验证签名
            String sign = notificationData.get("sign");
            notificationData.remove("sign");  // 验证签名时不需要 sign 本身
            String generatedSign = WeChatPayUtils.generateSignature(notificationData, "apiKey");

            if (sign.equals(generatedSign) && "SUCCESS".equals(notificationData.get("return_code"))) {
                // 支付成功，更新订单状态
                String outTradeNo = notificationData.get("out_trade_no");
                // 更新数据库订单状态为已支付...

                return "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "<xml><return_code><![CDATA[FAIL]]></return_code></xml>";
    }
}
