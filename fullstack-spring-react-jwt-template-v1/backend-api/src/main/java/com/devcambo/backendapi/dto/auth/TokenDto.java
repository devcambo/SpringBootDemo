package com.devcambo.backendapi.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenDto(@JsonProperty("access_token") String accessToken) {}
