/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.milepost.system.sqlparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 * @author Thomas Risberg
 * @author Juergen Hoeller
 * @author Rick Evans
 */
public class NamedParameterUtilsTests {

	@Test
	public void test2(){
		String sql = "select * from employee where id = :id and last_name = :lastName and age = :id";
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		System.out.println(parsedSql.getParameterNames());//[id, lastName, id]
	}
	
	
	@Test
	public void test1(){
		/*String sql = "select * from employee where id = :id and last_name = :lastName";
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		System.out.println("NamedParameterCount :" + parsedSql.getNamedParameterCount()); 
		System.out.println("OriginalSql :" + parsedSql.getOriginalSql()); 
		System.out.println("TotalParameterCount :" + parsedSql.getTotalParameterCount());
		System.out.println("UnnamedParameterCount :" + parsedSql.getUnnamedParameterCount());
		System.out.println("ParameterIndexes(0) :");//获取参数的起始索引和结束索引
		for(int index : parsedSql.getParameterIndexes(0)){
			System.out.println(index);
		}
		System.out.println("ParameterNames :" + parsedSql.getParameterNames());*/
		/**
			NamedParameterCount :3
			OriginalSql :xxx :a yyyy :b :c :a zzzzz
			TotalParameterCount :4
			UnnamedParameterCount :0
			ParameterIndexes(0) :[I@746dbfb0
			ParameterNames :[a, b, c, a]
		 */
		String sql1 = "select * from employee where [id = :id and] age = :age and birth = '['+ :birth +']' [and last_name = :lastName]";
		ParsedSql parsedSql1 = NamedParameterUtils.parseSqlStatement(sql1);
		System.out.println("NamedParameterCount :" + parsedSql1.getNamedParameterCount()); 
		System.out.println("OriginalSql :" + parsedSql1.getOriginalSql()); 
		System.out.println("TotalParameterCount :" + parsedSql1.getTotalParameterCount());
		System.out.println("UnnamedParameterCount :" + parsedSql1.getUnnamedParameterCount());
		List<String> parameterList = parsedSql1.getParameterNames();
		for(int i=0; i<parameterList.size(); i++){
			System.out.println("parameter=" + parameterList.get(i)); 
			System.out.println("startIndex=" + parsedSql1.getParameterIndexes(i)[0]);
			System.out.println("endIndex=" + parsedSql1.getParameterIndexes(i)[1]);
		}
		/**
			NamedParameterCount :4
			OriginalSql :select * from employee where [id = :id and] age = :age and birth = '['+ :birth +']' [and last_name = :lastName]
			TotalParameterCount :4
			UnnamedParameterCount :0
			parameter=id
			startIndex=35
			endIndex=38
			parameter=age
			startIndex=50
			endIndex=54
			parameter=birth
			startIndex=72
			endIndex=78
			parameter=lastName
			startIndex=101
			endIndex=110
		 */
	}

}
