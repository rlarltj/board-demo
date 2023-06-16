package com.example.board.global.dto;

public record ApiResponse<T> (
	T data
){

}
