package com.gz.tzreport.dao;

import com.github.pagehelper.Page;
import com.gz.tzreport.pojo.TbAdvice;
import java.util.List;

public interface TbAdviceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_advice
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer adviceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_advice
     *
     * @mbg.generated
     */
    int insert(TbAdvice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_advice
     *
     * @mbg.generated
     */
    TbAdvice selectByPrimaryKey(Integer adviceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_advice
     *
     * @mbg.generated
     */
    Page<TbAdvice> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_advice
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbAdvice record);
}