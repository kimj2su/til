package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Membership {

    @Getter
    private final String membershipId;
    @Getter
    private final String name;
    @Getter
    private final String email;
    @Getter
    private final String address;
    @Getter
    private final boolean isValid;
    @Getter
    private final boolean isCorp;

    public static Membership generateMember(
            MembershipId membershipId,
            MembershipName membershipName,
            MemberShipEmail memberShipEmail,
            MembershipAddress membershipAddress,
            MembershipisValid MembershipisValid,
            MembershipCorp membershipCorp
    ) {
        return new Membership(
                membershipId.membershipId,
                membershipName.MembershipName,
                memberShipEmail.MemberShipEmail,
                membershipAddress.MembershipAddress,
                MembershipisValid.MembershipisValid,
                membershipCorp.MembershipCorp
        );
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.membershipId = value;
        }
        String membershipId;
    }

    @Value
    public static class MembershipName {
        public MembershipName(String value) {
            this.MembershipName = value;
        }
        String MembershipName;
    }

    @Value
    public static class MemberShipEmail {
        public MemberShipEmail(String value) {
            this.MemberShipEmail = value;
        }
        String MemberShipEmail;
    }

    @Value
    public static class MembershipAddress {
        public MembershipAddress(String value) {
            this.MembershipAddress = value;
        }
        String MembershipAddress;
    }

    @Value
    public static class MembershipisValid {
        public MembershipisValid(boolean value) {
            this.MembershipisValid = value;
        }
        boolean MembershipisValid;
    }

    @Value
    public static class MembershipCorp {
        public MembershipCorp(boolean value) {
            this.MembershipCorp = value;
        }
        boolean MembershipCorp;
    }

}
