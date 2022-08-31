package ru.osipov;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NasaClass {
    private final String date;
    private final String explanation;
    private final String hdUrl;
    private final String mediaType;
    private final String serviceVersion;
    private final String title;
    private final String url;

    public NasaClass(
            @JsonProperty("date") String date,
            @JsonProperty("explanation") String explanation,
            @JsonProperty("hdurl") String hdUrl,
            @JsonProperty("media_type") String mediaType,
            @JsonProperty("service_version") String serviceVersion,
            @JsonProperty("title") String title,
            @JsonProperty("url") String url) {
        this.date = date;
        this.explanation = explanation;
        this.hdUrl = hdUrl;
        this.mediaType = mediaType;
        this.serviceVersion = serviceVersion;
        this.title = title;
        this.url = url;
    }

    public String getHdUrl() {
        return hdUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    @Override
    public String toString() {
        return "NasaClass{\n" +
                "date='" + date + "\'" + "\n"+
                "explanation='" + explanation + '\'' + "\n"+
                "hdUrl='" + hdUrl + '\'' + "\n"+
                "mediaType='" + mediaType + '\'' + "\n"+
                "service_version='" + serviceVersion + '\'' + "\n"+
                "title='" + title + '\'' + "\n"+
                "url='" + url + '\'' + "\n"+
                '}';
    }

    public String getExplanation() {
        return explanation;
    }

    public String getTitle() {

        return title.replace(" ", "_");
    }

    public String getUrl() {
        return url;
    }
}
