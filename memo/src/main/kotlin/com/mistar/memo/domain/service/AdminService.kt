package com.mistar.memo.domain.service

import com.mistar.memo.domain.exception.InvalidPageException
import com.mistar.memo.domain.exception.PageOutOfBoundsException
import com.mistar.memo.domain.model.common.Page
import com.mistar.memo.domain.model.dto.UserInfoDto
import com.mistar.memo.domain.model.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val userRepository: UserRepository
) {
    private final val defaultPageSize = 10

    fun getUserList(page: Int): List<UserInfoDto> {
        if (page < 1)
            throw InvalidPageException()
        val userCnt = userRepository.countByIsDeletedIsFalse()
        if (userCnt < (page - 1) * 10)
            throw PageOutOfBoundsException()

        val requestedPage = Page(page - 1, defaultPageSize)
        val users = userRepository.findAllByIsDeletedIsFalse(requestedPage)
        val userList = arrayListOf<UserInfoDto>()
        for (user in users)
            userList.add(UserInfoDto(user))
        return userList
    }
}