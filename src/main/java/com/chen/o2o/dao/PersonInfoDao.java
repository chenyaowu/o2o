package com.chen.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chen.o2o.entity.PersonInfo;

public interface PersonInfoDao {

	/**
	 * 
	 * @param personInfoCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<PersonInfo> queryPersonInfoList(
            @Param("personInfoCondition") PersonInfo personInfoCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	/**
	 * 
	 * @param personInfoCondition
	 * @return
	 */
	int queryPersonInfoCount(
            @Param("personInfoCondition") PersonInfo personInfoCondition);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);

	/**
	 * 
	 * @param personInfo
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);

	/**
	 * 
	 * @param personInfo
	 * @return
	 */
	int updatePersonInfo(PersonInfo personInfo);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	int deletePersonInfo(long userId);
}
