package com.example.myapplication

import com.example.myapplication.ApiService
import com.example.myapplication.Image
import com.example.myapplication.ImageRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever

class ImageRepositoryTest {

    private lateinit var repository: ImageRepository
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        apiService = mock(ApiService::class.java)
        repository = ImageRepository(apiService)
    }

    @Test
    fun `fetchImages should return list of images from apiService`(): Unit = runBlocking {
        // Given
        val mockImages = listOf(
            Image(1, "Title 1", "url1", "thumbnailUrl1"),
            Image(2, "Title 2", "url2", "thumbnailUrl2")
        )
        whenever(apiService.getPhotos()).thenReturn(mockImages)

        // When
        val result = repository.fetchImages()

        // Then
        assert(result == mockImages)
        verify(apiService).getPhotos()
    }

    @Test(expected = RuntimeException::class)
    fun `fetchImages should throw an exception when apiService fails`(): Unit = runBlocking {
        // Given
        whenever(apiService.getPhotos()).thenThrow(RuntimeException("Network Error"))

        // When
        repository.fetchImages() // This should throw an exception

        // Then exception is thrown
    }
}
