/**
 * Copyright (c) 2011-2016, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.tc.mybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.tc.mybatis.dao.MyMapper;
import org.tc.mybatis.service.IBaseService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * <p>
 * IBaseService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 * </p>
 */
public class BaseServiceImpl<M extends MyMapper<T>, T> implements IBaseService<T> {

    @Autowired
    protected M baseMapper;

    /**
     * 判断数据库操作是否成功
     * 注意！！ 该方法为 Integer 判断，不可传入 int 基本类型
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected static boolean retBool(int result) {
        return result >= 1;
    }

    @Override
    public boolean existsByPK(Object pk) {
        return baseMapper.existsWithPrimaryKey(pk);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertAutoGenPK(T entity) {
        return retBool(baseMapper.insertUseGeneratedKeys(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertSelective(T entity) {
        return retBool(baseMapper.insertSelective(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertAllCol(T entity) {
        return retBool(baseMapper.insert(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertListAutoGenPK(List<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        return retBool(baseMapper.insertList(entityList));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertOrUpdateSelective(T entity, Object pk) {
        int result = 0;
        if (pk == null) {
            result = baseMapper.insertSelective(entity);
        } else {
            result = baseMapper.updateByPrimaryKeySelective(entity);
        }
        return retBool(result);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertOrUpdateAllCol(T entity, Object pk) {
        int result = 0;
        if (pk == null) {
            result = baseMapper.insert(entity);
        } else {
            result = baseMapper.updateByPrimaryKey(entity);
        }
        return retBool(result);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteByPK(Object pk) {
        return retBool(baseMapper.deleteByPrimaryKey(pk));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteByEqualTo(Class<?> entityClass, String field, Object value) {
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(field, value);
        int count = baseMapper.deleteByExample(example);
        return retBool(count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteByIn(Class<?> entityClass, String field, Iterable<Object> values) {
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn(field, values);
        int count = baseMapper.deleteByExample(example);
        return retBool(count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateByPKSelective(T entity) {
        return retBool(baseMapper.updateByPrimaryKeySelective(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAllColByPK(T entity) {
        return retBool(baseMapper.updateByPrimaryKey(entity));
    }

    @Override
    public T selectByPK(Object pk) {
        return baseMapper.selectByPrimaryKey(pk);
    }

    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    @Override
    public List<T> selectByEqual(Class<?> entityClass, String field, Object value) {
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(field, value);
        List<T> list = baseMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<T> selectByIn(Class<?> entityClass, String field, Iterable<Object> values) {
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn(field, values);
        List<T> list = baseMapper.selectByExample(example);
        return list;
    }

    @Override
    public PageInfo<T> selectPageByExample(Example example, int pageNo, int pageSize) {
        // 开始分页
        PageHelper.startPage(pageNo, pageSize);
        List<T> list = baseMapper.selectByExample(example);
        //用PageInfo对结果进行包装
        PageInfo<T> page = new PageInfo<T>(list);
        return page;
    }

}
