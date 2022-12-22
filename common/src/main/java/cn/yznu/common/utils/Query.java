package cn.yznu.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.yznu.common.xss.SQLFilter;

import java.util.Map;

/**
 * 查询参数
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Query<T> {

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if(params.get(Constant.PAGE) != null){
            curPage = Long.parseLong(String.valueOf(params.get(Constant.PAGE)));
        }
        if(params.get(Constant.LIMIT) != null){
            limit = Long.parseLong(String.valueOf(params.get(Constant.LIMIT)));
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //分页参数
        params.put(Constant.PAGE, page);

        //排序字段
        // 兼容react排序
        String sorter = SQLFilter.sqlInjectOrderBy((String)params.get(Constant.SORTER));
        if(StringUtil.isNotEmpty(sorter)){
            String[] sorterParam = sorter.split("_");
            if(sorterParam.length == 2){
                String tableName = "";
                if(StringUtil.isNotEmpty(params.get("table"))){
                    tableName = params.get("table") + ".";
                }
                if("ascend".equals(sorterParam[1])){
                    page.addOrder(OrderItem.asc(tableName + StringUtil.HumpToUnderline(sorterParam[0])));
                }else if("descend".equals(sorterParam[1])){
                    page.addOrder(OrderItem.desc(tableName + StringUtil.HumpToUnderline(sorterParam[0])));
                }
            }

        }

        return page;
    }
}