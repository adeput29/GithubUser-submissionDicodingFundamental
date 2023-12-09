package com.example.githubuser.database.remote.response;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllUserGitResponse {
    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("items")
    private List<GithubUser> githubUsers;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<GithubUser> getGithubUsers() {
        return githubUsers;
    }

    public void setGithubUsers(List<GithubUser> githubUsers) {
        this.githubUsers = githubUsers;
    }

    public class GithubUser{
        @SerializedName("avatar_url")
        private String avatar_url;

        @SerializedName("html_url")
        private String html_url;

        @SerializedName("login")
        private String login;
    }
}