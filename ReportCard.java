package com.example.android.miwok;

/**
 * Created by priyankanandiraju on 10/16/16.
 */

public class ReportCard {
    private static final String TAG = ReportCard.class.getSimpleName();
    private String mCourseCode;
    private String mCourseName;
    private char mGrade;
    private int mCredits;
    private double mTotalGpa;

    public ReportCard(String mCourseCode, String mCourseName, char mGrade, int mCredits) {
        this.mCourseCode = mCourseCode;
        this.mCourseName = mCourseName;
        this.mGrade = mGrade;
        this.mCredits = mCredits;
    }

    public ReportCard(double mTotalGpa) {
        this.mTotalGpa = mTotalGpa;
    }

    public String getCourseCode() {
        return mCourseCode;
    }

    public void setCourseCode(String courseCode) {
        mCourseCode = courseCode;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(String courseName) {
        mCourseName = courseName;
    }

    public char getGrade() {
        return mGrade;
    }

    public void setGrade(char grade) {
        mGrade = grade;
    }

    public int getCredits() {
        return mCredits;
    }

    public void setCredits(int credits) {
        mCredits = credits;
    }

    public double getTotalGpa() {
        return mTotalGpa;
    }

    public void setTotalGpa(float totalGpa) {
        mTotalGpa = totalGpa;
    }

    public String getReportCard() {
        return toString();
    }

    @Override
    public String toString() {
        return "ReportCard{" +
                "mCourseCode='" + mCourseCode + '\'' +
                ", mCourseName='" + mCourseName + '\'' +
                ", mGrade=" + mGrade +
                ", mCredits=" + mCredits +
                ", mTotalGpa=" + mTotalGpa +
                '}';
    }
}
