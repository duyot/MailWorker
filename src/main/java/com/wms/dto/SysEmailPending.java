package com.wms.dto;

/**
 * Created by duyot on 12/6/2016.
 */
public class SysEmailPending {
    private String id;
    private String fromAddr;
    private String toAddr;
    private String subject;
    private String content;
    private String attachPath;
    private String schedule_time;
    private String status;
    private String createdDate;

    public SysEmailPending() {
    }

    public SysEmailPending(String id, String fromAddr, String toAddr, String subject, String content, String attachPath, String schedule_time, String status, String createdDate) {
        this.id = id;
        this.fromAddr = fromAddr;
        this.toAddr = toAddr;
        this.subject = subject;
        this.content = content;
        this.attachPath = attachPath;
        this.schedule_time = schedule_time;
        this.status = status;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }

    public String getSchedule_time() {
        return schedule_time;
    }

    public void setSchedule_time(String schedule_time) {
        this.schedule_time = schedule_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
