package com.example.githubuser.database.remote.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GitResponse {

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("incomplete_results")
	private boolean incompleteResults;

	@SerializedName("items")
	private List<GitItems> items;

	public int getTotalCount(){
		return totalCount;
	}

	public boolean isIncompleteResults(){
		return incompleteResults;
	}

	public List<GitItems> getItems(){
		return items;
	}
}