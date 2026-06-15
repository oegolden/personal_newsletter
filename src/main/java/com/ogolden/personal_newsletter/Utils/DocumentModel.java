package com.ogolden.personal_newsletter.Utils;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.*;

import static com.ogolden.personal_newsletter.Utils.WebScrapper.ScrapedDocStruct;
public class DocumentModel {

    public static class SummarizedClass extends ScrapedDocStruct {
        private String summary;
        private String dateSummaryWritten;
    }

    private final AnthropicClient client = AnthropicOkHttpClient.fromEnv();

    public SummarizedClass GenerateStruct(ScrapedDocStruct doc){
        StructuredMessageCreateParams<SummarizedClass> params = MessageCreateParams.builder()
                .model(Model.CLAUDE_OPUS_4_8)
                .maxTokens(1024)
                .addUserMessage("PROMPT ABOUT GETTING SUMMARY AND STUFF FROM HTML AND PUTTING IT IN THIS OBJECT")
                .outputConfig(SummarizedClass.class)
                .build();

        StructuredMessage<SummarizedClass> response = client.messages().create(params);
        return (SummarizedClass) response.content().stream()
                .flatMap(block -> block.text().stream())
                .findFirst().orElseThrow().text();
    }
}
