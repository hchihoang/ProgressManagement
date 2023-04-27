package com.progress.management.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

public abstract class Connection_Base {
    private static final String Tag = "Connection_Base";
    private String connectionString;
    private String user;
    private String pass;
    private String ip;
    private String port;
    private String database;
    protected Context context;
    protected boolean connectSuccess;
    protected boolean searchingIsDone;
    protected boolean networkIsError;
    protected ProgressDialog pdialog;
    //    protected String tableName;
    private ConnectToServer connectToServer;
    private Connection conn = null;

    public Connection_Base(Context context) {
        connectSuccess = true;
        searchingIsDone = false;
        networkIsError = false;
        this.context = context;
//            ip = Funtion.getDataNote(context, Variable.IP);
//            port = Funtion.getDataNote(context, Variable.Port);
//            user = Funtion.getDataNote(context, Variable.User);
//            pass = Funtion.getDataNote(context, Variable.Pass);
//            database = Funtion.getDataNote(context, Variable.Database);
        connectionString = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database + ";user=" + user + ";password=" + pass;
        connectToServer = new ConnectToServer();
    }

    /*
    Connect to server, return Result Set after select table: tableName
     */
    private void connection() {
        try {
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connectionString, user, pass);
            callStoreProcedure(conn);
        } catch (SQLException e) {
            Log.w(Tag, e);
            connectSuccess = false;
        } catch (Exception e) {
            Log.w(Tag, e);
            connectSuccess = false;
        }
    }

    public void callStoreProcedure(Connection conn2) throws SQLException {
    }

//	protected abstract void getDataInTable(Statement stmt) throws SQLException;

    private void startDialogProgress() {
        if (getDialogProgressText() != null) {
            pdialog = new ProgressDialog(context);
            pdialog.setMessage(getDialogProgressText());
            pdialog.setCanceledOnTouchOutside(false);
            pdialog.setCancelable(false);
            pdialog.show();
            TextView message = (TextView) pdialog.findViewById(android.R.id.message);
            //message.setTextColor(context.getResources().getColor(R.color.color_pmv_orange));
        }
    }

    protected abstract String getDialogProgressText();

    public abstract void connectFailAction();

    public abstract void connectSuccessAction();


    private class ConnectToServer extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (isWifiConnected()) {
                connection();
            } else {
                networkIsError = true;
                connectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void searchingIsDone) {
            if (pdialog != null) {
                pdialog.dismiss();
                pdialog = null;
            }
            connectionFinish();
        }

        @Override
        protected void onPreExecute() {
            startDialogProgress();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    protected void connectionFinish() {
    }

    public void closeDatabase() {
        CloseConnection closeConnection = new CloseConnection(conn);
        closeConnection.execute();
    }

    public void execute() {
        connectToServer.execute();
    }

    public void cancel() {
        connectToServer.cancel(true);
    }

    private class CloseConnection extends AsyncTask<Void, Void, Void> {

        public Connection conn;

        public CloseConnection(Connection conn) {
            this.conn = conn;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if ((conn != null) && (!conn.isClosed())) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                Log.w(Tag, e);
            }
            return null;
        }
    }

    private boolean isWifiConnected() {
        return true;
    }

}
