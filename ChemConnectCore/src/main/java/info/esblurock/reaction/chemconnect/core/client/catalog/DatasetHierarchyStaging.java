package info.esblurock.reaction.chemconnect.core.client.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class DatasetHierarchyStaging implements Comparator<DatasetHierarchyStaging> {
	
	int priority;
	SetUpCollapsibleItem setup;
	DatabaseObjectHierarchy hierarchy;
	String type;
	DatabaseObject object;
	
	/*
	 * This class is used to order the DatasetHierarchy items using the priority in
	 * SetUpCollapsibleItem
	 * 
	 */
	public static ArrayList<DatasetHierarchyStaging> computeStaging(DatabaseObjectHierarchy hierarchy) {
		ArrayList<DatasetHierarchyStaging> lst = new ArrayList<DatasetHierarchyStaging>();
		for(DatabaseObjectHierarchy  sub : hierarchy.getSubobjects()) {
			DatasetHierarchyStaging substage = new DatasetHierarchyStaging(sub);
			lst.add(substage);
		}
		Collections.sort(lst, new Comparator<DatasetHierarchyStaging> () {
			@Override
			public int compare(DatasetHierarchyStaging o1, DatasetHierarchyStaging o2) {
				return o2.getPriority() - o1.getPriority();
			}
		});
		return lst;
	}
	
	public DatasetHierarchyStaging(DatabaseObjectHierarchy hierarchy) {
		super();
		object = hierarchy.getObject();
		type = TextUtilities.removeNamespace(object.getClass().getSimpleName());
		setup = getSetup(hierarchy.getObject());
		priority = setup.priority();
		this.hierarchy = hierarchy;
	}
	public int getPriority() {
		return priority;
	}
	public SetUpCollapsibleItem getSetup() {
		return setup;
	}

	public DatabaseObjectHierarchy getHierarchy() {
		return hierarchy;
	}

	@Override
	public int compare(DatasetHierarchyStaging o1, DatasetHierarchyStaging o2) {
		return o1.getPriority() - o2.getPriority();
	}
	public static SetUpCollapsibleItem getSetup(DatabaseObject object) {
		String structure = object.getClass().getSimpleName();
		SetUpCollapsibleItem setup = null;
		try {
			setup = SetUpCollapsibleItem.valueOf(structure);
		} catch (Exception ex) {
			Window.alert(structure + " has not been not found: " + ex.getClass().getSimpleName());
			Window.alert(structure + " not found: " + SetUpCollapsibleItem.values());
		}
		return setup;
	}

}
