package org.digitalLibrary.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MembershipStatus {
    Active("Active"),
    Expired("Expired"),
    Paused("Paused");

    private String status;

    MembershipStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return this.status;
    }

    @JsonCreator
    public static MembershipStatus fromString(String status) {
        for (MembershipStatus s : MembershipStatus.values()) {
            if (s.getStatus().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unkonw status " + status);
    }
}
