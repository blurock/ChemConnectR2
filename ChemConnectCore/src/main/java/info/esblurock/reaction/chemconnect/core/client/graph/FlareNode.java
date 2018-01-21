package info.esblurock.reaction.chemconnect.core.client.graph;

import com.github.gwtd3.api.arrays.Array;
import com.google.gwt.core.client.JavaScriptObject;

public class FlareNode extends JavaScriptObject {

    protected FlareNode() {

    }

    public final native String name() /*-{
                                      return this.name;
                                      }-*/;

    public final native int size() /*-{
                                   return this.size;
                                   }-*/;

    public final native Array<FlareNode> children()/*-{
                                                   return this.children;
                                                   }-*/;

    public final native boolean isLeaf()/*-{
                                        return !this.children;
                                        }-*/;
}
