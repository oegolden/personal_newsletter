package com.ogolden.personal_newsletter.Utils;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;

public class DocumentModel {

    AnthropicClient client = AnthropicOkHttpClient.fromEnv();

}
