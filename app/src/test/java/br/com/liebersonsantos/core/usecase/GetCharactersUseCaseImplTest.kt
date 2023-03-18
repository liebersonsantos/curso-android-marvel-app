package br.com.liebersonsantos.core.usecase

import androidx.paging.PagingConfig
import br.com.liebersonsantos.core.data.repository.CharactersRepository
import com.liebersonsantos.testing.MainCoroutineRule
import com.liebersonsantos.testing.model.CharacterFactory
import com.liebersonsantos.testing.pagingsource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: CharactersRepository
    private lateinit var useCase: GetCharactersUseCase
    private val hero = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val fakePagingSource = PagingSourceFactory().create(listOf(hero))

    @Before
    fun setup(){
        useCase = GetCharactersUseCaseImpl(repository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() = runTest {

        whenever(repository.getCharacters("")).thenReturn(fakePagingSource)

        val result = useCase.invoke(GetCharactersUseCase.GetUseCharactersParams("", PagingConfig(20)))

        //funcao verify do mockito verifica se o metodo foi chamado. O times verifica quantas vezes esse metodo foi chamado, se colocar zero, significa que nao foi chamado
        verify(repository).getCharacters("")

        assertNotNull(result.first())

        //assertEquals(1, result.count() ou result.colect) //esse teste vai dar erro de job
    }




}