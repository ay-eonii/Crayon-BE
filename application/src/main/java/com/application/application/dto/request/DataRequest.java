package com.application.application.dto.request;

import com.application.domain.vo.Reply;

public record DataRequest(
        String v
) {

    public Reply toReply() {
        return new Reply(v);
    }
}
