package com.list.show.pojo;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmObject;

public class RootObject  extends RealmObject
{

  private String url;

  public String getUrl() { return this.url; }

  public void setUrl(String url) { this.url = url; }

  private String repository_url;

  public String getRepositoryUrl() { return this.repository_url; }

  public void setRepositoryUrl(String repository_url) { this.repository_url = repository_url; }

  private String labels_url;

  public String getLabelsUrl() { return this.labels_url; }

  public void setLabelsUrl(String labels_url) { this.labels_url = labels_url; }

  private String comments_url;

  public String getCommentsUrl() { return this.comments_url; }

  public void setCommentsUrl(String comments_url) { this.comments_url = comments_url; }

  private String events_url;

  public String getEventsUrl() { return this.events_url; }

  public void setEventsUrl(String events_url) { this.events_url = events_url; }

  private String html_url;

  public String getHtmlUrl() { return this.html_url; }

  public void setHtmlUrl(String html_url) { this.html_url = html_url; }

  private int id;

  public int getId() { return this.id; }

  public void setId(int id) { this.id = id; }

  private String node_id;

  public String getNodeId() { return this.node_id; }

  public void setNodeId(String node_id) { this.node_id = node_id; }

  private int number;

  public int getNumber() { return this.number; }

  public void setNumber(int number) { this.number = number; }

  private String title;

  public String getTitle() { return this.title; }

  public void setTitle(String title) { this.title = title; }

  private User user;

  public User getUser() { return this.user; }

  public void setUser(User user) { this.user = user; }



  private String state;

  public String getState() { return this.state; }

  public void setState(String state) { this.state = state; }

  private boolean locked;

  public boolean getLocked() { return this.locked; }

  public void setLocked(boolean locked) { this.locked = locked; }


  private String milestone;

  public String getMilestone() { return this.milestone; }

  public void setMilestone(String milestone) { this.milestone = milestone; }

  private int comments;

  public int getComments() { return this.comments; }

  public void setComments(int comments) { this.comments = comments; }

  private Date created_at;

  public Date getCreatedAt() { return this.created_at; }

  public void setCreatedAt(Date created_at) { this.created_at = created_at; }

  private Date updated_at;

  public Date getUpdatedAt() { return this.updated_at; }

  public void setUpdatedAt(Date updated_at) { this.updated_at = updated_at; }

  private String closed_at;

  public String getClosedAt() { return this.closed_at; }

  public void setClosedAt(String closed_at) { this.closed_at = closed_at; }

  private String author_association;

  public String getAuthorAssociation() { return this.author_association; }

  public void setAuthorAssociation(String author_association) { this.author_association = author_association; }

  private PullRequest pull_request;

  public PullRequest getPullRequest() { return this.pull_request; }

  public void setPullRequest(PullRequest pull_request) { this.pull_request = pull_request; }

  private String body;

  public String getBody() { return this.body; }

  public void setBody(String body) { this.body = body; }
}
