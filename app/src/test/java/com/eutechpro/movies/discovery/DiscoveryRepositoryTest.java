package com.eutechpro.movies.discovery;

import com.eutechpro.movies.Movie;
import com.eutechpro.movies.TestConsumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.BaseTestConsumer;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class DiscoveryRepositoryTest {
    private static final int PAGE = 11;
    private static final int YEAR = 2011;
    private DiscoveryRepository      repository;
    @Mock
    private DiscoveryApi             api;
    @Mock
    private RetrofitApi.Api.Response response;
    @Mock
    private List<Movie>              movies;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(api.fetchMovies(anyMap())).thenReturn(Observable.just(response));
        when(movies.size()).thenReturn(2);
        when(response.getResults()).thenReturn(movies);
        repository = new RetrofitRepository(api);
    }

    @Test
    public void testParametersSetting() throws Exception {
        ArgumentCaptor<Map<String, String>> captor = forClass(HashMap.class);

        repository.discoverMoviesByYear(YEAR, DiscoveryRepository.Sort.REVENUE_ASC, PAGE);

        verify(api).fetchMovies(captor.capture());
        Map<String, String> params = captor.getValue();
        assertEquals(String.valueOf(YEAR), params.get("year"));
        assertEquals(DiscoveryRepository.Sort.REVENUE_ASC, params.get("sort_by"));
        assertEquals(String.valueOf(PAGE), params.get("page"));
    }

    @Test
    public void testMappingResponse() throws Exception {
        TestConsumer<List<Movie>> testConsumer = new TestConsumer();
        repository.discoverMoviesByYear(YEAR, DiscoveryRepository.Sort.DEFAULT, PAGE).subscribe(testConsumer);
        testConsumer.awaitCount(1, BaseTestConsumer.TestWaitStrategy.SLEEP_10MS,500);

        assertEquals(movies, testConsumer.getContent());
    }
}