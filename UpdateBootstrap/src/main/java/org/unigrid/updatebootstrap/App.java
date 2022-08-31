/*
	The Janus Wallet
	Copyright © 2021 The Unigrid Foundation

	This program is free software: you can redistribute it and/or modify it under the terms of the
	addended GNU Affero General Public License as published by the Free Software Foundation, version 3
	of the License (see COPYING and COPYING.addendum).

	This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
	even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
	GNU Affero General Public License for more details.

	You should have received an addended copy of the GNU Affero General Public License with this program.
	If not, see <http://www.gnu.org/licenses/> and <https://github.com/unigrid-project/janus-java>.
 */

package org.unigrid.updatebootstrap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.update4j.Configuration;
import org.update4j.service.Delegate;

public class App extends Application implements Delegate {

	private static Scene scene;

	@Override
	public void start(Stage stage) throws IOException {

		stage.setMinWidth(650);
		stage.setMinHeight(500);
		
		URL configUrl = new URL("http://docs.unigrid.org/wallet/config.xml");
		Configuration config = null;
		
		
		
		try(Reader in = new InputStreamReader(configUrl.openStream(), StandardCharsets.UTF_8)) {
			config = Configuration.read(in);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			try(Reader in = Files.newBufferedReader(Paths.get("/home/marcus/Documents/unigrid/config/UpdateWalletConfig/config.xml"))) {
				System.out.println("reading local config xml");
				config = Configuration.read(in);
			}
		}
		
		config.sync();
		scene = new Scene(loadFXML("updateView"), 640, 480);
		stage.setScene(scene);
		stage.show();
		UpdateView updateView = new UpdateView(config, stage);

	}

	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void main(List<String> list) throws Throwable {
		launch();
	}

}