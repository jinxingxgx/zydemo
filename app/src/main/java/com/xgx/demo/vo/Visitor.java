package com.xgx.demo.vo;

/**
 * <p>
 * 访客管理
 * </p>
 *
 * @author xgx
 * @since 2019-05-18
 */
public class Visitor {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 访问原因
     */
    private String content;
    /**
     * 接访宿管id
     */
    private Integer userid;
    /**
     * 接访宿管
     */
    private String oper;
    /**
     * 访问时间
     */
    private String createtime;
    private Integer depId;

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Visitor{" + ", id=" + id + ", name=" + name + ", content=" + content + ", userid=" + userid + ", oper=" + oper + ", createtime=" + createtime + "}";
    }
}
