package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;

import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetRow;

public class SpreadSheetDataSource implements DataSource<SpreadSheetRow> {

	@Override
	public void load(LoadConfig<SpreadSheetRow> loadConfig, LoadCallback<SpreadSheetRow> callback) {
		// TODO Auto-generated method stub
		/*
		 * 
		 *    private final PersonServiceAsync personService;

    public PersonDataSource(PersonServiceAsync personService) {
        this.personService = personService;
    }

    @Override
    public void load(LoadConfig<Person> loadConfig, LoadCallback<Person> callback) {
        List<CategoryComponent> categories = loadConfig.getOpenCategories();

        List<String> categoryNames = null;
        if(categories != null) {
            categoryNames = categories.stream().map(CategoryComponent::getName).collect(Collectors.toList());
        }

        personService.getPeople(loadConfig.getOffset(), loadConfig.getLimit(), categoryNames,
                new AsyncCallback<People>() {
            @Override
            public void onSuccess(People people) {
                callback.onSuccess(new LoadResult<>(people, loadConfig.getOffset(), people.getAbsoluteTotal()));
            }
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log("Getting people async call failed.", throwable);
                callback.onFailure(throwable);
            }
});
		 */
	}

	@Override
	public boolean useRemoteSort() {
		// TODO Auto-generated method stub
		return false;
	}

}
