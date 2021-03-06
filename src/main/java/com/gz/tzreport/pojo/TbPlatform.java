package com.gz.tzreport.pojo;

import java.util.Date;

public class TbPlatform {

    public TbPlatform(String platformName){
        this.platformName = platformName;
    }

    public TbPlatform(){

    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_platform.platform_id
     *
     * @mbg.generated
     */
    private Integer platformId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_platform.platform_name
     *
     * @mbg.generated
     */
    private String platformName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_platform.platform_link
     *
     * @mbg.generated
     */
    private String platformLink;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_platform.platform_descript
     *
     * @mbg.generated
     */
    private String platformDescript;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_platform.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_platform.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_platform.platform_id
     *
     * @return the value of tb_platform.platform_id
     *
     * @mbg.generated
     */
    public Integer getPlatformId() {
        return platformId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_platform.platform_id
     *
     * @param platformId the value for tb_platform.platform_id
     *
     * @mbg.generated
     */
    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_platform.platform_name
     *
     * @return the value of tb_platform.platform_name
     *
     * @mbg.generated
     */
    public String getPlatformName() {
        return platformName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_platform.platform_name
     *
     * @param platformName the value for tb_platform.platform_name
     *
     * @mbg.generated
     */
    public void setPlatformName(String platformName) {
        this.platformName = platformName == null ? null : platformName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_platform.platform_link
     *
     * @return the value of tb_platform.platform_link
     *
     * @mbg.generated
     */
    public String getPlatformLink() {
        return platformLink;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_platform.platform_link
     *
     * @param platformLink the value for tb_platform.platform_link
     *
     * @mbg.generated
     */
    public void setPlatformLink(String platformLink) {
        this.platformLink = platformLink == null ? null : platformLink.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_platform.platform_descript
     *
     * @return the value of tb_platform.platform_descript
     *
     * @mbg.generated
     */
    public String getPlatformDescript() {
        return platformDescript;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_platform.platform_descript
     *
     * @param platformDescript the value for tb_platform.platform_descript
     *
     * @mbg.generated
     */
    public void setPlatformDescript(String platformDescript) {
        this.platformDescript = platformDescript == null ? null : platformDescript.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_platform.create_time
     *
     * @return the value of tb_platform.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_platform.create_time
     *
     * @param createTime the value for tb_platform.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_platform.update_time
     *
     * @return the value of tb_platform.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_platform.update_time
     *
     * @param updateTime the value for tb_platform.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}