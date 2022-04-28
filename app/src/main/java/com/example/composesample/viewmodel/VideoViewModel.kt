package com.example.composesample.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.composesample.model.entity.VideoEntity
import com.example.composesample.model.service.VideoService

class VideoViewModel : ViewModel() {

    private val videoService = VideoService.instance()

    var list by mutableStateOf(
        listOf(
            VideoEntity(
                title = "行测老师告诉你如何制定适合自己的学习方案",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://docs.bughub.icu/compose/assets/banner1.webp"
            ),
            VideoEntity(
                title = "行测老师告诉你如何制定适合自己的学习方案",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://docs.bughub.icu/compose/assets/banner2.webp"
            ),
            VideoEntity(
                title = "行测老师告诉你如何制定适合自己的学习方案",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://docs.bughub.icu/compose/assets/banner3.webp"
            ),
            VideoEntity(
                title = "行测老师告诉你如何制定适合自己的学习方案",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://docs.bughub.icu/compose/assets/banner4.jpg"
            ),
            VideoEntity(
                title = "行测老师告诉你如何制定适合自己的学习方案",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://docs.bughub.icu/compose/assets/banner5.jpg"
            ),
            VideoEntity(
                title = "行测老师告诉你如何制定适合自己的学习方案",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://docs.bughub.icu/compose/assets/banner1.webp"
            ),
            VideoEntity(
                title = "行测老师告诉你如何制定适合自己的学习方案",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://docs.bughub.icu/compose/assets/banner1.webp"
            ),
            VideoEntity(
                title = "行测老师告诉你如何制定适合自己的学习方案",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://docs.bughub.icu/compose/assets/banner1.webp"
            )
        )
    )
        private set

    private val pageSize = 20
    private var pageOffset = 1

    var refreshing by mutableStateOf(false)
        private set

    private var hasMore = false
    var listLoaded by mutableStateOf(false)
        private set

    suspend fun fetchList() {
        val res = videoService.list(pageSize = pageSize, pageOffset = pageOffset)
        if (res.code == 0 && res.data != null) {
            val tmpList = mutableListOf<VideoEntity>()
            if (pageOffset != 1) {
                tmpList.addAll(list)
            }
            tmpList.addAll(res.data)
            //是否还有更多数据
            hasMore = res.data.size == pageSize
            list = tmpList
            listLoaded = true
            refreshing = false
        } else {
            pageOffset--
            if (pageOffset <= 1) {
                pageOffset = 1
            }
        }
    }

    suspend fun refresh() {
        pageOffset = 1
        refreshing = true
        fetchList()
    }

    suspend fun loadMore() {
        if (hasMore) {
            pageOffset++
            fetchList()
        }
    }

    var videoUrl by mutableStateOf("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4")
        private set

    var coverUrl by mutableStateOf("https://img1.baidu.com/it/u=1875739781,4152007440&fm=253&fmt=auto&app=120&f=JPEG?w=1024&h=576")
        private set

    private var videoTitle by mutableStateOf("习近平@战“疫”一线的这些群体：你们为疫")

    //HTML 头部
    private val htmlHeader = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title></title>
            <style>
                img {
                    max-width: 100% !important;
                }
            </style>
        </head>
        <body>
    """.trimIndent()

    //html尾部
    private val htmlFooter = """
        </body>
        </html>
    """.trimIndent()

    var videoDesc by mutableStateOf(
        """$htmlHeader
      <h5 style="color:#333333;font-size:16px;">$videoTitle</h5>
    $htmlFooter
    """.trimIndent()
    )

    var infoLoaded by mutableStateOf(false)
        private set

    suspend fun fetchInfo() {
        val res = videoService.info("")
        if (res.code == 0 && res.data != null) {
            val videoEntity = res.data
            videoTitle = videoEntity.title
            coverUrl = videoEntity.imageUrl
            videoUrl = videoEntity.video ?: ""
            videoDesc = """$htmlHeader
                      <h5 style="color:#333333;font-size:16px;">$videoTitle</h5>
                      ${res.data.desc}
                    $htmlFooter
                    """.trimIndent()
            infoLoaded = true
        }
    }
}