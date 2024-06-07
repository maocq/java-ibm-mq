package co.com.bancolombia.model.account;

import lombok.Builder;

@Builder(toBuilder = true)
public record Account(String name, int balance) {}
