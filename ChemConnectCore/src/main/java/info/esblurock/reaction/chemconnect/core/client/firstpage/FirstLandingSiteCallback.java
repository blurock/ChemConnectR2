package info.esblurock.reaction.chemconnect.core.client.firstpage;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;

import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;

public class FirstLandingSiteCallback implements AsyncCallback<String>{
	MaterialPanel panel;
	
	public FirstLandingSiteCallback(MaterialPanel panel) {
		this.panel = panel;
		MaterialLoader.loading(true);
		}
	@Override
	public void onSuccess(String code) {
		Window.alert(code);
		MaterialWindow window = new MaterialWindow();
		MaterialPanel p = new MaterialPanel();
		HTML html = new HTML(code);
		p.add(html);
		window.add(p);
		window.open();
		//panel.add(html);
		MaterialLoader.loading(false);
		}


	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert(arg0.toString());
	}

}
