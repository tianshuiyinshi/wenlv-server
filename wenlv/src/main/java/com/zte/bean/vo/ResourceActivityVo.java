package com.zte.bean.vo;

import com.zte.bean.ResourceActivity;

import java.util.List;

public class ResourceActivityVo extends ResourceActivity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourtypename ; //分类名称

	private List<ResourceLabelVo> resourceLabelVos;

	public String getResourtypename() {
		return resourtypename;
	}

	public void setResourtypename(String resourtypename) {
		this.resourtypename = resourtypename;
	}

	public List<ResourceLabelVo> getResourceLabelVos() {
		return resourceLabelVos;
	}

	public void setResourceLabelVos(List<ResourceLabelVo> resourceLabelVos) {
		this.resourceLabelVos = resourceLabelVos;
	}
}
