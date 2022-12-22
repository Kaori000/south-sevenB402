package cn.yznu.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author lfh
 * @email kaoriii@163.com
 * @date 2021-11-04 21:16:05
 */
@Data
@TableName("sys_log")
public class SysLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.UUID)
	private String id;
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String operation;
	/**
	 * 
	 */
	private String method;
	/**
	 * 
	 */
	private String params;
	/**
	 * 
	 */
	private Integer time;
	/**
	 * 
	 */
	private String ip;
	/**
	 * 
	 */
	private Date createDate;

}
