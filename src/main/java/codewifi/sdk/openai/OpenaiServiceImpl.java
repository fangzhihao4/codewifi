package codewifi.sdk.openai;

import codewifi.common.constant.LogConstant;
import codewifi.common.constant.enums.VerystatusGoodsEnum;
import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.request.very.VerystatusPayGoodsRequest;
import codewifi.utils.JsonUtil;
import codewifi.utils.LogUtil;
import codewifi.utils.RestTemplateUtil;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class OpenaiServiceImpl implements OpenaiService {
    private final JsonUtil jsonUtil;
    private static final LogUtil logUtil = LogUtil.getLogger(OpenaiServiceImpl.class);
    private static final String v2 = "OpenAIServiceImpl";
    private final RestTemplateUtil restTemplateUtil;

    public static String URL = "https://api.openai.com/v1/chat/completions";

    public OpenaiRequest getRequest(String userContent){
        OpenaiRequest openAiRequest = new OpenaiRequest();
        openAiRequest.setModel("gpt-3.5-turbo");
        OpenaiRequest.Messages messages = new OpenaiRequest.Messages();
        messages.setRole("user");
        messages.setContent(userContent);
        openAiRequest.setMessages(Collections.singletonList(messages));
        return openAiRequest;
    }


    public String request (String  content, String v3){
        OpenaiRequest openaiRequest = getRequest(content);
        String response = "{}";
        try {


            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间
                    .readTimeout(30, TimeUnit.SECONDS) // 读取超时时间
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonUtil.writeValueAsString(openaiRequest));
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer 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")
                    .build();
            Response responseData = client.newCall(request).execute();
            response = responseData.body().string();

        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "openai查询错误", URL, ExceptionUtils.getStackTrace(e));
            return null;
        }
        OpenaiResponse openaiResponse;
        try {
            openaiResponse = jsonUtil.fromJsonString(response, OpenaiResponse.class);
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "openai失败数据转对象失败", null, response);
            return null;
        }
        if (Objects.isNull(openaiResponse.getChoices())){
            return null;
        }
        if (Objects.isNull(openaiResponse.getChoices().get(0).getMessage())){
            return null;
        }
        if (Objects.isNull(openaiResponse.getChoices().get(0).getMessage().getContent())){
            return null;
        }
        return openaiResponse.getChoices().get(0).getMessage().getContent();
    }



    @Override
    public String getByName(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest) {
        String messageHead = "男".equals(verystatusPayGoodsRequest.getParamTwo()) ? "男孩取名" : "女孩取名";
        String message = messageHead + verystatusPayGoodsRequest.getParamFirst() + ",解读一下这个名字有哪些寓意和期望，有什么深的含义和著名的诗词典故";
        return request(message,"getByName");
    }

    @Override
    public String getReport(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest) {
        String content = "";
        if ("日报".equalsIgnoreCase(verystatusPayGoodsRequest.getParamTwo())){
            content = verystatusPayGoodsRequest.getParamFirst() + ", 根据以上内容生成日报";
        }
        if ("周报".equalsIgnoreCase(verystatusPayGoodsRequest.getParamTwo())){
            content = verystatusPayGoodsRequest.getParamFirst() + ", 根据以上内容生成周报";
        }
        if ("总结".equalsIgnoreCase(verystatusPayGoodsRequest.getParamTwo())){
            content = verystatusPayGoodsRequest.getParamFirst() + ", 根据以上内容生成总结";
        }
        if (StringUtils.isBlank(content)){
            return null;
        }
        return request(content, "getReport");
    }
}
