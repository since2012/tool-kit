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
package org.tc.mybatis.service;

import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * <p>
 * 顶级 Service
 * </p>
 *
 * @author hubin
 * @Date 2016-04-20
 */
public interface IBaseService<T> {

    /**
     * 根据主键判定是否存在
     *
     * @return
     */
    public boolean existsByPK(Object pk);

    /**
     * 插入数据，限制为实体包含`id`属性并且必须为自增列，实体配置的主键策略无效
     *
     * @param entity
     * @return
     */
    public boolean insertUseGeneratedKeys(T entity);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    boolean insertSelective(T entity);

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity
     * @return
     */
    boolean insertAllCol(T entity);

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，
     * 另外该接口限制实体包含`id`属性并且必须为自增列
     *
     * @param entityList
     * @return
     */
    boolean insertList(List<T> entityList);

    /**
     * 插入或更新
     *
     * @param entity 实体对象
     * @return boolean
     */
    boolean insertOrUpdateSelective(T entity, Object pk);

    /**
     * 插入或修改一条记录的全部字段
     *
     * @param entity 实体对象
     * @return boolean
     */
    boolean insertOrUpdateAllCol(T entity, Object pk);

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param pk 主键ID
     * @return boolean
     */
    boolean deleteByPK(Object pk);

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @return boolean
     */
    public boolean deleteByEqualTo(Class<?> entityClass, String field, Object value);


    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @return boolean
     */
    public boolean deleteByIn(Class<?> entityClass, String field, List<Object> value);

    /**
     * <p>
     * 根据 ID 选择修改
     * </p>
     *
     * @param entity 实体对象
     * @return boolean
     */
    boolean updateByPKSelective(T entity);

    /**
     * <p>
     * 根据 ID 修改全部字段
     * </p>
     *
     * @param entity 实体对象
     * @return boolean
     */
    boolean updateAllColByPK(T entity);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param pk 主键ID
     * @return T
     */
    T selectByPK(Object pk);


    List<T> selectAll();

    /**
     * 根据 字段 查询
     */
    public List<T> selectByEqual(Class<?> entityClass, String field, Object value);

    /**
     * 根据 字段 查询
     */
    public List<T> selectByIn(Class<?> entityClass, String field, List<Object> value);

    /**
     * 翻页查询
     */
    PageInfo<T> selectPage(Example example, int pageNum, int pageSize);

}
