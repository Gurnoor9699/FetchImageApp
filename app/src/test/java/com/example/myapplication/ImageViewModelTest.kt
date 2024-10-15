package com.example.myapplication
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.myapplication.Image
import com.example.myapplication.ImageRepository
import com.example.myapplication.ImageViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.mockito.kotlin.whenever

class ImageViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ImageViewModel
    private lateinit var repository: ImageRepository
    private lateinit var observer: Observer<List<Image>>

    @Before
    fun setup() {
        repository = mock(ImageRepository::class.java)
        viewModel = ImageViewModel(repository)
        observer = mock(Observer::class.java) as Observer<List<Image>>
        viewModel.images.observeForever(observer)
    }

    @Test
    fun `loadImages should update LiveData when repository returns data`() = runBlocking {
        // Given
        val mockImages = listOf(
            Image(1, "Title 1", "url1", "thumbnailUrl1"),
            Image(2, "Title 2", "url2", "thumbnailUrl2")
        )
        whenever(repository.fetchImages()).thenReturn(mockImages)

        // When
        viewModel.loadImages()

        // Then
        verify(observer).onChanged(mockImages)
    }

    @Test
    fun `loadImages should handle exceptions gracefully`() = runBlocking {
        // Given
        whenever(repository.fetchImages()).thenThrow(RuntimeException("Network Error"))

        // When
        viewModel.loadImages()

        // Then
        verify(observer, never()).onChanged(anyList())
        // Additional error handling can be tested if you expose an error LiveData
    }
}
