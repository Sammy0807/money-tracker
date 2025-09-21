package com.example.finance.transactionservice.api;

import java.time.Instant;

public record ApiError(String error, String message, int status, Instant timestamp) { }
