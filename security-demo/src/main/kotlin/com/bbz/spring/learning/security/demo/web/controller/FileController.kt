package com.bbz.spring.learning.security.demo.web.controller

import com.bbz.spring.learning.security.demo.dto.FileInfo
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.util.*
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/file")
class FileController {
    private val folder = "/Users/liukun/work/java/security/security-demo"
    @PostMapping
    fun upload(file: MultipartFile): FileInfo {
        println(file.name)
        println(file.originalFilename)
        println(file.size)


        val localFile = File(folder, "${Date().time}.txt")
        file.transferTo(localFile)
        return FileInfo(localFile.absolutePath)
    }

    @GetMapping("/{id}")
    fun download(@PathVariable id: String, request: HttpServletRequest, response: HttpServletResponse) {

        FileInputStream(File(folder, "$id.txt")).use { fileInputStream ->
            response.outputStream.use { outputStream ->

                fileInputStream.copyTo(outputStream)
                response.contentType = "application/x-download"
                response.addHeader("Content-Disposition", "attachment;filename=test.txt")
                outputStream.flush()

            }
        }


    }
}