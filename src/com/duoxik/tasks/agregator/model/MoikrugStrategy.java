package com.duoxik.tasks.agregator.model;

import com.duoxik.tasks.agregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoikrugStrategy implements Strategy {

    private static final String URL = "https://moikrug.ru";
    private static final String URL_FORMAT = URL + "/vacancies?q=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {

        List<Vacancy> vacancies = new ArrayList<>();

        try {
            for (int i = 0; ; i++) {
                Document document = getDocument(searchString, i);

                Elements elements = document.getElementsByClass("job");

                if (elements.size() == 0) break;

                for (Element element : elements) {

                    Vacancy vacancy = new Vacancy();

                    Element titleElement = element.getElementsByClass("title").first().child(0);

                    String title = titleElement.text();
                    vacancy.setTitle(title);

                    String siteName = URL + titleElement.attr("href");
                    vacancy.setSiteName(siteName);

                    String url = URL + titleElement.attr("href");
                    vacancy.setUrl(url);

                    String city = element.getElementsByClass("location").text();
                    vacancy.setCity(city);

                    String companyName = element.getElementsByClass("company_name").text();
                    vacancy.setCompanyName(companyName);

                    String salary = element.getElementsByClass("salary").text();
                    vacancy.setSalary(salary);

                    vacancies.add(vacancy);
                }
            }
        } catch (IOException e) {}

        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        return Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
                .referrer("")
                .get();
    }
}
