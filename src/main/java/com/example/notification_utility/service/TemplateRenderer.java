package com.example.notification_utility.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TemplateRenderer {

    public String render(String template, Map<String, Object> templateData) {

        if (template == null || template.isEmpty()) {
            return template;
        }

        if (templateData == null || templateData.isEmpty()) {
            return template;
        }

        String renderedTemplate = template;

        for (Map.Entry<String, Object> entry : templateData.entrySet()) {

            String placeholder = "{{" + entry.getKey() + "}}";

            String value = entry.getValue() != null
                    ? entry.getValue().toString()
                    : "";

            renderedTemplate = renderedTemplate.replace(placeholder, value);
        }

        return renderedTemplate;
    }
}