package com.gz.tzreport.handlers;

import com.gz.tzreport.pojo.TbPlatform;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//@MappedJdbcTypes(JdbcType.INTEGER)
//@MappedTypes(TbPlatform.class)
public class PlatformTypeHandler extends BaseTypeHandler<TbPlatform> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TbPlatform parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i,parameter.getPlatformId());
    }

    @Override
    public TbPlatform getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return new TbPlatform(rs.getString(columnName));
    }

    @Override
    public TbPlatform getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return new TbPlatform(rs.getString(columnIndex));
    }

    @Override
    public TbPlatform getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new TbPlatform(cs.getString(columnIndex));
    }
}
