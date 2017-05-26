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

package com.tiamaes.mybatis.test.basic.example;

import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.mybatis.mapper.CountryMapper;
import com.tiamaes.mybatis.model.Country;
import com.tiamaes.mybatis.model.CountryExample;
import com.tiamaes.mybatis.util.MybatisHelper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestExample {

    @Test
    public void testNull() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
        try {
            PageHelper.startPage(1, 20);
            List<Country> list = countryMapper.selectByExample(null);
            assertEquals(1, list.get(0).getId());
            assertEquals(20, list.size());
            assertEquals(183, ((Pagination<?>) list).getTotal());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testGreaterThan() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
        try {
            CountryExample example = new CountryExample();
            example.createCriteria().andIdGreaterThan(100);
            PageHelper.startPage(1, 20);
            List<Country> list = countryMapper.selectByExample(example);
            assertEquals(101, list.get(0).getId());
            assertEquals(20, list.size());
            assertEquals(83, ((Pagination<?>) list).getTotal());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInList() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
        try {
            CountryExample example = new CountryExample();
            example.createCriteria().andIdIn(Arrays.asList(1, 2, 3, 4, 5));
            PageHelper.startPage(1, 20);
            List<Country> list = countryMapper.selectByExample(example);
            assertEquals(1, list.get(0).getId());
            assertEquals(5, list.size());
            assertEquals(5, ((Pagination<?>) list).getTotal());
        } finally {
            sqlSession.close();
        }
    }
}
