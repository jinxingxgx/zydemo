package com.xgx.demo.vo;

/**
 * <p>
 * 请假管理
 * </p>
 *
 * @author xgx
 * @since 2019-05-16
 */
public class Approve {

    private static final long serialVersionUID = 1L;

    /**
     * 考勤id
     */
    private Integer id;
    /**
     * 请假标题
     */
    private String title;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 请假内容
     */
    private String detail;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 考勤状态
     */
    private Integer status;
    /**
     * 操作员id
     */
    private Integer operId;
    /**
     * 用户id
     */
    private Integer userId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
