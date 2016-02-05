package com.reustonium.lunchpals.pals;

import com.google.common.collect.Lists;

import com.reustonium.lunchpals.data.Pal;
import com.reustonium.lunchpals.data.PalsRepository;
import com.reustonium.lunchpals.data.PalsRepository.LoadPalsCallback;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Andrew on 2/5/2016.
 */
public class PalsPresenterTest {

    private static List<Pal> PALS = Lists.newArrayList(new Pal("Title1", "Description1"),
            new Pal("Title2", "Description2"));

    private static List<Pal> EMPTY_PALS = new ArrayList<>(0);

    @Mock
    private PalsRepository mPalsRepository;

    @Mock
    private PalsContract.View mPalsView;

    @Captor
    private ArgumentCaptor<LoadPalsCallback> mLoadPalsCallbackCaptor;

    private PalsPresenter mPalsPresenter;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mPalsPresenter = new PalsPresenter(mPalsRepository, mPalsView);
    }

    @Test
    public void testLoadPalsAndLoadIntoView() throws Exception {
        // Given an initialized PalsPresenter with initialized pals
        // When loading of Pals is requested
        mPalsPresenter.loadPals(true);

        // Callback is captured and invoked with stubbed pals
        verify(mPalsRepository).getPals(mLoadPalsCallbackCaptor.capture());
        mLoadPalsCallbackCaptor.getValue().onPalsLoaded(PALS);

        // Then progress indicator is hidden and pals are shown in UI
        verify(mPalsView).setProgressIndicator(false);
        verify(mPalsView).showPals(PALS);
    }

    @Test
    public void testClickFABShowAddNewPalSnackbar() throws Exception {
        // When adding a new pal
        mPalsPresenter.addNewPal();

        // Then add pal UI is shown
        verify(mPalsView).showAddPal();
    }

    @Test
    public void testClickPalAndOpenPalDetails() throws Exception {
        // Given a stubbed pal
        Pal requestedPal = new Pal("Details Requested", "For this pal");

        // When open pal details is requested
        mPalsPresenter.openPalDetails(requestedPal);

        // Then pal detail UI is shown
        verify(mPalsView).showPalDetailUi(any(String.class));
    }
}