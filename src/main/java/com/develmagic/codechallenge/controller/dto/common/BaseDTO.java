package com.develmagic.codechallenge.controller.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseDTO {
}
