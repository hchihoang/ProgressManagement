package com.progress.management.network;
import com.progress.management.entity.request.LoginRequest;
import com.progress.management.entity.response.LoginResponse;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connection_Login extends Connection_Base {
    public Connection_Login(LoginRequest loginRequest,
                            DataCallback<LoginResponse> callback) {
        super();
        this.loginRequest = loginRequest;
        this.callback = callback;
    }
    LoginRequest loginRequest;
    DataCallback<LoginResponse> callback = null;
    LoginResponse loginResponse = new LoginResponse();

    @Override
    protected void connectionFinish() {
        // TODO Auto-generated method stub
        super.connectionFinish();
        if (connectSuccess){
            connectSuccessAction();
        }else{
            connectFailAction();
        }
    }

    @Override
    public void connectFailAction() {
        callback.onConnectFail(null);
    }

    @Override
    public void connectSuccessAction() {
        callback.onConnectSuccess(loginResponse);
    }

    @Override
    public void callStoreProcedure(Connection conn) throws SQLException{
        // TODO Auto-generated method stub
        super.callStoreProcedure(conn);

        String insertStoreProc = "{call [sp_LOGIN](?,?)}";
        CallableStatement callableStatement = conn.prepareCall(insertStoreProc);
        callableStatement.setString(1, loginRequest.getUserName());
        callableStatement.setString(2, loginRequest.getPassWork());
        callableStatement.execute();
        ResultSet reset = callableStatement.getResultSet();
        if (reset != null){
            while(reset.next()){
                loginResponse.setUsername(reset.getString("CAPTION"));
                loginResponse.setMaNV(reset.getString("MANV"));
                connectSuccess = true;
            }
        }
    }
}
