package ru.ipimenov.myviolets.utils;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkUtils {

    private static final String BASE_URL = "https://myfialka.ru";
    // Адрес каталога Российская и Украинская селекция
    private static final String RU_UA_URL = "/index.php/senpolii-standarty/rossijskaya-i-ukrainskaya-selektsiya";
    // Адрес катклога Зарубежная селекция
    private static final String FOREIGN_URL = "/index.php/senpolii-standarty/zarubezhnaya-selektsiya";
    // Адрес каталога Миниатюры и Трейлеры
    private static final String MINI_URL = "/index.php/miniatyury-i-trejlery";
    // Параметр страницы каталога
    private static final String PARAMS_PAGE = "start";
    // Начало страницы
    private static final String PAGE_START = "<div class=\"items-leading clearfix\">";
    // Конец страницы
    private static final String PAGE_FINISH = "<div class=\"pagination\">";
    // Идентификаторы каталогов
    public static final int RU_UA_SELECTION = 0;
    public static final int FOREIGN_SELECTION = 1;
    public static final int MINI_SELECTION = 2;

    public static String getBaseUrl() {
        return BASE_URL;
    }

    private static URL buildURL(int catalog, int page) {
        URL result = null;
        String url = "";
        String pageOfCatalog = "";
        switch (catalog) {
            case RU_UA_SELECTION:
                url = RU_UA_URL;
                break;
            case FOREIGN_SELECTION:
                url = FOREIGN_URL;
                break;
            case MINI_SELECTION:
                url = MINI_URL;
                break;
        }
        if (page > 0) {
            pageOfCatalog = (page - 1) + "0";
        } else {
            pageOfCatalog = "00";
        }
        Uri uri = Uri.parse(BASE_URL + url).buildUpon()
                .appendQueryParameter(PARAMS_PAGE, pageOfCatalog).build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getContentFromNetwork(int catalog, int page) {
        String result = null;
        URL url = buildURL(catalog, page);
        try {
            result = new ContentLoadTask().execute(url).get();
            Pattern pattern = Pattern.compile(PAGE_START + "(.*?)" + PAGE_FINISH);
            Matcher matcher = pattern.matcher(result);
            result = matcher.find() ? matcher.group(1) : null;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class ContentLoadTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String result = null;
            if (urls == null || urls.length == 0) {
                return result;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                result = builder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }
    }
}
