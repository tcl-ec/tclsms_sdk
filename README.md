tcl短信 Java SDK
===

## tcl短信服务

目前`tcl短信`为接入了`创蓝`、`微网通`两大服务，使用tcl短信sdk可以快速的集成短信发送功能：



## 开发

### 准备

在开始开发短信应用之前，需要准备如下信息:

- [x] 获取SDK AppID和AppKey

短信应用SDK `AppID`和`AppKey`可在联系管理员获取。

- [x] 申请签名

一个完整的短信由短信`签名`和短信正文内容两部分组成，短信`签名`须申请和审核，`签名`联系管理员获取。


### 安装

tclsms_sdk可以采用多种方式进行安装，我们提供以下三种方法供用户使用：


#### 1. 下载包导入

从release中寻找最新的包导入：[release](https://github.com/tcl-ec/tclsms_sdk/releases)


#### 2. maven导入

在pom.xml中添加如下依赖：

```xml
<dependency>
  <groupId>com.github.tcl-ec</groupId>
  <artifactId>tclsms</artifactId>
  <version>1.0.0</version>
</dependency>
```

#### 3. 其他

将源代码直接引入到项目工程中

- Note 由于tclsms_sdk依赖四个依赖项目library： org.json , httpclient, httpcore和 httpmine 采用源代码方法需要将以上四个jar包导入工程。



### 示例

- **准备必要参数**

```java
// 短信应用SDK AppID
int appid = 20190001; 

// 短信应用SDK AppKey
String appkey = "5f03a35d00ee52a21327ab048186a2h1";

// 需要发送短信的手机号码
String[] phoneNumbers = {"21212313123", "12345678902", "12345678903"};

// 签名
String sign = "1"; // NOTE: 这里的签名需要注意的是`签名ID`，联系管理员获取
```

- **单发短信**

```java
import com.github.tclec.tclsms.SmsSender;
import com.github.tclec.tclsms.SmsSenderResult;
import com.github.tclec.tclsms.HTTPException;
import java.io.IOException;


 SmsSender smsSender = new SmsSender(20190002, "5f03a35d00ee52a21327ab048186a2h1");
        SmsSenderResult result = null;
        try {
            result = smsSender.send("1", "这是一条测试短信", phoneNumbers[0], null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
```



- **群发**

```java
import com.github.tclec.tclsms.SmsSender;
import com.github.tclec.tclsms.SmsSenderResult;
import com.github.tclec.tclsms.HTTPException;
import java.io.IOException;


 SmsSender smsSender = new SmsSender(20190002, "5f03a35d00ee52a21327ab048186a2h1");
        SmsSenderResult result = null;
        try {
            result = smsSender.sendBatch("1", "这是一条测试短信", phoneNumbers, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

```



> `Note` 注意上面的这个示例代码只作参考，无法直接编译和运行，需要作相应修改。
