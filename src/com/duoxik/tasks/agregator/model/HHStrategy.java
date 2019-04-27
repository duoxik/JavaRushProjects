package com.duoxik.tasks.agregator.model;

import com.duoxik.tasks.agregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {

    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {

        List<Vacancy> vacancies = new ArrayList<>();

        try {
            for (int i = 0; ; i++) {
                Document document = getDocument(searchString, i);

                Elements elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");

                if (elements.size() == 0) break;

                for (Element element : elements) {

                    Vacancy vacancy = new Vacancy();

                    Element titleElement = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").first();

                    String title = titleElement.text();
                    vacancy.setTitle(title);

                    String siteName = titleElement.attr("href");
                    vacancy.setSiteName(siteName);

                    String url = titleElement.attr("href");
                    vacancy.setUrl(url);

                    String city = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text();
                    vacancy.setCity(city);

                    String companyName = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text();
                    vacancy.setCompanyName(companyName);

                    String salary = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text();
                    vacancy.setSalary(salary);

                    vacancies.add(vacancy);
                }
            }
        } catch (IOException e) {}

        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        //return Jsoup.connect(String.format(URL_FORMAT, searchString, page))
        return Jsoup.connect("http://javarush.ru/testdata/big28data2.html")
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
                    .referrer("")
                    .get();
    }
}
