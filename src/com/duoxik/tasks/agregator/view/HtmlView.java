package com.duoxik.tasks.agregator.view;

import com.duoxik.tasks.agregator.Controller;
import com.duoxik.tasks.agregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {

    private Controller controller;
    private final String filePath = "/home/duoxik/IdeaProjects/JavaRushProjects/src/com/duoxik/tasks/agregator/view/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        String data = getUpdatedFileContent(vacancies);
        updateFile(data);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) {

        try {
            Document document = getDocument();
            Element realTemplate = document.getElementsByClass("template").first();
            Element template = realTemplate.clone();
            template.removeClass("template").removeAttr("style");
            for (Element element : document.getElementsByClass("vacancy")) {
                if (element.className().equals("vacancy template"))
                    continue;
                element.remove();
            }

            for (Vacancy vacancy : vacancies) {

                Element element = template.clone();
                element.getElementsByClass("city").first().appendText(vacancy.getCity());
                element.getElementsByClass("companyName").first().appendText(vacancy.getCompanyName());
                element.getElementsByClass("salary").first().appendText(vacancy.getSalary());
                element.getElementsByTag("a").first().attr("href", vacancy.getUrl()).appendText(vacancy.getTitle());

                realTemplate.before(element.outerHtml());
            }

            return document.html();

        } catch (IOException e) {
            e.printStackTrace();
            return "Some exception occurred";
        }
    }

    private void updateFile(String data) {
        try (BufferedWriter bos = new BufferedWriter(new FileWriter(filePath))) {
            bos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
