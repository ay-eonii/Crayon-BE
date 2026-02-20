package com.mail.presentation;

import java.util.UUID;

public record TestRequest(
        UUID clubId,
        int size
) {
}
