package ec.espe.monster.views;

import java.awt.event.ActionListener;

public interface ILoginView {

    String getUsername();

    String getPassword();

    void setSubmitListener(ActionListener listener);

    void showErrorMessage(String message);

    void resetForm();

    void showView();

    void hideView();
}