package reustonium.com.lunchpals.ui.main;

import java.util.List;

public interface MainMvpView {

    void showPals(List<String> pals);

    void showPalsEmpty();

    void showError();
}
