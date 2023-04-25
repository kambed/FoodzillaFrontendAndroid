package pl.better.foodzilla.data.api.recipe

import androidx.paging.*
import pl.better.foodzilla.data.models.Recipe

class RecipePagingSource(
    private val recipeFlowClient: RecipeFlowClient,
    private val phrase: String
) : PagingSource<Int, Recipe>() {
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {
            val page = params.key ?: 1
            val response = recipeFlowClient.searchRecipes(phrase, page, params.loadSize)

            LoadResult.Page(
                data = response!!,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}