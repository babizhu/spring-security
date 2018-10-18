package com.bbz.spring.learning.security.demo.web.controller

import org.intellij.lang.annotations.Language
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.nio.charset.Charset
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoField
import java.time.temporal.Temporal
import java.time.temporal.TemporalField
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun whenQuerySuccess() {
        val response = this.mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("username", "liulaoye")
                .param("age", "31")
                .param("size", "15")
                .param("page", "3")
                .param("sort", "age,desc")
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn().response.contentAsString
        println(response)
    }

    @Test
    fun whenGetInfoSuccess() {
        val response = mvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.username").value("liulaoye"))
                .andReturn().response.contentAsString
        println(response)
    }

    @Test
    fun whenGetInfoFail() {
        mvc.perform(get("/user/a").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError)
    }

    @Test
    fun whenCreateSuccess() {
        val milli = Date().time
        @Language("JSON")
        val content = "{\"username\":\"liulaoye\",\"password\":\"pass\",\"age\":100,\"birthday\":$milli}"
        val response = mvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().response.contentAsString
        println(response)
    }

    @Test
    fun whenUpdateSuccess() {
        val milli = Date().time + 1000000000
//        val milli = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()
        @Language("JSON")
        val content = "{\"id\":\"1\",\"username\":\"liulaoye\",\"password\":\"pass\",\"age\":100,\"birthday\":$milli}"
        val response = mvc.perform(put("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().response.contentAsString
        println(response)
    }

    @Test
    fun whenDeleteSuccess() {

        mvc.perform(delete("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk)

    }
    @Test
fun whenUploadSuccess(){
        val response = mvc.perform(multipart("/file")
                .file(MockMultipartFile("file", "test.txt", "multipart/form-data", "hello upload".toByteArray(Charsets.UTF_8)))
        )
                .andExpect(status().isOk)
                .andReturn().response.contentAsString
        println(response)
    }
}