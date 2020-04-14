package com.ichi2.libanki.sync;

import android.content.SharedPreferences;

import com.ichi2.anki.AnkiDroidApp;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class HttpSyncerTest {
    private static String sCustomServerWithNoFormatting = "https://sync.example.com/";
    private static String sCustomServerWithFormatting   = "https://sync%s.example.com/";

    @Test
    public void getDefaultMediaUrlWithNoHostNum() {
        HttpSyncer underTest = getServerWithHostNum("");

        String syncUrl = underTest.syncURL();

        assertThat(syncUrl, is("https://sync.ankiweb.net/sync/"));
    }


    @Test
    @Ignore("Not yet supported")
    public void getDefaultMediaUrlWithHostNum() {
        HttpSyncer underTest = getServerWithHostNum("1");

        String syncUrl = underTest.syncURL();

        assertThat(syncUrl, is("https://sync1.ankiweb.net/sync/"));
    }


    @Test
    @Ignore("Not yet supported")
    public void getCustomMediaUrlWithNoHostNum() {
        HttpSyncer underTest = getServerWithHostNum("");
        setCustomServer(sCustomServerWithFormatting);

        String syncUrl = underTest.syncURL();

        assertThat(syncUrl, is("https://sync.example.com/sync/"));
    }


    @Test
    @Ignore("Not yet supported")
    public void getCustomMediaUrlWithHostNum() {
        HttpSyncer underTest = getServerWithHostNum("1");
        setCustomServer(sCustomServerWithFormatting);

        String syncUrl = underTest.syncURL();

        assertThat(syncUrl, is("https://sync1.example.com/sync/"));
    }

    @Test
    public void getUnformattedCustomMediaUrlWithHostNum() {
        HttpSyncer underTest = getServerWithHostNum("");
        setCustomServer(sCustomServerWithNoFormatting);

        String syncUrl = underTest.syncURL();

        assertThat(syncUrl, is("https://sync.example.com/sync/"));
    }

    @Test
    public void getUnformattedCustomMediaUrlWithNoHostNum() {
        HttpSyncer underTest = getServerWithHostNum("1");
        setCustomServer(sCustomServerWithNoFormatting);

        String syncUrl = underTest.syncURL();

        assertThat(syncUrl, is("https://sync.example.com/sync/"));
    }

    private void setCustomServer(String s) {

        SharedPreferences userPreferences = AnkiDroidApp.getSharedPrefs(AnkiDroidApp.getInstance());
        SharedPreferences.Editor e = userPreferences.edit();
        e.putBoolean("useCustomSyncServer", true);
        e.putString("syncBaseUrl", s);
        e.commit();
    }

    @NonNull
    private HttpSyncer getServerWithHostNum(String hostNum) {
        return new HttpSyncer(null, null, hostNum);
    }
}