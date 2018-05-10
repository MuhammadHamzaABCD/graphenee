package io.graphenee.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.graphenee.core.api.GxNamespaceService;
import io.graphenee.core.model.api.GxDataService;
import io.graphenee.core.model.bean.GxNamespaceBean;

@Service
public class GxNamespaceServiceImpl implements GxNamespaceService {

	private static final String SYSTEM_NAMESPACE = "io.graphenee.system";
	private static final String APPLICATION_NAMESPACE = "io.graphenee.application";

	@Autowired
	GxDataService dataService;

	@Override
	public GxNamespaceBean systemNamespace() {
		return dataService.findNamespace(SYSTEM_NAMESPACE);
	}

	@Override
	public GxNamespaceBean applicationNamespace() {
		return dataService.findNamespace(APPLICATION_NAMESPACE);
	}

	@Override
	public GxNamespaceBean namespace(String namespace) {
		return dataService.findNamespace(namespace);
	}

}
