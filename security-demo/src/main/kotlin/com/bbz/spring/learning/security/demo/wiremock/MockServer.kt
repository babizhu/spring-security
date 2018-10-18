package com.bbz.spring.learning.security.demo.wiremock


import com.bbz.spring.learning.security.demo.wiremock.MockServer.Companion.mock
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.matching.UrlPattern
import org.springframework.core.io.ClassPathResource

import java.nio.file.Files.readAllBytes
import java.nio.file.Paths.get

/**
 * MockServer实现，配合MockServer的standalone Jar包(http://wiremock.org/docs/running-standalone/)
 * 可以完成一个模拟服务器
 * 执行 java -jar xx.jar --port 8008
 *
 * 数据响应文件(Response内容)缺省放在resources/mock/response中，可根据实际情况更改
 *
 * 数据设置好之后，执行main函数即可
 */
class MockServer {
    companion object {
        private const val BASE_DIR = "mock/response/"
        private fun buildAbsolutePath(filePath: String): String {
            val resource = ClassPathResource("$BASE_DIR$filePath")
            return resource.file.absolutePath!!
        }

        /**
         * @param   url
         *          要测试访问的url
         * @param   filePath
         *          在resources下的相对路径
         */
        fun mock(url: String, filePath: String) {

            val content = String(readAllBytes(get(buildAbsolutePath(filePath))))
            WireMock.stubFor(WireMock.get(UrlPattern.fromOneOf(url, null, null, null))
                    .willReturn(aResponse().withStatus(200).withBody(content)))

        }
    }
}
// 不屏蔽无法用maven打包
//fun main(args: Array<String>) {
//    WireMock.configureFor(8001)
//    WireMock.removeAllMappings()
//    mock("/order/1", "order.json")
//
//}
