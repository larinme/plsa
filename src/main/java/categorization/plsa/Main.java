package categorization.plsa;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main2(String[] args) throws IOException {

        TextHelper textHelper = new TextHelper();
        List<String> strings1 = textHelper.extractWords("D:\\ИТМО\\ДИПЛОМ-ПОСЛЕДНЕЕ ИЗДАНИЕ\\Даха\\plsa\\src\\main\\resources\\data\\51084");
        List<String> strings2 = textHelper.extractWords("D:\\ИТМО\\ДИПЛОМ-ПОСЛЕДНЕЕ ИЗДАНИЕ\\Даха\\plsa\\src\\main\\resources\\data\\51492");
        Document document1 = new Document(strings1, "51084");
        Document document2 = new Document(strings2, "51492");
        Plsa plsa = new Plsa(3);
        List<Document> docs = Arrays.asList(document1, document2);
        plsa.train(docs, 3);
        double[][] docTopics = plsa.getDocTopics();
        System.out.println(Arrays.deepToString(docTopics));

    }

    public static void main(String[] args) {
        List<String> strings1 = Arrays.asList("мама", "мыла", "раму");
        List<String> strings2 = Arrays.asList("мама", "готовила", "рыбу");
        List<String> strings3 = Arrays.asList("папа", "купил", "собаку");
        Document document = new Document(strings1, "1");
        Document document1 = new Document(strings2, "2");
        Document document2 = new Document(strings3, "3");
        List<Document> documents = Arrays.asList(document, document1, document2);
        Plsa plsa = new Plsa(2);
        plsa.train(documents, 50);
        double[][] docTopics = plsa.getDocTopics();

    }

}
