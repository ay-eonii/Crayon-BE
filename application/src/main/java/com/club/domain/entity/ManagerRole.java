package com.club.domain.entity;

public enum ManagerRole {
    OWNER,
    MANAGER;

    public boolean isOwner() {
        return this == OWNER;
    }
}
