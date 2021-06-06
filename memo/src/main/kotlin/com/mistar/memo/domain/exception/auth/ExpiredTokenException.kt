package com.mistar.memo.domain.exception.auth

class ExpiredTokenException : RuntimeException("만료된 토큰입니다.")