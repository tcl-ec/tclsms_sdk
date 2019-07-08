package com.github.tclec.tclsms;

import org.json.JSONObject;
import org.json.JSONException;

import com.github.tclec.tclsms.httpclient.HTTPResponse;


public class SmsSenderResult extends SmsResultBase {

    public int code;
    public String msg;
    public Object data;

    public SmsSenderResult() {
        this.msg = "";
        this.data=null;
    }

    @Override
    public SmsSenderResult parseFromHTTPResponse(HTTPResponse response)
            throws JSONException {

        JSONObject json = parseToJson(response);

        code = json.getInt("code");
        msg = json.getString("msg");
        data = json.get("data");
        return this;
    }
}
