package com.xgx.demo.vo;

import java.io.Serializable;

/**
 * <p>
 * 宿舍签进表
 * </p>
 *
 * @author xgx
 * @since 2019-05-16
 */
public class SignLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 签到学生的id
     */
    private Integer userid;
    /**
     * 宿舍id
     */
    private Integer depid;
    /**
     * 识别分
     */
    private String confidence;
    /**
     * token
     */
    private String faceToken;
    private String faceUrl;
    /**
     * 状态(1：启用 2：冻结 3：删除）
     */
    private Integer status;
    /**
     * 创建时间
     */
    private String createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getDepid() {
        return depid;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
