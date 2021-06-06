package com.mistar.memo.domain.exception.auth

class BlackedUserException : RuntimeException("이미 로그아웃한 회원입니다.")