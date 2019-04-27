package com.duoxik.tasks.agregator;

import com.duoxik.tasks.agregator.model.HHStrategy;
import com.duoxik.tasks.agregator.model.Model;
import com.duoxik.tasks.agregator.model.MoikrugStrategy;
import com.duoxik.tasks.agregator.model.Provider;
import com.duoxik.tasks.agregator.view.HtmlView;

public class Aggregator {
    public static void main(String[] args) {

        HtmlView view = new HtmlView();
        Provider hhProvider = new Provider(new HHStrategy());
        Provider moikrugProvider = new Provider(new MoikrugStrategy());
        Model model = new Model(view, hhProvider, moikrugProvider);
        Controller controller = new Controller(model);
        view.setController(controller);

        view.userCitySelectEmulationMethod();
    }
}
