package com.mistar.memo.domain.service

import com.mistar.memo.domain.exception.memo.InvalidPageException
import com.mistar.memo.domain.exception.memo.MemoNotFoundException
import com.mistar.memo.domain.exception.memo.PageOutOfBoundsException
import com.mistar.memo.domain.exception.auth.UserNotFoundException
import com.mistar.memo.domain.model.common.Page
import com.mistar.memo.domain.model.dto.UserInfoDto
import com.mistar.memo.domain.model.entity.user.Role
import com.mistar.memo.domain.model.entity.memo.Tag
import com.mistar.memo.domain.model.repository.MemoRepository
import com.mistar.memo.domain.model.repository.TagRepository
import com.mistar.memo.domain.model.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AdminService(
    private val userRepository: UserRepository,
    private val memoRepository: MemoRepository,
    private val tagRepository: TagRepository
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

    fun deleteUser(userId: Int) {
        val user = userRepository.findByIdAndIsDeleted(userId, false).orElseThrow { UserNotFoundException() }
        user.roleFlag = 1
        user.isDeleted = true
        user.deletedAt = LocalDateTime.now()
        userRepository.save(user)
    }

    fun grantRole(userId: Int) {
        val user = userRepository.findByIdAndIsDeleted(userId, false).orElseThrow { UserNotFoundException() }
        user.roleFlag += Role.Flag.ADMIN.value
        userRepository.save(user)
    }

    fun deleteMemo(memoId: Int) {
        val memo = memoRepository.findByIdAndIsDeletedIsFalse(memoId).orElseThrow { MemoNotFoundException() }
        memo.isDeleted = true
        memo.deletedAt = LocalDateTime.now()
        memoRepository.save(memo)
    }

    fun getTagsDescending(count: Int): List<Tag> {
        val requestedPage = Page(0, count)
        return tagRepository.findTagsOrderByContentDesc(requestedPage)
    }
}