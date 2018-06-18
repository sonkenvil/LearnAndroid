package com.something.kteam.learnandroid;

/**
 * Created by Nguyen Hung Son on 11/8/2017.
 */

public class VideoPlay {
    private String title;
    private String time;
    private String url;

    public String getTitle() {
        return title;
    }

    public VideoPlay(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public VideoPlay() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
