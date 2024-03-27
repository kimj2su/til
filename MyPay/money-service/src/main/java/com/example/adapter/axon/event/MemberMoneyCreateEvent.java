package com.example.adapter.axon.event;

import com.example.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberMoneyCreateEvent extends SelfValidating<MemberMoneyCreateEvent> {
    private String membershipId;

    public MemberMoneyCreateEvent(String membershipId) {
        this.membershipId = membershipId;
        this.validateSelf();
    }

    public MemberMoneyCreateEvent() {
    }
}