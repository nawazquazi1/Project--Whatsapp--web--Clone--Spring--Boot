package com.nawaz.watsapp.web.clone.payloads;

import lombok.*;

@Data
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
    private Long userId;
    private Long  chatId;
    private String content;
}
