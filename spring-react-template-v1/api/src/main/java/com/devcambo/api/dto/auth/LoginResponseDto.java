package com.devcambo.api.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDto(@JsonProperty("access_token") String token) {}
