package com.application.application.dto.request;

import com.application.domain.vo.Replies;
import com.application.domain.vo.Reply;

import java.util.List;

public record RespondentRequest(
        List<DataRequest> c
) {

    public Replies toReplies() {
        List<Reply> replies = c.stream()
                .map(this::toReply)
                .toList();
        return new Replies(replies);
    }

    private Reply toReply(DataRequest dataRequest) {
        if (dataRequest == null) {
            return Reply.empty();
        }
        return dataRequest.toReply();
    }
}
