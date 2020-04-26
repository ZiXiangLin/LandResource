package com.gis.demo.domain.ModelParament;

public class Result {
    //响应码
    private int code;
    private String Login;
    private int authority;
    private String Email;
    private String redirect;

    public Result(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    public String getLogin(){return Login;}
    public int getAuthority(){return authority;}
    public String getEmail(){return Email;}
    public String getRedirect() {
        return redirect;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public void setLogin(String login){
        this.Login = login;
    }
    public void setAuthority(int aUthority){
        this.authority =aUthority;
    }
    public void setEmail(String email){
        this.Email =email;
    }
    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
