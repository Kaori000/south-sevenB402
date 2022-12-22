package cn.yznu.modules.sys.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName MaintenanceDeviceVo
 * @Descirption 运维人员设备VO
 * @Author Borit Wang
 * @Date 2020/2/18 10:13
 * @Version 1.0
 */
@Data
public class MaintenanceDeviceVo {
    @ApiParam("运维人员id")
    @NotNull
    private Long maintenacneId;

    @ApiParam("old_运维人员id-->运维人员变更时使用")
    @NotNull
    private Long oldMaintenacneId;

    @ApiParam("设备id")
    private List<String> deviceId;
}
