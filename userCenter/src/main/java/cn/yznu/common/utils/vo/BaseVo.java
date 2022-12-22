package cn.yznu.common.utils.vo;

import io.swagger.annotations.ApiParam;

public class BaseVo {

    @ApiParam("条数")
    private Integer limit;

    @ApiParam("页数")
    private Integer page;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit == null ? 0 : limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page == null ? 0 : page;
    }
}
