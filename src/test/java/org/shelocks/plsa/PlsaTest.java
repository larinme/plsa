package org.shelocks.plsa;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.*;

import categorization.plsa.Document;
import categorization.plsa.Plsa;
import categorization.plsa.TextHelper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;

public class PlsaTest {

    private Plsa plsa;

    private TextHelper textHelper = new TextHelper();

    @Before
    public void before() {
        plsa = new Plsa(-1);
    }

    @Test
    public void testRandomProbilities() {
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            double[] array1 = plsa.randomProbilities(r.nextInt(10000) + 1);
            double sum = 0.0;
            for (double d : array1) {
                sum += d;
            }
            assertTrue(1.0 - 0.0001 < sum && sum < 1.0 + 0.0001);
        }
    }

    @Test
    public void testPlsaSimple() throws IOException, MyStemApplicationException {
        List<Document> docs = new LinkedList<Document>();

        String doc1 = "Нападающий футбольной сборной Аргентины и испанской \"Барселоны\" Лионель Месси оформил дубль в гостевом матче против мадридского \"Реала\", благодаря чему каталонцы выиграли в столице Испании. Второй гол стал для лидера \"Барсы\" 500-м за свою команду.\n" +
                "Решающий мяч Месси провел в ворота своих принципиальных соперников за 10 секунд до конца матча, в добавленное судьей время, напоминает \"Спорт сегодня\". При этом по ходу встречи аргентинец получил удар локтем в лицо от Марсело, но вернулся в игру и оформил дубль.\n" +
                "За карьеру, которую Месси проводит в \"Барселоне\", он забил 343 гола в чемпионате Испании, 43 гола в национальном Кубке, 94 гола в Лиге чемпионов и еще 20 – в остальных турнирах.\n" +
                "Месси стал восьмым игроком, который смог провести полтысячи голов за одну команду. Ранее 500 мячей за один клуб забивали Пеле (бразильский \"Сантос\", всего – 643 гола), Герд Мюллер (немецкая \"Бавария\", 566), Фернанду Пейротеу (португальский \"Спортинг\", 544), Йозеф Бицан (чешская \"Славия\", 534), Джимми Макгрори (шотландский \"Селтик\", 522), Джимми Джонс (североирландский \"Гленавон\", 517) и Уве Зеелер (немецкий \"Гамбург\", 507).";

        docs.add(getDoc("doc1", doc1));

        String doc2 = "Дубль 29-летнего аргентинца принес победу каталонскому клубу в «Эль-Класико»\n" +
                "\n" +
                "Форвард сборной Аргентины и «Барселоны» Лионель Месси достиг отметки в 500 забитых мячей в матчах за каталонскую команду во всех турнирах.\n" +
                "\n" +
                "29-летний футболист отметился дублем в ворота мадридского «Реала» и помог своей команде победить со счетом 3:2.\n" +
                "\n" +
                "В чемпионате Испании аргентинец забил за «Барселону» 343 гола (в 377 матчах), в Кубке Испании — 43 (61), в еврокубках — 94 (115), в других турнирах — 20 (24).\n" +
                "\n" +
                "За основной состав «Барселоны» Месси дебютировал в 2004 году.";
        docs.add(getDoc("doc2", doc2));

        String doc3 = "Евросоюз заинтересован в возвращении к стратегическому партнерству в отношениях с Россией. Об этом, отвечая на вопрос “Ъ”, заявила на переговорах с министром иностранных дел РФ Сергеем Лавровым Федерика Могерини, возглавляющая внешнеполитическую службу ЕС. Ее сегодняшний однодневный визит в Москву — первый в качестве руководителя европейской дипломатии.\n" +
                "\n" +
                "Верховный представитель ЕС по иностранным делам и политике безопасности посетила Россию впервые с 2013 года. Впрочем, тогда в Москве побывала Кэтрин Эштон, предшественница Федерики Могерини на этом посту. Сама госпожа Могерини уже приезжала в Россию летом 2014 года в качестве министра иностранных дел Италии: за визит, состоявшийся после присоединения Крыма и обострения ситуации на востоке Украины, ее тогда упрекали едва ли не в заигрывании с Кремлем.\n" +
                "\n" +
                "\n" +
                "Как Евросоюз продлил антироссийские санкции без обсуждения\n" +
                "О новом визите Федерики Могерини уже в качестве главы внешнеполитической службы ЕС речь шла с конца 2015 года. «Она приедет, но сроки мы пока не обсуждали»,— заявил в интервью “Ъ” постпред РФ при ЕС Владимир Чижов в июне прошлого года. Теперь в доме приемов МИД РФ в ответ на реплику корреспондента “Ъ” о том, что нынешний визит можно считать «прорывом», господин Чижов рассмеялся: «Победа. Но небольшая».\n" +
                "\n" +
                "«Спасибо, что приняли наше приглашение, хотя оно было направлено достаточно давно,— напомнил своей собеседнице Сергей Лавров.— Но лучше поздно, чем никогда». Та в ответ только широко улыбнулась — единственный раз за то время," +
                " когда за встречей могли наблюдать журналисты. «Мы переживаем непростой период в отношениях России с Западом, и это нам не доставляет радости. Мы по-прежнему нацелены на восстановление полноценного сотрудничества с ЕС — нашим крупнейшим торгово-экономическим партнером, с народами которого у нас очень много общего в историческом, культурном и ценностном отношении»,— заверил российский министр, призвав коллегу «сосредоточиться на реальных угрозах, а не на мнимых». В свою очередь, госпожа Могерини подтвердила: «У нас существуют определенные разногласия, но тем не менее мы остаемся партнерами. Европейский союз — это очень важный партнер России, в том числе экономический»."
        ;

        docs.add(getDoc("doc3", doc3));

        String doc4 = "Пресс-секретарь президента Дмитрий Песков отреагировал на заявление министра обороны Дании Клауса Йорта Фредриксена, обвинившего Россию в хахерских атаках и взломе почты сотрудников датского военного ведомства в период с 2015 по 2016 год.\n" +
                "\n" +
                "«В данном случае, конечно, интересно понять, что такое Россия? Россия — это страна, и Россия не занимается хакерскими атаками», — ответил спикер Кремля.\n" +
                "\n" +
                "Песков также призвал понять, «о чём идет речь и что послужило основанием для таких заявлений».";
        docs.add(getDoc("doc4", doc4));

        String doc5 = "Форвард «Барселоны» Лионель Месси 24 апреля забил юбилейный мяч за каталонскую команду — аргентинец оформил 500-е взятие ворот во всех турнирах за сине-гранатовых. Выступает Месси за «Барселону» с 2003 года.\n" +
                "\n" +
                "Ранее он в концовке выездного матча 33-го тура чемпионата Испании против «Реала» принес победу своей команде — 3:2."
;
        docs.add(getDoc("doc5", doc5));
        String doc6 = "Аргентинский нападающий \"Барселоны\" Лионель Месси в игре 33-го тура чемпионата Испании по футболу с мадридским \"Реалом\" забил свой 500-й мяч в официальных матчах за каталонскую команду.\n" +
                "В воскресенье в Мадриде \"Барселона\" одержала волевую победу над \"Реалом\" со счетом 3:2. Месси в этой игре сделал дубль, принеся своей команде победу на второй компенсированной минуте матча – именно этот мяч стал 500-м для форварда в футболке \"сине-гранатовых\".\n" +
                "Лучшие кадры матча >>>\n" +
                "Месси находится в системе \"Барселоны\" с 13 лет. Аргентинец дебютировал за каталонцев в октябре 2004 года, а первый мяч забил в мае 2005-го, став самым молодым автором гола в истории клуба."
                ;docs.add(getDoc("doc6", doc6));

        Plsa plsa = new Plsa(3);
        plsa.train(docs, 100000);

        double[][] docTopicPros = plsa.getDocTopics();

        assertTrue(docTopicPros[0][0] == docTopicPros[1][0]);
        assertTrue(docTopicPros[2][0] == docTopicPros[3][0]);
        assertTrue(docTopicPros[0][0] != docTopicPros[2][0]);
    }
    
    /**
     * 
     * This test cost will cost long time:(,you could choose
     * to skip it :)
     * 
     */
    @Test
    @Ignore
    public void testPlsaText() {
        try {
            File root = new File(Plsa.class.getClassLoader().getResource("news").toURI());
            List<File> files = Arrays.asList(root.listFiles());

            List<Document> docs = new LinkedList<Document>();

            for (File file : files) {
                docs.add(new Document(textHelper.extractWords(file), file.getName()));
            }

            int numOfTopic = 50;
            Plsa plsa = new Plsa(numOfTopic);
            plsa.train(docs, 50);

            /**
             * those are documents' name prefix
             * 
             */
            List<String> prefixes = new LinkedList<String>();
            int i = 0;
            while (i < numOfTopic){
                prefixes.add(String.valueOf(++i));
            }
            Map<Double, List<Integer>> group = new HashMap<Double, List<Integer>>();
            for (int j = 0; j < files.size(); j++) {
                double[] doubles = plsa.getDocTopics()[j];
                double max = -1;
                double maxIndex = -1;
                for (int k = 0; k < doubles.length; k++) {
                    double aDouble = doubles[k];
                    if (aDouble > max){
                        max = aDouble;
                        maxIndex = k;
                    }
                }
                List<Integer> integers = group.get(maxIndex);
                if (integers == null){
                    integers = new ArrayList<Integer>();
                }
                integers.add(j);
                group.put(maxIndex, integers);
            }

            for (Map.Entry<Double, List<Integer>> doubleListEntry : group.entrySet()) {
                System.out.println(doubleListEntry.getKey() + " ====== " + doubleListEntry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 
     * Print model statistics data
     * 
     * @param plsa
     * @param docs
     * @param prefixes
     */
    private void printStatisticsData(Plsa plsa, List<Document> docs, List<String> prefixes) {
        double[][] docTopicPros = plsa.getDocTopics();

        double[][] docTopicsStatistics = new double[plsa.getTopicNum()][plsa.getTopicNum()];
        for (int i = 0; i < docs.size(); i++) {
            String fileName = docs.get(i).getFileName();
            int index = -1;
            for (String prefix : prefixes) {
                if (fileName.startsWith(prefix)) {
                    index = prefixes.indexOf(prefix);
                    break;
                }
            }
            if (index == -1) {
                throw new RuntimeException("The index is -1!");
            }
            int maxValueIndex = TextHelper.getMaxValueIndex(docTopicPros[i]);
            docTopicsStatistics[index][maxValueIndex] = docTopicsStatistics[index][maxValueIndex] + 1;
        }
        String header = "  ";
        for (int i = 0; i < plsa.getTopicNum(); i++) {
            header += "topic" + i + " ";
        }
        System.out.println(header);

        for (int i = 0; i < docTopicsStatistics.length; i++) {
            System.out.println(prefixes.get(i) + " " + docTopicsStatistics[i][0] + " "
                    + docTopicsStatistics[i][1] + " " + docTopicsStatistics[i][2]);
        }
    }

    private Document getDoc(String docName, String line) throws IOException {
        List<String> words = textHelper.extractWords(line);
        return new Document(words, docName);
    }

}
