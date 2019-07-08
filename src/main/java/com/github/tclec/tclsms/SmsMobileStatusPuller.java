package com.github.tclec.tclsms;

import org.json.JSONObject;
import org.json.JSONException;

import com.github.tclec.tclsms.httpclient.HTTPClient;
import com.github.tclec.tclsms.httpclient.HTTPException;
import com.github.tclec.tclsms.httpclient.HTTPMethod;
import com.github.tclec.tclsms.httpclient.HTTPRequest;
import com.github.tclec.tclsms.httpclient.HTTPResponse;
import com.github.tclec.tclsms.httpclient.DefaultHTTPClient;

import java.io.IOException;
import java.net.URISyntaxException;

@Deprecated
public class SmsMobileStatusPuller extends SmsBase {

    private String url = "https://sms.tcl.com/api/v1/pullstatus4mobile";

    public SmsMobileStatusPuller(int appid, String appkey) {
        super(appid, appkey, new DefaultHTTPClient());
    }

    public SmsMobileStatusPuller(int appid, String appkey, HTTPClient httpclient) {
        super(appid, appkey, httpclient);
    }

    private HTTPResponse pull(int type, String nationCode, String mobile, long beginTime,
            long endTime, int max) throws IOException {

        long random = SmsSenderUtil.getRandom();
        long now = SmsSenderUtil.getCurrentTime();
        JSONObject body = new JSONObject();
        body.put("sig", SmsSenderUtil.calculateSignature(this.appkey, random, now))
            .put("type", type)
            .put("time", now)
            .put("max", max)
            .put("begin_time", beginTime)
            .put("end_time", endTime)
            .put("nationcode", nationCode)
            .put("mobile", mobile);

        HTTPRequest req = new HTTPRequest(HTTPMethod.POST, this.url)
            .addHeader("Conetent-Type", "application/json")
            .addQueryParameter("sdkappid", this.appid)
            .addQueryParameter("random", random)
            .setConnectionTimeout(60 * 1000)
            .setRequestTimeout(60 * 10000)
            .setBody(body.toString());

        // May throw IOException
        try {
            return httpclient.fetch(req);
        } catch(URISyntaxException e) {
            throw new RuntimeException("API url has been modified, current url: " + url);
        }
    }

    public SmsStatusPullCallbackResult pullCallback(String nationCode, String mobile,
        long beginTime, long endTime, int max)
            throws HTTPException, JSONException, IOException {

        // May throw IOException
        HTTPResponse res = pull(0, nationCode, mobile, beginTime, endTime, max);

        // May throw HTTPException
        handleError(res);

        // May throw JSONException
        return (new SmsStatusPullCallbackResult()).parseFromHTTPResponse(res);
    }

    public SmsStatusPullReplyResult pullReply(String nationCode, String mobile,
        long beginTime, long endTime, int max)
            throws HTTPException, JSONException, IOException {

        // May throw IOException
        HTTPResponse res = pull(1, nationCode, mobile, beginTime, endTime, max);

        // May throw HTTPException
        handleError(res);

        // May throw JSONException
        return (new SmsStatusPullReplyResult()).parseFromHTTPResponse(res);
    }
}
