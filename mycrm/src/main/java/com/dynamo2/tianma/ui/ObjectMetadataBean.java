package com.dynamo2.tianma.ui;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.icefaces.bean.ViewRetained;
import org.icefaces.bean.WindowDisposed;

import com.dynamo2.tianma.model.ObjectMetadata;
import com.dynamo2.tianma.service.ObjectMetadataService;

@ManagedBean(name = "objectMetadata")
@ViewScoped
@ViewRetained
@WindowDisposed
public class ObjectMetadataBean {

	private ObjectMetadataService service = new ObjectMetadataService();
	
	public String list(){
		List<ObjectMetadata> list = service.list();
		
		return "list";
	}
}
