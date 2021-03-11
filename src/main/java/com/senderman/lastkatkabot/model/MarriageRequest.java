package com.senderman.lastkatkabot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import java.util.Objects;

@TypeAlias("marriageRequest")
public class MarriageRequest {

    @Id
    private int id;
    private long proposerId;
    private String proposerName;
    private long proposeeId;
    private String proposeeName;
    private int requestDate;

    public MarriageRequest() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getProposerId() {
        return proposerId;
    }

    public void setProposerId(long proposerId) {
        this.proposerId = proposerId;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public long getProposeeId() {
        return proposeeId;
    }

    public void setProposeeId(long proposeeId) {
        this.proposeeId = proposeeId;
    }

    public String getProposeeName() {
        return proposeeName;
    }

    public void setProposeeName(String proposeeName) {
        this.proposeeName = proposeeName;
    }

    public int getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(int requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarriageRequest that = (MarriageRequest) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
