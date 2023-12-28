package com.example.adapter.out.service;

import com.example.application.port.out.GetMembershipPort;
import com.example.application.port.out.MembershipStatus;
import com.example.common.CommonHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MemberShipServiceAdapter implements GetMembershipPort {

    private final CommonHttpClient commonHttpClient;
    private final String membershipServiceUrl;

    public MemberShipServiceAdapter(
            CommonHttpClient commonHttpClient,
            @Value("${service.membership.url}") String membershipServiceUrl
    ) {
        for (int i = 0; i < 100; i++) {
            System.out.println("membershipServiceUrl = " + membershipServiceUrl);
        }
        this.commonHttpClient = commonHttpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public MembershipStatus getMembership(String membershipId) {
        String url = String.join("/", membershipServiceUrl, "membership", membershipId);
        System.out.println("url = " + url);
        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            System.out.println("jsonResponse = " + jsonResponse);
            ObjectMapper objectMapper = new ObjectMapper();
            Membership membership = objectMapper.readValue(jsonResponse, Membership.class);

            if (membership.isValid()) {
                return new MembershipStatus(membership.getMembershipId(), true);
            } else {
                return new MembershipStatus(membership.getMembershipId(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
