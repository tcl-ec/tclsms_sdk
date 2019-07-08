package com.github.tclec.tclsms;

import com.github.tclec.tclsms.httpclient.HTTPClient;
import com.github.tclec.tclsms.httpclient.HTTPException;
import com.github.tclec.tclsms.httpclient.HTTPMethod;
import com.github.tclec.tclsms.httpclient.HTTPRequest;
import com.github.tclec.tclsms.httpclient.HTTPResponse;
import com.github.tclec.tclsms.httpclient.DefaultHTTPClient;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class SmsSender extends SmsBase {

    private String url = "http://sms.tcl.com/api/v1/sendsms";

    public SmsSender(int appid, String appkey) {
        super(appid, appkey, new DefaultHTTPClient());
    }

    public SmsSender(int appid, String appkey, HTTPClient httpclient) {
        super(appid, appkey, httpclient);
    }


    /**
     * 单发无变量短信
     *
     * @param sign         签名id
     * @param msg          短信内容
     * @param phoneNumber 电话号码
     * @param report       是否需要状态报告，默认为false
     * @param extend       下发短信扩展码，可填空
     * @return {@link}SmsSenderResult
     */
    public SmsSenderResult send(String sign, String msg, String phoneNumber,
                                String report, String extend)
            throws HTTPException, JSONException, IOException {
        ArrayList<String> phoneNumbers=new ArrayList<String>();
        phoneNumbers.add(phoneNumber);
        return sendBatch(sign, msg, phoneNumbers, report, extend);
    }

    /**
     * 批量发送无变量短信
     *
     * @param sign         签名id
     * @param msg          短信内容
     * @param phoneNumbers 接收短信手机号码
     * @param report       是否需要状态报告，默认为false
     * @param extend       短信下发扩展码
     * @return {@link}SmsSenderResult
     */
    public SmsSenderResult sendBatch(String sign, String msg, List<String> phoneNumbers
            , String report, String extend)
            throws HTTPException, JSONException, IOException {
        long random = SmsSenderUtil.getRandom();
        //发送时间
        String sendTime = SmsSenderUtil.getSendTime();
        //时间戳
        long time = System.currentTimeMillis();
        //生成签名验证
        String signature = SmsSenderUtil.calculateSignature(this.appkey, random, time, phoneNumbers);

        JSONObject body = new JSONObject()
                .put("msg", msg)
                .put("sign", sign)
                .put("sig", signature)
                .put("phone", phoneNumbers)
                .put("time", time)
                .put("sendtime", sendTime)
                .put("report", SmsSenderUtil.isNotEmpty(report) ? report : "false")
                .put("extend", SmsSenderUtil.isNotEmpty(extend) ? extend : "");

        HTTPRequest request = new HTTPRequest(HTTPMethod.POST, url)
                .addHeader("Content-Type", "application/json")
                .addQueryParameter("appid", this.appid)
                .addQueryParameter("random", random)
                .setConnectionTimeout(60 * 1000)
                .setRequestTimeout(60 * 1000)
                .setBody(body.toString());
        try {
            HTTPResponse response = httpclient.fetch(request);

            handleError(response);

            return new SmsSenderResult().parseFromHTTPResponse(response);
        } catch (URISyntaxException e) {
            throw new RuntimeException("API url has been modified, current url: " + url);
        }
    }



}
