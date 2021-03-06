package org.mark.mybatis.page;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.List;

/**
 * 分页结果的封装(for Bootstrap Table)
 * JPA版本
 */
@Data
public class PageInfoBT<T> {

    // 结果集
    private List<T> rows;
    // 总记录数
    private long total;

    public PageInfoBT(Page<T> page) {
        this.rows = page.getResult();
        this.total = page.getTotal();
    }

    public PageInfoBT(List<T> rows, long total) {
        this.rows = rows;
        this.total = total;
    }
}
