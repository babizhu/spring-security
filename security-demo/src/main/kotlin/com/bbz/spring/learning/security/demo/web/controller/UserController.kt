package com.bbz.spring.learning.security.demo.web.controller

import com.bbz.spring.learning.security.demo.dto.User
import com.bbz.spring.learning.security.demo.dto.UserQueryCondition
import com.fasterxml.jackson.annotation.JsonView
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/user")
class UserController {

    @GetMapping("/me")
    fun getCurrentUser(@AuthenticationPrincipal user: UserDetails, authentication: Authentication): Any {
        println("UserDetails is $user")
        return authentication
//        return  SecurityContextHolder.getContext().authentication
    }

    @PostMapping
    fun create(@Valid @RequestBody user: User/*, errors: BindingResult*/): User {
//        if (errors.hasErrors()) {
//            errors.allErrors.forEach { println(it.defaultMessage) }
//        }
        println(user)
        user.id = "1"
        return user
    }

    @PutMapping("/{id:\\d+}")
    fun update(@Valid @RequestBody user: User, errors: BindingResult): User {
        if (errors.hasErrors()) {
            errors.allErrors.forEach {
                println((it as FieldError).field + " " + it.defaultMessage)
            }
        }
        println(user)
        user.id = "1"
        return user
    }

    @DeleteMapping("/{id:\\d+}")
    fun delete(@PathVariable id: String): String {
        println("@DeleteMapping, pathVariable is $id")
        return "ok"
    }

    @GetMapping
    @JsonView(User.UserSimpleView::class)
    @ApiOperation("用户查询服务")
    fun query(condition: UserQueryCondition,
              @PageableDefault(page = 1, size = 15, sort = ["username asc"]) pageable: SpringDataWebProperties.Pageable): List<User> {

        println("condition = $condition")
        println("page = $pageable")

        return listOf(User("1", "bbz", "password")
                , User("2", "liulaoye", "password")
                , User("3", "lk", "password")
        )
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetaileView::class)
    @ApiOperation("某个具体用户查询服务")
    fun getInfo(@ApiParam("要查询的用户ID") @PathVariable id: String): User? {
//        throw UserNotExistException(id)
        return User("1", "liulaoye", "pass", age = 100)
    }
}