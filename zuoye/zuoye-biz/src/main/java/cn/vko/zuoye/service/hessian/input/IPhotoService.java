/**
 * IPhotoService.java
 * cn.vko.web.photo.hessian.inter
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.hessian.input;

/**
 * 相册服务
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-28 	 
 */
public interface IPhotoService {

	//手机相册全部图片
	public String getAllMobileByUserId(Long userId);

	//手机相册分页显示
	public String getPagingMobileByUserId(int page, int pageSize, Long userId);

	//根据用户得到手机相册id
	public Long getMobileAlbumId(Long userId);
}
