package com.gz.tzreport.handlers;

import com.gz.tzreport.pojo.TbCategory;
import org.apache.ibatis.type.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(TbCategory.class)
public class CategoryTypeHandler extends BaseTypeHandler<TbCategory> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TbCategory parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i,parameter.getCategoryId());
    }

    @Override
    public TbCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
        System.out.println("aaaaa---"+rs.getString(columnName));
        return new TbCategory(rs.getString(columnName));
    }

    @Override
    public TbCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        System.out.println("bbbbb---"+rs.getString(columnIndex));

        return new TbCategory(rs.getString(columnIndex));
    }

    @Override
    public TbCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        System.out.println("ccccc---"+cs);

        return new TbCategory(cs.getString(columnIndex));
    }
}

