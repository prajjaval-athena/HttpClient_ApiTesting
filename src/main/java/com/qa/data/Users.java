package com.qa.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Users {
	private String name;
	private String job;
}