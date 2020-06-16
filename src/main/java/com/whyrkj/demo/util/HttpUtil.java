package com.whyrkj.demo.util;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * 说明: http请求客户端
 * @Author zhangsanfeng
 *
 * new HttpUtil().url(url)
 * .data("token", "76AF8B201FEC")
 * .data("body", json.toJSONString())
 * .post();
 *
 * new HttpUtil().url(url)
 * .data("token", "76AF8B201FEC")
 * .file("image", fileRootPath)
 * .postMultipart();
 */
public class HttpUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");

    private OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS);
    private Request.Builder requestBuilder = new Request.Builder();
    private FormBody.Builder formBodyBuilder = new FormBody.Builder();
    private MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

    private String url = "";
    private Map<String, String> headers = new LinkedHashMap<>();
    private Map<String, String> queryParas = new LinkedHashMap<>();
    private Map<String, String> files = new LinkedHashMap<>();

    public HttpUtil url(String url) {
        this.url = url;
        return this;
    }

    public HttpUtil data(String key, String value) {
        this.queryParas.put(key, value);
        return this;
    }

    public HttpUtil files(Map<String, String> files) {
        this.files.putAll(files);
        return this;
    }

    public HttpUtil file(String key, String value) {
        this.files.put(key, value);
        return this;
    }

    public HttpUtil header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public HttpUtil userAgent(String userAgent) {
        header("User-Agent", userAgent);
        return this;
    }

    public HttpUtil contentType(String contentType) {
        header("Content-Type", contentType);
        return this;
    }

    public HttpUtil acceptEncoding(String acceptEncoding) {
        header("Accept-Encoding", acceptEncoding);
        return this;
    }

    /**
     * Send POST request
     *
     * @throws IOException
     */
    public String post() throws IOException {
        if (StringUtils.isEmpty(url)) {
            log.error("URL为空");
            return "";
        }

        for (Entry<String, String> entry : queryParas.entrySet()) {
            formBodyBuilder.add(entry.getKey(), entry.getValue());
        }

        requestBuilder.url(url);
        for (Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder.post(formBodyBuilder.build()).build();
        Response response = httpClient.build().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("POST请求服务器端错误: " + response);
        }

        return response.body().string();
    }

    /**
     * Send GET request
     *
     * @throws IOException
     */
    public String get() throws IOException {
        if (StringUtils.isEmpty(url)) {
            log.error("URL为空");
            return "";
        }

        requestBuilder.url(buildUrlWithQueryString(url, queryParas));
        for (Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder.build();
        Response response = httpClient.build().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("GET请求服务器端错误: " + response);
        }

        return response.body().string();
    }

    /**
     * Send POST request
     *
     * @throws IOException
     */
    public String postMultipart() throws IOException {
        if (StringUtils.isEmpty(url)) {
            log.error("URL为空");
            return "";
        }

        for (Entry<String, String> entry : files.entrySet()) {
            File file = new File(entry.getValue());
            if (file.exists()) {
                multipartBodyBuilder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
            } else {
                log.warn("待上传文件没有在磁盘上找到, filePath = " + entry.getValue());
            }
        }

        for (Entry<String, String> entry : queryParas.entrySet()) {
            multipartBodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }

        requestBuilder.url(url);
        for (Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder.post(multipartBodyBuilder.build()).build();
        Response response = httpClient.build().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: url=" + this.url + ", response=" + response.toString());
        }

        return response.body().string();
    }

    /**
     * Build queryString of the url
     */
    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas == null || queryParas.isEmpty()) {
            return url;
        }

        StringBuffer sb = new StringBuffer(url);
        boolean isFirst;
        if (!url.contains("?")) {
            isFirst = true;
            sb.append("?");
        } else {
            isFirst = false;
        }

        for (Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }

            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isEmpty(value)) {
                try {
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    //发送响应流方法
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

