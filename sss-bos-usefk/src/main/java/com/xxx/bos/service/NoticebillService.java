package com.xxx.bos.service;

import com.xxx.bos.entity.Noticebill;

/** 
  * @className   类名 : NoticebillService
  * @description 作用 : 业务通知单Service接口
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月15日 下午11:52:42 
  * @version 版本 : V1.0  
  */

public interface NoticebillService {

	/**
	 * 此方法描述的是： 保存业务通知单，并尝试自动分单
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月16日 上午12:08:50 
	 * @version: V1.0
	 */
	Noticebill save(Noticebill noticebill);

}
