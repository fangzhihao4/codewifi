/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.records;


import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.generated.tables.User;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户登录信息表
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record12<Integer, String, String, Byte, String, String, String, String, String, LocalDateTime, LocalDateTime, Byte> {

    private static final long serialVersionUID = 1182552811;

    /**
     * Setter for <code>codewifi.user.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>codewifi.user.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>codewifi.user.openid</code>. openid
     */
    public void setOpenid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>codewifi.user.openid</code>. openid
     */
    public String getOpenid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>codewifi.user.user_no</code>.
     */
    public void setUserNo(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>codewifi.user.user_no</code>.
     */
    public String getUserNo() {
        return (String) get(2);
    }

    /**
     * Setter for <code>codewifi.user.type</code>. 1账号密码  2微信 3抖音
     */
    public void setType(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>codewifi.user.type</code>. 1账号密码  2微信 3抖音
     */
    public Byte getType() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>codewifi.user.unionid</code>. unionid
     */
    public void setUnionid(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>codewifi.user.unionid</code>. unionid
     */
    public String getUnionid() {
        return (String) get(4);
    }

    /**
     * Setter for <code>codewifi.user.nickname</code>. 昵称
     */
    public void setNickname(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>codewifi.user.nickname</code>. 昵称
     */
    public String getNickname() {
        return (String) get(5);
    }

    /**
     * Setter for <code>codewifi.user.head_img_url</code>. 头像
     */
    public void setHeadImgUrl(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>codewifi.user.head_img_url</code>. 头像
     */
    public String getHeadImgUrl() {
        return (String) get(6);
    }

    /**
     * Setter for <code>codewifi.user.gender</code>. 性别
     */
    public void setGender(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>codewifi.user.gender</code>. 性别
     */
    public String getGender() {
        return (String) get(7);
    }

    /**
     * Setter for <code>codewifi.user.region</code>. 地区
     */
    public void setRegion(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>codewifi.user.region</code>. 地区
     */
    public String getRegion() {
        return (String) get(8);
    }

    /**
     * Setter for <code>codewifi.user.create_time</code>. 创建时间
     */
    public void setCreateTime(LocalDateTime value) {
        set(9, value);
    }

    /**
     * Getter for <code>codewifi.user.create_time</code>. 创建时间
     */
    public LocalDateTime getCreateTime() {
        return (LocalDateTime) get(9);
    }

    /**
     * Setter for <code>codewifi.user.update_time</code>. 更新时间
     */
    public void setUpdateTime(LocalDateTime value) {
        set(10, value);
    }

    /**
     * Getter for <code>codewifi.user.update_time</code>. 更新时间
     */
    public LocalDateTime getUpdateTime() {
        return (LocalDateTime) get(10);
    }

    /**
     * Setter for <code>codewifi.user.status</code>. 1使用中 2已删除
     */
    public void setStatus(Byte value) {
        set(11, value);
    }

    /**
     * Getter for <code>codewifi.user.status</code>. 1使用中 2已删除
     */
    public Byte getStatus() {
        return (Byte) get(11);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record12 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row12<Integer, String, String, Byte, String, String, String, String, String, LocalDateTime, LocalDateTime, Byte> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    @Override
    public Row12<Integer, String, String, Byte, String, String, String, String, String, LocalDateTime, LocalDateTime, Byte> valuesRow() {
        return (Row12) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return User.USER.ID;
    }

    @Override
    public Field<String> field2() {
        return User.USER.OPENID;
    }

    @Override
    public Field<String> field3() {
        return User.USER.USER_NO;
    }

    @Override
    public Field<Byte> field4() {
        return User.USER.TYPE;
    }

    @Override
    public Field<String> field5() {
        return User.USER.UNIONID;
    }

    @Override
    public Field<String> field6() {
        return User.USER.NICKNAME;
    }

    @Override
    public Field<String> field7() {
        return User.USER.HEAD_IMG_URL;
    }

    @Override
    public Field<String> field8() {
        return User.USER.GENDER;
    }

    @Override
    public Field<String> field9() {
        return User.USER.REGION;
    }

    @Override
    public Field<LocalDateTime> field10() {
        return User.USER.CREATE_TIME;
    }

    @Override
    public Field<LocalDateTime> field11() {
        return User.USER.UPDATE_TIME;
    }

    @Override
    public Field<Byte> field12() {
        return User.USER.STATUS;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getOpenid();
    }

    @Override
    public String component3() {
        return getUserNo();
    }

    @Override
    public Byte component4() {
        return getType();
    }

    @Override
    public String component5() {
        return getUnionid();
    }

    @Override
    public String component6() {
        return getNickname();
    }

    @Override
    public String component7() {
        return getHeadImgUrl();
    }

    @Override
    public String component8() {
        return getGender();
    }

    @Override
    public String component9() {
        return getRegion();
    }

    @Override
    public LocalDateTime component10() {
        return getCreateTime();
    }

    @Override
    public LocalDateTime component11() {
        return getUpdateTime();
    }

    @Override
    public Byte component12() {
        return getStatus();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getOpenid();
    }

    @Override
    public String value3() {
        return getUserNo();
    }

    @Override
    public Byte value4() {
        return getType();
    }

    @Override
    public String value5() {
        return getUnionid();
    }

    @Override
    public String value6() {
        return getNickname();
    }

    @Override
    public String value7() {
        return getHeadImgUrl();
    }

    @Override
    public String value8() {
        return getGender();
    }

    @Override
    public String value9() {
        return getRegion();
    }

    @Override
    public LocalDateTime value10() {
        return getCreateTime();
    }

    @Override
    public LocalDateTime value11() {
        return getUpdateTime();
    }

    @Override
    public Byte value12() {
        return getStatus();
    }

    @Override
    public UserRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public UserRecord value2(String value) {
        setOpenid(value);
        return this;
    }

    @Override
    public UserRecord value3(String value) {
        setUserNo(value);
        return this;
    }

    @Override
    public UserRecord value4(Byte value) {
        setType(value);
        return this;
    }

    @Override
    public UserRecord value5(String value) {
        setUnionid(value);
        return this;
    }

    @Override
    public UserRecord value6(String value) {
        setNickname(value);
        return this;
    }

    @Override
    public UserRecord value7(String value) {
        setHeadImgUrl(value);
        return this;
    }

    @Override
    public UserRecord value8(String value) {
        setGender(value);
        return this;
    }

    @Override
    public UserRecord value9(String value) {
        setRegion(value);
        return this;
    }

    @Override
    public UserRecord value10(LocalDateTime value) {
        setCreateTime(value);
        return this;
    }

    @Override
    public UserRecord value11(LocalDateTime value) {
        setUpdateTime(value);
        return this;
    }

    @Override
    public UserRecord value12(Byte value) {
        setStatus(value);
        return this;
    }

    @Override
    public UserRecord values(Integer value1, String value2, String value3, Byte value4, String value5, String value6, String value7, String value8, String value9, LocalDateTime value10, LocalDateTime value11, Byte value12) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(Integer id, String openid, String userNo, Byte type, String unionid, String nickname, String headImgUrl, String gender, String region, LocalDateTime createTime, LocalDateTime updateTime, Byte status) {
        super(User.USER);

        set(0, id);
        set(1, openid);
        set(2, userNo);
        set(3, type);
        set(4, unionid);
        set(5, nickname);
        set(6, headImgUrl);
        set(7, gender);
        set(8, region);
        set(9, createTime);
        set(10, updateTime);
        set(11, status);
    }
}
