package com.dotcms.plugin.dotai.interceptor;


import com.dotcms.plugin.dotai.Marshaller;
import com.dotmarketing.util.Logger;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class RequestLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Logger.info(this.getClass(), String.format("[ChatGPT API request] : URL = %s, method = %s, requestBody = %s", request.url(), request.method(), requestBodyToString(request)));

        Response response = chain.proceed(request);
        Logger.info(this.getClass(), String.format("[ChatGPT API response]: HTTPStatusCode = %s, message = %s, responseBody = %s", response.code(), response.message(), responseBodyToString(response)));

        return response;
    }

    private static String requestBodyToString(final Request request){
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "Failed to read request body";
        }
    }

    private static String responseBodyToString(final Response response) throws IOException {
        ResponseBody clonedResponseBody = response.peekBody(Long.MAX_VALUE);
        return Marshaller.marshal(clonedResponseBody.string());
    }

}
