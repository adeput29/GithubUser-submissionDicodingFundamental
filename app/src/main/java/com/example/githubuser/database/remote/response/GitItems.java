package com.example.githubuser.database.remote.response;

import com.google.gson.annotations.SerializedName;

public class GitItems {

	@SerializedName("gists_url")
	private String gistsUrl;

	@SerializedName("repos_url")
	private String reposUrl;

	@SerializedName("following_url")
	private String followingUrl;

	@SerializedName("starred_url")
	private String starredUrl;

	@SerializedName("login")
	private String login;

	@SerializedName("followers_url")
	private String followersUrl;

	@SerializedName("type")
	private String type;

	@SerializedName("url")
	private String url;

	@SerializedName("subscriptions_url")
	private String subscriptionsUrl;

	@SerializedName("score")
	private Object score;

	@SerializedName("received_events_url")
	private String receivedEventsUrl;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("events_url")
	private String eventsUrl;

	@SerializedName("html_url")
	private String htmlUrl;

	@SerializedName("site_admin")
	private boolean siteAdmin;

	@SerializedName("id")
	private int id;

	@SerializedName("gravatar_id")
	private String gravatarId;

	@SerializedName("node_id")
	private String nodeId;

	@SerializedName("organizations_url")
	private String organizationsUrl;

	@SerializedName("name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGistsUrl(){
		return gistsUrl;
	}

	public String getReposUrl(){
		return reposUrl;
	}

	public String getFollowingUrl(){
		return followingUrl;
	}

	public String getStarredUrl(){
		return starredUrl;
	}

	public String getLogin(){
		return login;
	}

	public String getFollowersUrl(){
		return followersUrl;
	}

	public String getType(){
		return type;
	}

	public String getUrl(){
		return url;
	}

	public String getSubscriptionsUrl(){
		return subscriptionsUrl;
	}

	public Object getScore(){
		return score;
	}

	public String getReceivedEventsUrl(){
		return receivedEventsUrl;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public String getEventsUrl(){
		return eventsUrl;
	}

	public String getHtmlUrl(){
		return htmlUrl;
	}

	public boolean isSiteAdmin(){
		return siteAdmin;
	}

	public int getId(){
		return id;
	}

	public String getGravatarId(){
		return gravatarId;
	}

	public String getNodeId(){
		return nodeId;
	}

	public String getOrganizationsUrl(){
		return organizationsUrl;
	}
}