/*******************************************************************************
 * Copyright (c) 2016, 2017, Graphenee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package io.graphenee.i18n.vaadin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.SpringComponent;

import io.graphenee.core.model.api.GxDataService;
import io.graphenee.core.model.bean.GxSupportedLocaleBean;
import io.graphenee.vaadin.AbstractEntityListPanel;
import io.graphenee.vaadin.TRAbstractForm;

@SpringComponent
@Scope("prototype")
public class GxSupportedLocaleListPanel extends AbstractEntityListPanel<GxSupportedLocaleBean> {

	@Autowired
	GxDataService dataService;

	@Autowired
	GxSupportedLocaleForm editorForm;

	public GxSupportedLocaleListPanel() {
		super(GxSupportedLocaleBean.class);
	}

	@Override
	protected boolean onSaveEntity(GxSupportedLocaleBean entity) {
		dataService.save(entity);
		return true;
	}

	@Override
	protected boolean onDeleteEntity(GxSupportedLocaleBean entity) {
		dataService.delete(entity);
		return true;
	}

	@Override
	protected String panelCaption() {
		return null;
	}

	@Override
	protected List<GxSupportedLocaleBean> fetchEntities() {
		return dataService.findSupportedLocale();
	}

	@Override
	protected String[] visibleProperties() {
		return new String[] { "localeName", "localeCode" };
	}

	@Override
	protected TRAbstractForm<GxSupportedLocaleBean> editorForm() {
		return editorForm;
	}

}
