/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tiamaes.mybatis.test.basic.dynamic;

import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.mybatis.mapper.CountryMapper;
import com.tiamaes.mybatis.model.Country;
import com.tiamaes.mybatis.util.MybatisHelper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestDynamicIf {

    /**
     * 使用Mapper接口调用时，使用PageHelper.startPage效果更好，不需要添加Mapper接口参数
     */
    @Test
    public void testCountCache() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
        try {
            //获取第1页，10条内容，默认查询总数count
            PageHelper.startPage(1, 10);
            List<Country> list = countryMapper.selectIf(1);
            assertEquals(2, list.get(0).getId());
            assertEquals(10, list.size());
            assertEquals(182, ((Pagination<?>) list).getTotal());

            //获取第1页，10条内容，默认查询总数count
            PageHelper.startPage(1, 10);
            list = countryMapper.selectIf(null);
            assertEquals(1, list.get(0).getId());
            assertEquals(10, list.size());
            assertEquals(183, ((Pagination<?>) list).getTotal());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 使用Mapper接口调用时，使用PageHelper.startPage效果更好，不需要添加Mapper接口参数
     */
    @Test
    public void testCountCache2() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
        try {
            //获取第1页，10条内容，默认查询总数count
            PageHelper.startPage(1, 10);
            List<Country> list = countryMapper.selectIf(1);
            assertEquals(2, list.get(0).getId());
            assertEquals(10, list.size());
            assertEquals(182, ((Pagination<?>) list).getTotal());

            //获取第1页，10条内容，默认查询总数count
            PageHelper.startPage(2, 10);
            list = countryMapper.selectIf(1);
            assertEquals(12, list.get(0).getId());
            assertEquals(10, list.size());
            assertEquals(182, ((Pagination<?>) list).getTotal());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 单个POJO参数情况特殊
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void testMapper() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
        try {
            Map map = new HashMap();

            Country country = new Country();
            country.setId(1);
            map.put("country", country);
            //获取第1页，10条内容，默认查询总数count
            PageHelper.startPage(1, 10);
            List<Country> list = countryMapper.selectIf3(country);
            assertEquals(2, list.get(0).getId());
            assertEquals(10, list.size());
            assertEquals(182, ((Pagination<?>) list).getTotal());
        } finally {
            sqlSession.close();
        }
    }
}