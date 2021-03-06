package com.mydlp.ui.schema.granules;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.mydlp.ui.dao.DAOUtil;
import com.mydlp.ui.domain.BundledKeywordGroup;
import com.mydlp.ui.domain.DataFormat;
import com.mydlp.ui.domain.InformationDescription;
import com.mydlp.ui.domain.InformationFeature;
import com.mydlp.ui.domain.InformationType;
import com.mydlp.ui.domain.InventoryBase;
import com.mydlp.ui.domain.InventoryCategory;
import com.mydlp.ui.domain.InventoryItem;
import com.mydlp.ui.domain.Matcher;
import com.mydlp.ui.domain.MatcherArgument;
import com.mydlp.ui.domain.NonCascadingArgument;
import com.mydlp.ui.schema.AbstractGranule;

public class _000_00046_GLBA_Names_SSN extends AbstractGranule {

	@Override
	protected void callback() {
			
		DetachedCriteria criteria = 
				DetachedCriteria.forClass(InventoryCategory.class)
					.add(Restrictions.eq("nameKey", "inventory.compliance.finance.glba.predefined"));
		@SuppressWarnings("unchecked")
		List<InventoryCategory> list = getHibernateTemplate().findByCriteria(criteria);
		InventoryCategory glba = DAOUtil.getSingleResult(list);
		
		DetachedCriteria criteria2 = 
				DetachedCriteria.forClass(DataFormat.class)
					.add(Restrictions.eq("nameKey", "dataFormat.all.label"));
		@SuppressWarnings("unchecked")
		List<DataFormat> list2 = getHibernateTemplate().findByCriteria(criteria2);
		DataFormat df = DAOUtil.getSingleResult(list2);
		
		DetachedCriteria criteria3 = 
				DetachedCriteria.forClass(BundledKeywordGroup.class)
					.add(Restrictions.eq("nameKey", "names.keywordList"));
		@SuppressWarnings("unchecked")
		List<BundledKeywordGroup> list3 = getHibernateTemplate().findByCriteria(criteria3);
		BundledKeywordGroup names = DAOUtil.getSingleResult(list3);
		
		Matcher matcherSSN = new Matcher();
		matcherSSN.setFunctionName("ssn");
	
		Matcher matcherNames = new Matcher();
		matcherNames.setFunctionName("keyword_group");
		
		NonCascadingArgument nonCascadingArgumentNames = new NonCascadingArgument(); 	
		MatcherArgument matcherArgument = new MatcherArgument();
		nonCascadingArgumentNames.setArgument(names);
		matcherArgument.setCoupledMatcher(matcherNames);
		matcherArgument.setCoupledArgument(nonCascadingArgumentNames);
		List<MatcherArgument> matcherArguments = new ArrayList<MatcherArgument>();
		matcherArguments.add(matcherArgument);
		matcherNames.setMatcherArguments(matcherArguments);
		
		InformationFeature informationFeatureSSN = new InformationFeature();
		informationFeatureSSN.setThreshold(new Long(1));
		informationFeatureSSN.setMatcher(matcherSSN);
		matcherSSN.setCoupledInformationFeature(informationFeatureSSN);
		
		InformationFeature informationFeatureNames = new InformationFeature();
		informationFeatureNames.setThreshold(new Long(1));
		informationFeatureNames.setMatcher(matcherNames);
		matcherNames.setCoupledInformationFeature(informationFeatureNames);
		
		InformationDescription informationDescription = new InformationDescription();
		List<InformationFeature> ifts = new ArrayList<InformationFeature>();
		informationDescription.setDistanceEnabled(true);
		informationDescription.setDistance(100);
		ifts.add(informationFeatureSSN);
		ifts.add(informationFeatureNames);
		informationDescription.setFeatures(ifts);
		
		InformationType informationType = new InformationType();
		informationType.setInformationDescription(informationDescription);
		List<DataFormat> dfs = new ArrayList<DataFormat>();
		dfs.add(df);
		informationType.setDataFormats(dfs);
		
		InventoryItem inventoryItem = new InventoryItem();
		inventoryItem.setNameKey("informationType.compliance.finance.glba.names_ssn");
		inventoryItem.setItem(informationType);
		informationType.setCoupledInventoryItem(inventoryItem);

		inventoryItem.setCategory(glba);
		List<InventoryBase> dtc = new ArrayList<InventoryBase>();
		dtc.add(inventoryItem);
		glba.setChildren(dtc);
		
		getHibernateTemplate().saveOrUpdate(glba);
		getHibernateTemplate().saveOrUpdate(inventoryItem);
	}
}
