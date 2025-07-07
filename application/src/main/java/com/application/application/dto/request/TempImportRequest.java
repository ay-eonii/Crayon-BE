package com.application.application.dto.request;

import com.application.domain.vo.ApplicationReply;
import com.application.domain.vo.Question;
import com.application.domain.vo.QuestionReply;
import com.application.domain.vo.Reply;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public record TempImportRequest(
        List<Map<String, String>> data
) {
    public List<ApplicationReply> toApplicationReplies() {
        return data.stream()
                .map(this::toQuestionReplyList)
                .map(ApplicationReply::of)
                .toList();
    }

    private List<QuestionReply> toQuestionReplyList(Map<String, String> map) {
        return map.entrySet().stream()
                .map(this::toQuestionReply)
                .toList();
    }

    private QuestionReply toQuestionReply(Entry<String, String> entry) {
        Question question = new Question(entry.getKey(), entry.getValue());
        Reply reply = new Reply(entry.getValue());
        return QuestionReply.of(question, reply);
    }
}
