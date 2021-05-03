package com.mistar.memo.domain.exception

class UserAndMemoNotMatchedException : RuntimeException("메모를 작성하지 않은 사람은 해당 메모를 수정할 수 없습니다.")