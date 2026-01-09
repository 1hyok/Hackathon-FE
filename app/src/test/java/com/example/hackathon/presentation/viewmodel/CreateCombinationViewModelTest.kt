package com.example.hackathon.presentation.viewmodel

import app.cash.turbine.test
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.User
import com.example.hackathon.domain.repository.CombinationRepository
import com.example.hackathon.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * CreateCombinationViewModel 단위 테스트
 * 해커톤 특성상 Happy Path 중심으로 테스트
 */
class CreateCombinationViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CreateCombinationViewModel
    private val repository: CombinationRepository = mockk()

    @Before
    fun setup() {
        viewModel = CreateCombinationViewModel(repository)
    }

    @Test
    fun `제목과 설명 입력 시 상태가 업데이트된다`() =
        runTest {
            // Given
            val title = "서브웨이 꿀조합"
            val description = "에그마요에 아보카도 추가"

            // When
            viewModel.updateTitle(title)
            viewModel.updateDescription(description)

            // Then
            viewModel.uiState.test {
                val state = awaitItem()
                assertEquals(title, state.title)
                assertEquals(description, state.description)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `태그 추가 시 태그 리스트에 추가된다`() =
        runTest {
            // Given
            val tag = "#서브웨이"

            // When
            viewModel.addTag(tag)

            // Then
            viewModel.uiState.test {
                val state = awaitItem()
                assertEquals(1, state.tags.size)
                assertEquals(tag, state.tags.first())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `조합 등록 성공 시 로딩 상태가 변경된다`() =
        runTest {
            // Given
            val mockCombination =
                Combination(
                    id = "1",
                    title = "테스트 조합",
                    description = "테스트 설명",
                    imageUrl = null,
                    category = Category.SUBWAY,
                    ingredients = listOf("재료1 수량1", "재료2 수량2"),
                    steps = listOf("단계1", "단계2"),
                    tags = emptyList(),
                    author = User(id = "user1", nickname = "테스트", profileImageUrl = null),
                    likeCount = 0,
                    createdAt = "2024-01-01",
                )

            viewModel.updateTitle("테스트 조합")
            viewModel.updateDescription("테스트 설명")
            viewModel.updateIngredientName(0, "재료1")
            viewModel.updateIngredientQuantity(0, "수량1")
            viewModel.updateIngredientName(1, "재료2")
            viewModel.updateIngredientQuantity(1, "수량2")
            viewModel.updateSteps("단계1\n단계2")

            coEvery {
                repository.createCombination(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                )
            } returns Result.success(mockCombination)

            var successCallbackCalled = false

            // When
            viewModel.createCombination {
                successCallbackCalled = true
            }
            this.advanceUntilIdle()

            // Then
            val finalState = viewModel.uiState.value
            assertEquals(false, finalState.isLoading)
            assertEquals(null, finalState.error)
            assertEquals(true, successCallbackCalled)
        }

    @Test
    fun `제목이 비어있으면 에러 메시지가 표시된다`() =
        runTest {
            // Given
            viewModel.updateTitle("")
            viewModel.updateDescription("설명")
            viewModel.updateIngredientName(0, "재료1")
            viewModel.updateIngredientQuantity(0, "수량1")
            viewModel.updateSteps("단계1")

            // When
            viewModel.createCombination {}

            // Then
            viewModel.uiState.test {
                val state = awaitItem()
                assertEquals("제목과 설명을 입력해주세요", state.error)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `재료가 비어있으면 에러 메시지가 표시된다`() =
        runTest {
            // Given
            viewModel.updateTitle("제목")
            viewModel.updateDescription("설명")
            // ingredientsList는 기본값으로 빈 IngredientItem("", "")이 있으므로
            // name과 quantity가 모두 비어있어서 validIngredients가 비어있게 됨
            viewModel.updateSteps("단계1")

            // When
            viewModel.createCombination {}

            // Then
            viewModel.uiState.test {
                val state = awaitItem()
                assertEquals("재료를 입력해주세요", state.error)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `조합 등록 실패 시 에러 상태가 표시된다`() =
        runTest {
            // Given
            viewModel.updateTitle("제목")
            viewModel.updateDescription("설명")
            viewModel.updateIngredientName(0, "재료1")
            viewModel.updateIngredientQuantity(0, "수량1")
            viewModel.updateSteps("단계1")

            coEvery {
                repository.createCombination(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                )
            } returns Result.failure(Exception("네트워크 오류"))

            // When
            viewModel.createCombination {}
            this.advanceUntilIdle()

            // Then
            val errorState = viewModel.uiState.value
            assertEquals(false, errorState.isLoading)
            assertEquals("네트워크 오류", errorState.error)
        }
}
