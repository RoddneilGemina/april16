package com.example.csit228_f1_v2;

public class Post {
    private int postid = -1;
    private int accountid = -1;
    private String contents = "";

    public Post(int postid, int accountid, String contents){
        this.postid = postid;
        this.accountid = accountid;
        this.contents = contents;
    }

    public int getId(){
        return postid;
    }
    public int getUserid(){
        return accountid;
    }

    public String getContents(){
        return contents;
    }
}
