package com.aptech.common.event.email;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEvent {
    private String eventType;
    private String recipientEmail;
    private String templateId;
    private Map<String, String> dynamicData;
}
