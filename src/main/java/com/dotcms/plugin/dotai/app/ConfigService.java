package com.dotcms.plugin.dotai.app;

import com.dotcms.security.apps.AppSecrets;
import com.dotcms.security.apps.Secret;
import com.dotmarketing.beans.Host;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.util.Logger;
import com.liferay.util.StringPool;
import io.vavr.control.Try;
import java.util.Map;
import java.util.Optional;

public class ConfigService {

    public static final ConfigService INSTANCE = new ConfigService();

    /**
     * Gets the secrets from the App - this will check the current host then the SYSTEMM_HOST for a
     * valid configuration. This lookup is low overhead and cached by dotCMS.
     *
     * @param host
     * @return
     */
    public Optional<AppConfig> config(final Host host) {

        final Optional<AppSecrets> appSecrets = Try.of(
                () -> APILocator.getAppsAPI().getSecrets(AppKeys.APP_KEY, true, host, APILocator.systemUser()))
            .getOrElse(Optional.empty());

        if (!appSecrets.isPresent()) {
            Logger.debug(this.getClass().getName(), () -> "App secrets is empty for host: " + host.getHostname());
            return Optional.empty();
        }

        final Map<String, Secret> secrets = appSecrets.get().getSecrets();
        final String apiUrl = Try.of(() -> secrets
            .get(AppKeys.API_URL.key).getString()).getOrElse(StringPool.BLANK);
        final String apiKey = Try.of(() -> secrets
            .get(AppKeys.API_KEY.key).getString()).getOrElse(StringPool.BLANK);
        final String rolePrompt = Try.of(() -> secrets
            .get(AppKeys.ROLE_PROMPT.key).getString()).getOrElse(StringPool.BLANK);
        final String textPrompt = Try.of(() -> secrets
            .get(AppKeys.TEXT_PROMPT.key).getString()).getOrElse(StringPool.BLANK);
        final String imagePrompt = Try.of(() -> secrets
            .get(AppKeys.IMAGE_PROMPT.key).getString()).getOrElse(StringPool.BLANK);
        final String model = Try.of(() -> secrets
            .get(AppKeys.MODEL.key).getString()).getOrElse(StringPool.BLANK);

        Logger.debug(this.getClass().getName(), () -> "apiUrl: " + apiUrl);
        Logger.debug(this.getClass().getName(), () -> "apiKey: " + apiKey);
        Logger.debug(this.getClass().getName(), () -> "rolePrompt: " + rolePrompt);
        Logger.debug(this.getClass().getName(), () -> "textPrompt: " + textPrompt);
        Logger.debug(this.getClass().getName(), () -> "imagePrompt: " + imagePrompt);
        Logger.debug(this.getClass().getName(), () -> "model: " + model);

        final AppConfig config = new AppConfig(apiUrl, apiKey, rolePrompt, textPrompt, imagePrompt, model);

        return Optional.ofNullable(config);
    }

}
