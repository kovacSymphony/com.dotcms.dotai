package com.dotcms.plugin.dotai;

import com.dotcms.plugin.dotai.app.AppConfig;
import com.dotcms.plugin.dotai.model.AITextResponseDTO;
import java.util.Optional;

public interface ChatGPTService {

    AITextResponseDTO sendChatGPTRequest(String prompt, Optional<AppConfig> config);

}
