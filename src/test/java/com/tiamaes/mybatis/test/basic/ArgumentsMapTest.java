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

package com.tiamaes.mybatis.test.basic;

import com.tiamaes.mybatis.Pagination;
import com.tiamaes.mybatis.mapper.CountryMapper;
import com.tiamaes.mybatis.model.Country;
import com.tiamaes.mybatis.util.MybatisHelper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArgumentsMapTest {

    /**
     * 使用Mapper接口调用时，使用PageHelper.startPage效果更好，不需要添加Mapper接口参数
     */
    @Test
    public void testArgumentsMap() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
        try {
            List<Country> list = countryMapper.selectByPageNumSizeOrderBy(1, 10, "id desc");
            assertEquals(10, list.size());
            assertEquals(183, ((Pagination<?>) list).getTotal());

            list = countryMapper.selectByPageNumSize(2, 10);
            assertEquals(10, list.size());
            assertEquals(183, ((Pagination<?>) list).getTotal());

            list = countryMapper.selectByPageNumSize(3, 20);
            assertEquals(20, list.size());
            assertEquals(183, ((Pagination<?>) list).getTotal());

            list = countryMapper.selectByOrderBy("id desc");
            assertEquals(183, list.size());
            assertEquals(183, list.get(0).getId());
        } finally {
            sqlSession.close();
        }
    }
}
