package com.foddie.food.models;

public class ReviewModel {

    private String reviewId;
    private String reviewComment;
    private String reviewGivenBy;
    private String reviewGivenByEmail;
    private String reviewRating;
    private String restrauntId;
    private String reviewDate;

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewGivenBy() {
        return reviewGivenBy;
    }

    public void setReviewGivenBy(String reviewGivenBy) {
        this.reviewGivenBy = reviewGivenBy;
    }

    public String getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(String reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getRestrauntId() {
        return restrauntId;
    }

    public void setRestrauntId(String restrauntId) {
        this.restrauntId = restrauntId;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewGivenByEmail() {
        return reviewGivenByEmail;
    }

    public void setReviewGivenByEmail(String reviewGivenByEmail) {
        this.reviewGivenByEmail = reviewGivenByEmail;
    }
}
