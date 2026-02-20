package com.application.application.dto.response;

import com.application.domain.entity.Answer;
import com.item.domain.entity.Item;
import java.util.List;

public class AnswerResponseDTO {

    public record Response(
            String id,
            List<Item> items
    ) {
        public static Response toAnswerResponse(Answer answer) {
            return new AnswerResponseDTO.Response(
                    answer.getId(),
                    answer.getItems()
            );
        }
    }
}
