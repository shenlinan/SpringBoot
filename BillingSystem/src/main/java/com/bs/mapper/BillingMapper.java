package com.bs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bs.domain.ExpenditureBean;
import com.bs.domain.InComeBean;

@Mapper
public interface BillingMapper {

	@Insert("INSERT INTO T_INCOME(USERID, INCOME,INCOMEDATE) VALUES(#{userId}, #{inCome},#{inComeDate})")
	int saveInCome(@Param("userId") int userId, @Param("inCome") String inCome, @Param("inComeDate") String inComeDate);

	@Insert("INSERT INTO T_EXPENDITURE(USERID, EXPENDITURE,USEAGE,PAYDATE) VALUES(#{userId}, #{expenditure},#{useage},#{payDate})")
	int saveExpenditure(@Param("userId") int userId, @Param("expenditure") String expenditure,
			@Param("useage") String useage, @Param("payDate") String payDate);

	@Select("SELECT EX.USERID,U.NAME,EX.EXPENDITURE,EX.USEAGE,EX.PAYDATE FROM (SELECT E.USERID,E.EXPENDITURE,E.USEAGE,E.PAYDATE FROM T_EXPENDITURE E,(SELECT #{userIdSearch} AS USERID,#{useageSearch} AS USEAGE FROM DUAL) D WHERE E.USEAGE LIKE CONCAT('%',D.USEAGE,'%') AND IF(D.USERID = '0','1=1',D.USERID=E.USERID)) EX LEFT JOIN T_USER U ON EX.USERID = U.ID ORDER BY EX.PAYDATE DESC LIMIT #{pageIndex},#{pageSize}")
	List<ExpenditureBean> loadExpenditureList(@Param("useageSearch") String useageSearch,
			@Param("userIdSearch") String userIdSearch, @Param("pageIndex") int pageIndex,
			@Param("pageSize") int pageSize);

	@Select("SELECT U.NAME,IC.INCOME,IC.INCOMEDATE FROM T_INCOME IC LEFT JOIN T_USER U ON IC.USERID = U.ID ")
	List<InComeBean> loadInComeList();

	@Select("SELECT COUNT(1) FROM T_EXPENDITURE E,(SELECT #{userIdSearch} AS USERID,#{useageSearch} AS USEAGE FROM DUAL) D WHERE E.USEAGE LIKE CONCAT('%',D.USEAGE,'%') AND IF(D.USERID = '0','1=1',D.USERID=E.USERID)")
	int loadExpenditureCnt(@Param("useageSearch") String useageSearch, @Param("userIdSearch") String userIdSearch);
}
