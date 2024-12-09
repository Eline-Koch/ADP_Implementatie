import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.BinarySearch;
import org.adp_implementatie.PerformanceBenchmark;
import org.adp_implementatie.SelectionSort;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BinarySearchTest {

    public static void main(String[] args)  {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();
        String[] strArray = {
                "aardbei", "abrikoos", "adelaar", "afwas", "agenda", "akker", "alarm", "algen", "ambacht", "anker",
                "ananas", "apotheek", "appel", "armband", "asbak", "avocado", "azijn", "bad", "baksteen", "banaan",
                "band", "bank", "barbecue", "batterij", "beker", "bel", "berg", "bessen", "bier", "bij",
                "bijbel", "bijl", "bingo", "blaadje", "blad", "bloem", "boek", "boer", "boerderij", "boetiek",
                "boog", "boon", "boot", "bos", "bot", "brander", "bril", "brood", "brug", "bruin",
                "buis", "cactus", "camera", "camping", "capsule", "caravan", "champignon", "chips", "cirkel", "clown",
                "code", "cola", "computer", "condens", "container", "cursus", "damp", "dank", "dapper", "datum",
                "deeg", "deken", "deksel", "diepvries", "diner", "dinosaurus", "dokter", "domino", "donder", "dorp",
                "doos", "doorn", "draak", "druif", "duif", "duik", "duim", "dynamiet", "eekhoorn", "ei",
                "eiland", "eind", "eis", "elastiek", "engel", "erwt", "etiket", "fabriek", "fiets", "figuur",
                "film", "flessen", "fluit", "folder", "friet", "fruit", "garage", "geel", "geest", "geld",
                "gem", "geranium", "glans", "gloed", "goud", "gras", "groente", "groot", "gruis", "gummi",
                "haak", "haan", "hakken", "ham", "handdoek", "hars", "haven", "heining", "heks", "helm",
                "herfst", "hond", "hooi", "hoorn", "hotel", "ijs", "ijzer", "inkt", "insect", "jak",
                "jas", "jong", "jongeren", "kaars", "kaas", "kaart", "kabouter", "kamer", "kamp", "kan",
                "kapitein", "kapstok", "kar", "karavaan", "karper", "kat", "keel", "kerst", "kikker", "kip",
                "klaar", "klei", "klok", "klomp", "klont", "knoop", "koek", "koe", "koets", "kogel",
                "koker", "kolen", "komeet", "komkommer", "konijn", "kop", "korf", "koud", "krab", "kraan",
                "krat", "krokodil", "kruiden", "kubus", "kussen", "laken", "lamp", "land", "leeuw", "lepel",
                "leuk", "lijn", "lijst", "lippen", "lolly", "machine", "mand", "markt", "mat", "meter",
                "meubel", "molen", "muis", "muur", "muziek", "naam", "naald", "nacht", "nagel", "nap",
                "natuur", "neus", "nieuw", "noot", "olie", "oog", "oor", "oven", "pak", "pan",
                "paraplu", "parel", "park", "pen", "pet", "piano", "pizza", "plant", "plank", "plaat",
                "plas", "plek", "pluim", "pomp", "poort", "pop", "pruim", "raam", "regen", "riet",
                "ring", "roos", "rug", "salade", "schaduw", "schoen", "schop", "schroef", "schuur", "sint",
                "sneeuw", "snoep", "soep", "spaarpot", "speld", "spin", "spiegel", "spoel", "sport", "stal",
                "steen", "stoel", "stof", "strand", "struik", "sushi", "taart", "tand", "tas", "tegel",
                "tent", "tijd", "toekan", "toilet", "tomaat", "trap", "trein", "trommel", "tulp", "uien",
                "uil", "vaas", "varken", "vijver", "vlam", "vlees", "vlieg", "vlinder", "vloer", "vogel",
                "voet", "volk", "volleybal", "vork", "vulkaan", "wand", "was", "water", "web", "wekker",
                "wind", "winkel", "worst", "zaad", "zak", "zeep", "zee", "zeil", "zeven", "zilver",
                "zon", "zout", "zwaard"
        };

        Pizza[] pizzas = {
                new Pizza("anchovis", false),
                new Pizza("artichoke", true),
                new Pizza("bbq chicken", false),
                new Pizza("capricciosa", false),
                new Pizza("carbonara", false),
                new Pizza("diavola", false),
                new Pizza("funghi", true),
                new Pizza("hawaiian", false),
                new Pizza("margherita", true),
                new Pizza("meat feast", false),
                new Pizza("mozarella", true),
                new Pizza("napoletana", true),
                new Pizza("pepperoni", false),
                new Pizza("quattro formaggi", true),
                new Pizza("quattro stagioni", false),
                new Pizza("salami", false),
                new Pizza("spinaci", true),
                new Pizza("tonno", false),
                new Pizza("vegetariana", true),
                new Pizza("vesuvio", false)
        };

        Integer[] largeArray = new Integer[100000000];


        int target = 99999999;

        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i + 1;
        }

        performSearch("Integer", largeArray, target);
        performSearch("String", strArray, "zeep");
        performSearch("Pizza objects", pizzas, pizzas[pizzas.length-1]);


        performSearchInefficient("Integer", largeArray, target);
        performSearchInefficient("String", strArray, "zeep");
        performSearchInefficient("Pizza objects", pizzas, pizzas[pizzas.length-1]);
    }

    public static <T extends Comparable<T>> int inefficientSearchWithForLoop(T[] array, T target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].compareTo(target) == 0) {
                return i;
            }
        }
        return -1; // item niet gevonden
    }

    public static <T extends Comparable<T>> void performSearch(String inputData, T[] array, T target){
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        System.out.println(inputData + " array test:");
        benchmark.start();
        int result = BinarySearch.binarySearch(array, target);
        benchmark.stop();
        System.out.println(benchmark.formatElapsedTime(benchmark.getElapsedTimeInSeconds()) + " seconden om " + target + " te vinden in een array met " + array.length + " items");

        System.out.println("Resultaat: " + result);
        System.out.println();
    }

    public static <T extends Comparable<T>> void performSearchInefficient(String inputData, T[] array, T target){
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        System.out.println(inputData + " array test Inefficient:");
        benchmark.start();
        int resultInefficient = inefficientSearchWithForLoop(array, target);
        benchmark.stop();
        System.out.println(benchmark.formatElapsedTime(benchmark.getElapsedTimeInSeconds()) + " seconden om " + target + " te vinden in een array met " + array.length + " items");

        System.out.println("Resultaat: " + resultInefficient);
        System.out.println();
    }
}
