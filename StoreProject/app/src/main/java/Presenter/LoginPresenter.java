package Presenter;


public class LoginPresenter  {
    iLogin iLogin;

    public LoginPresenter(Presenter.iLogin iLogin) {
        this.iLogin = iLogin;
    }

    public void onLogin(String username, String password){
        if(username.equals("QuangLM@gmail.com") && password.equals("123456"))
            iLogin.onLoginSuccess("Đăng nhập thành công");
        else iLogin.onLoginError("Đăng nhập thất bại");
    }
}
