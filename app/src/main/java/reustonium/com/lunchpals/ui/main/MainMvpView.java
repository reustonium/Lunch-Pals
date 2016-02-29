package reustonium.com.lunchpals.ui.main;

import java.util.List;

import reustonium.com.lunchpals.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showPals(List<String> pals);

    void showPalsEmpty();

    void showError();
}
